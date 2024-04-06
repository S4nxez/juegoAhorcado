package ui;

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

    private static IGestionPalabras servicio;
    private static final String pass = "2223";

    public GestionArranque(){
        servicio = new GestionPalabras();
    }

    public void juegoArranque() {
        Scanner sc = new Scanner(System.in);
        int num=0;
        while (num!=1 && num!=2){
            System.out.println(Constantes.JUGAR_ADMINISTRADOR);
            num = leerNumeros();
            switch (num) {
                case 1:
                    System.out.println(Constantes.NOMBRE_JUGADOR);
                    String nombre = sc.nextLine();
                    Jugador jug = new Jugador(nombre);
                    start(jug , introduccion(sc, jug));
                    break;
                case 2:
                    //introducirContrasenya(sc);
                    mostrarMenuArranque();
                    break;
            }
        }
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
        if (jue.getIntentos() == 7) {
            System.out.println(Constantes.PERDIDO + jue.getAAdivinar().getIncognita());
        } else {
            System.out.println(Constantes.GANADO);
        }
    }

    public static Juego introduccion(Scanner sc, Jugador jug1){
        boolean categoriaExiste;
        String  categoria;
        Juego   juego = null;

        while (juego == null) {
            System.out.println(Constantes.PARTIDA_ANTIGUA);
            int num = leerNumeros();

            if (num == 2) {
                do {
                    System.out.println("Escibe la categoría deseada: Formula1, Simpsons");
                    categoria = sc.nextLine();
                    categoriaExiste = false;
                    try {
                        Comprobacion.categoriaOk(categoria);
                        categoriaExiste = true;
                    } catch (CategoriaException e){
                        System.out.println(e.getMessage());
                    }
                } while (!categoriaExiste);
                System.out.println(Constantes.DIFICULTAD);
                int dificultad = sc.nextInt();
                sc.nextLine();
                int numero = (int) (Math.random() * servicio.consultaNivelDificultad(dificultad, categoria).size());
                System.out.println(numero);
                juego = new Juego(servicio.consultaNivelDificultad(dificultad, categoria).get(numero), jug1);
            } else {
                juego = servicio.cargarFicheroBinario();
                if(juego==null){
                    System.out.println(Constantes.NO_PARTIDA_GUARDADA);
                }
            }
        }
        return juego;
    }
    public static int mostrarMenu(){
        Scanner lector = new Scanner(System.in);
        System.out.println(Constantes.MENU+"\n"+Constantes.OPCION1+"\n"+Constantes.OPCION2+"\n"+Constantes.OPCION3+"\n"+Constantes.OPCION4);
        int num = leerNumeros();
        return num;
    }
    public void opciones(int opcion){
        switch(opcion){
            case 1:
                System.out.println(servicio.getListaPalabras());
        }
    }

    public void mostrarMenuArranque(){
        Scanner lector = new Scanner(System.in);
        System.out.println(Constantes.MENU+"\n"+Constantes.OPCION1+"\n"+Constantes.OPCION2+"\n"+Constantes.OPCION3+"\n"+Constantes.OPCION4);
        int num = lector.nextInt(); //tratar la excepción para evitar que se pare el programa si no introduce un número
        opciones(num);
    }

    private static int leerNumeros() {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        boolean flag = false;
        while (!flag) {
            try {
                num = sc.nextInt();
                flag = true;
            } catch (Exception e) {
                System.out.println(Constantes.ERROR_INPT_MISMATCH);
            }
            sc.nextLine();
        }
        return num;
    }

}
