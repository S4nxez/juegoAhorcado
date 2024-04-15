package ui;

import common.Categoria;
import common.CategoriaException;
import common.Comprobacion;
import common.Constantes;
import domain.Juego;
import domain.Jugador;
import domain.Palabra;
import service.GestionPalabras;
import service.IGestionPalabras;

import java.io.IOException;
import java.util.Scanner;

/**
 * Clase con métodos de administración para consola
 */
public class GestionArranque {
    private final IGestionPalabras servicio;
    private static final String pass = "2223";
    public GestionArranque(){
        servicio = new GestionPalabras();
    }

    public void juegoArranque() {
        Scanner sc = new Scanner(System.in);
        int num=0;

        try {
            servicio.setLista(servicio.cargarFichero());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (num!=1 && num!=2){
            System.out.println(Constantes.JUGAR_ADMINISTRADOR);
            num = leerNumeros(1,2);
            switch (num) {
                case 1:
                    System.out.println(Constantes.NOMBRE_JUGADOR);
                    Jugador jug = new Jugador(sc.nextLine());
                    Juego jue = introduccion(jug);
                    imprimirSol(jue.getSol(), jue.getAAdivinar().getIncognita().length());
                    start(jug , jue);
                    break;
                case 2:
                    System.out.print(Constantes.CONTRASENYA);
                    if (introducirContrasenya(sc.nextLine())) mostrarMenuArranque();
                    break;
            }
        }
        servicio.escribirFichero();
    }

    private boolean introducirContrasenya(String pwd) {
        return pwd.equals(pass);
    }

    public void start(Jugador jug ,Juego jue) {
        Scanner         sc = new Scanner(System.in);
        String          palabra = null;

        while (jue.getIntentos() != 7 && !jue.getAAdivinar().getIncognita().equalsIgnoreCase(palabra)) {
            palabra = jue.jugar(sc.nextLine());
            servicio.escribirFicheroBinario(jue);
            imprimirSol(jue.getSol(), jue.getAAdivinar().getIncognita().length());
        }
        if (jue.getIntentos() == 7)
            System.out.println(Constantes.PERDIDO + jue.getAAdivinar().getIncognita());
         else
            System.out.println(Constantes.GANADO);
    }
    private void imprimirSol(char[] sol, int len) {
        for (int i = 0; i < len; i++) {
            if (sol[i]  == '\0') {
                System.out.print("_");
            }else {
                System.out.print(sol[i]);
            }
        }
        System.out.println();
    }
    public Juego introduccion(Jugador jug1){
        String  categoria;
        Juego   juego = null;

        while (juego == null) {
            System.out.println(Constantes.PARTIDA_ANTIGUA);
            int num = leerNumeros(1,2);

            if (num == 2) {
                categoria = pedirCategoria();
                System.out.println(Constantes.DIFICULTAD);
                int dificultad = leerNumeros(1,3);
                int numero = (int) (Math.random() * servicio.consultaNivelDificultad(dificultad, categoria).size());
                juego = new Juego(servicio.consultaNivelDificultad(dificultad, categoria).get(numero), jug1);
            } else if (num == 1) {
                juego = servicio.cargarFicheroBinario();
                if(juego==null){
                    System.out.println(Constantes.NO_PARTIDA_GUARDADA);
                }
            }
        }
        return juego;
    }

    public void mostrarMenuArranque(){
        boolean salir = false;
        int     num;

        while(!salir) {
            System.out.println(Constantes.MENU+"\n"+Constantes.OPCION1+"\n"+Constantes.OPCION2+"\n"+Constantes.OPCION3+"\n"+Constantes.OPCION4+"\n"+Constantes.SALIR);
            num = leerNumeros(0,0);
            switch(num){
                case 1:
                    System.out.println(servicio.getListaPalabras());
                    break;
                case 2: //revisa aquí para que el nivel se ponga automáticamente en relación con la longitud de la incognita
                    String categoria = pedirCategoria();
                    System.out.println(Constantes.DIFICULTAD);
                    int dificultad = leerNumeros(1,3);
                    System.out.println(Constantes.PEDIR_PALABRA);
                    String palabra = new Scanner(System.in).nextLine();
                    servicio.insertarPalabra(new Palabra(dificultad, palabra, categoria));
                    break;
                case 3:
                    System.out.print(Constantes.INTRODUCE_ID);
                    int id = leerNumeros(0,0);
                    String cat = pedirCategoria();
                    try {
                        servicio.modificarCategoria(id, cat);
                    } catch (CategoriaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println(Constantes.INTRODUCE_ID);
                    int id2 = leerNumeros(0,0);
                    servicio.eliminarPalabra(id2);
                    break;
                default:
                    servicio.escribirFichero();
                    salir = true;
            }
        }
    }

    private String pedirCategoria() {
        Scanner sc = new Scanner(System.in);
        boolean categoriaExiste = false;
        String  categoria;

        do {
            System.out.println(Constantes.SELECCIONA_CATEGORIA);
            for (Categoria cat: Categoria.values()) {
                System.out.print(cat + " | ");
            }
            System.out.println();
            categoria = sc.nextLine();
            try {
                Comprobacion.categoriaOk(categoria);
                categoriaExiste = true;
            } catch (CategoriaException e){
                System.out.println(e.getMessage());
            }
        } while (!categoriaExiste);
        return categoria;
    }

    private int leerNumeros(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        boolean flag = false;

        while (!flag) {
            try {
                num = sc.nextInt();
                if ((num >= min && num <= max) || (min == max))
                    flag = true;
                else {
                    System.out.println(Constantes.ERROR_RANGO + min + " , " + max);
                }
            } catch (Exception e) {
                System.out.println(Constantes.ERROR_INPT_MISMATCH);
            }
            sc.nextLine();
        }
        return num;
    }
}
