package ui;

import common.Constantes;
import domain.Juego;
import net.datafaker.providers.entertainment.SouthPark;
import service.GestionPalabras;
import service.IGestionPalabras;

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
        do {
            try {
                System.out.println("Si quieres jugar pulsa 1, si eres administrador 2");
                num = sc.nextInt();
                sc.nextLine();
                switch (num) {
                    case 1:
                        Juego ju = new Juego();
                        System.out.println("Introduce tu nombre");
                        ju.start(sc.nextLine());
                        break;
                    case 2:
                        //introducirContrasenya(sc);
                        mostrarMenuArranque();
                        break;
                    default:
                        System.out.println("Has introducido una opción que no existe");
                }
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Introduce un número");
            }
        }
        while (num!=1 && num!=2);
    }
    public static int mostrarMenu(){
        Scanner lector = new Scanner(System.in);
        System.out.println(Constantes.MENU+"\n"+Constantes.OPCION1+"\n"+Constantes.OPCION2+"\n"+Constantes.OPCION3+"\n"+Constantes.OPCION4);
        int num = lector.nextInt(); //tratar la excepción para evitar que se pare el programa si no introduce un número
        return num;
    }
    public void opciones(int opcion){
        switch(opcion){
            case 1:
                System.out.println(servicio.getListaPalabras());

        }
    }
    public void opciones(){
        int opcion = mostrarMenu();
        switch(opcion){
            case 0:
                System.out.println(servicio.escribirFichero());
                break;
            case 1:
                System.out.println(servicio.getListaPalabras());
                break;
            case 4:
                System.out.println(servicio.eliminarPalabra(2));
                break;
        }
    }

    public void mostrarMenuArranque(){
        Scanner lector = new Scanner(System.in);
        System.out.println(Constantes.MENU+"\n"+Constantes.OPCION1+"\n"+Constantes.OPCION2+"\n"+Constantes.OPCION3+"\n"+Constantes.OPCION4);
        int num = lector.nextInt(); //tratar la excepción para evitar que se pare el programa si no introduce un número
        opciones(num);
    }

}
