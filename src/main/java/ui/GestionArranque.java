package ui;

import common.Categoria;
import common.CategoriaException;
import common.Comprobacion;
import common.Constantes;
import domain.Juego;
import domain.Jugador;
import domain.Palabra;
import net.datafaker.providers.entertainment.SouthPark;
import service.GestionPalabras;
import service.IGestionPalabras;

import java.io.IOException;
import java.util.Scanner;
import java.util.SplittableRandom;

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

        while (num!=1 && num!=2){
            System.out.println(Constantes.JUGAR_ADMINISTRADOR);
            num = leerNumeros(1,2);
            switch (num) {
                case 1:
                    System.out.println(Constantes.NOMBRE_JUGADOR);
                    String nombre = sc.nextLine();
                    Jugador jug = new Jugador(nombre);
                    start(jug , introduccion(jug));
                    break;
                case 2:
                    System.out.print(Constantes.CONTRASENYA);
                    if (introducirContrasenya(sc.nextLine())) mostrarMenuArranque();
                    break;
            }
        }
    }

    private boolean introducirContrasenya(String pwd) {
        if (pwd.equals(pass))
            return true;
        return false;
    }

    public void start(Jugador jug ,Juego jue) {
        GestionPalabras gp = new GestionPalabras();
        Scanner         sc = new Scanner(System.in);
        String          palabra = null;

        try {
            gp.setLista(gp.cargarFichero());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (jue.getIntentos() != 7 && !jue.getAAdivinar().getIncognita().equalsIgnoreCase(palabra)) {
            palabra = jue.jugar(sc.nextLine());
        }
        if (jue.getIntentos() == 7)
            System.out.println(Constantes.PERDIDO + jue.getAAdivinar().getIncognita());
         else
            System.out.println(Constantes.GANADO);
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
                case 2: //revisa aqui para que el nivel se ponga automaticamente en relación a la longitud de la incognita
                    String categoria = pedirCategoria();;
                    System.out.println(Constantes.DIFICULTAD);
                    int dificultad = leerNumeros(1,3);
                    System.out.println(Constantes.PEDIR_PALABRA);
                    String palabra = new Scanner(System.in).nextLine();
                    servicio.insertarPalabra(new Palabra(dificultad, palabra, categoria));
                    break;
                case 3:
                    System.out.println("Introduce el id de la palabra que quieres modificar");
                    int id = leerNumeros(0,0);
                    System.out.println("Introduce la nueva categoria");
                    String cat = pedirCategoria();
                    try {
                        servicio.modificarCategoria(id, cat);
                    } catch (CategoriaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Introduce el id de la palabra que quieres eliminar");
                    int id2 = leerNumeros(0,0);
                    servicio.eliminarPalabra(id2);
                    break;
                default:
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
