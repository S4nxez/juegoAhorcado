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

/**
 * Clase con métodos de administración para consola
 */
public class GestionArranque {

    private static IGestionPalabras servicio;
    private static final String pass = "2223";

    public GestionArranque(){ servicio = new GestionPalabras();
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
                        String nombre = sc.nextLine();
                        Jugador jug = new Jugador(nombre);
                        System.out.println("Introduce tu nombre");
                        Juego ju = new Juego(new Palabra("Prueba"), jug); // aqui hay que solucionar el tema de la palabra que le meto ahi a machete
                        start(nombre , introduccion(sc, jug));
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
    public void start(String jugNom ,Juego jue) {
        GestionPalabras gp = new GestionPalabras();
        Jugador         jug1 = new Jugador(jugNom);
        Scanner         sc = new Scanner(System.in);
        String          palabra = null;
        int             out = 0;

        try {
            gp.setLista(gp.cargarFichero());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (out != 2) {
            jue = introduccion(sc, jug1);
            String[] intento = new String[jue.getAAdivinar().getIncognita().length()];
            while (jue.getIntentos() != 7 && !jue.getAAdivinar().getIncognita().equalsIgnoreCase(palabra)) {
                palabra = jue.jugar(sc.nextLine());
                if (palabra == null) break;
            }
            System.out.println("Si quieres seguir jugando escribe 1, quieres parar escribe 2");
            out = sc.nextInt();
            sc.nextLine();
        }
    }

    public static Juego introduccion(Scanner sc, Jugador jug1){
        boolean categoriaExiste;
        String  categoria;
        Juego   juego = null;

        while (juego == null) {
            System.out.println("Para jugar una partida antigua escribe 1, sino escribe 2");
            int num = sc.nextInt();
            sc.nextLine();

            if (num == 2) {
                do {
                    System.out.println("Escibe la categoría deseada: Formula1, Simpsons");
                    categoria = sc.nextLine();
                    categoriaExiste = false;
                    try {
                        Comprobacion.categoriaOk(categoria);
                        categoriaExiste = true;
                    } catch (CategoriaException e){
                        System.out.println("Categoria no valida");
                    }
                } while (!categoriaExiste);
                System.out.println("Escribe la dificultad, van del 1 al 3, cuanto más nivel las palabras son más largas.");
                int dificultad = sc.nextInt();
                sc.nextLine();
                int numero = (int) (Math.random() * servicio.consultaNivelDificultad(dificultad, categoria).size());
                System.out.println(numero);
                juego = new Juego(servicio.consultaNivelDificultad(dificultad, categoria).get(numero), jug1);

            } else {
                juego = servicio.cargarFicheroBinario();
                if(juego==null){
                    System.out.println("No tienes partida guardada, por favor presione 2");
                }
            }
        }
        return juego;
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
