package dao;

import common.Categoria;
import common.CategoriaException;
import domain.Juego;
import domain.Palabra;
import net.datafaker.Faker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaoPalabrasFicheros{
    public static final String FICHERO = "Fichero";
    public static final String FICHEROB = "FicheroBinario";

    public static void crearFicheros() throws IOException {
        File fichero1 = new File(FICHERO);
        File fichero2 = new File(FICHEROB);
        if (!fichero1.exists()) {
            fichero1.createNewFile();
            PrintWriter pw = new PrintWriter(fichero1);
            for(Palabra pa :  crearDiccionario()){
                pw.print(pa);
            }
            pw.close();
        }
        if (!fichero2.exists())
            fichero2.createNewFile();
    }

    private static List<Palabra> crearDiccionario() {
        List<Palabra> ret = new ArrayList<>();
        int autonumerico = 0;

        try {
            ret.add(new Palabra(autonumerico++, 1, "El mejor verano de mi vida", Categoria.comedia.name()));
            ret.add(new Palabra(autonumerico++, 1, "Misión Imposible IV fallout", Categoria.accion.name()));
            Faker faker = new Faker();
            for (int i = 0; i < 10; i++) {
                ret.add(new Palabra(autonumerico++, 2, faker.pokemon().name(), Categoria.pokemon.name()));
            }
            for (int i = 0; i < 30; i++) {
                String chiquito = faker.chiquito().terms();
                if (chiquito.length() <= 6)
                    ret.add(new Palabra(autonumerico++, 1, chiquito, Categoria.chiquito.name()));
                else if (chiquito.length() == 7)
                    ret.add(new Palabra(autonumerico++, 2, chiquito, Categoria.chiquito.name()));
                else
                    ret.add(new Palabra(autonumerico++, 3, chiquito, Categoria.chiquito.name()));
            }
        } catch (CategoriaException e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public static List<Palabra> leerFichero() throws IOException {
        return leerFichero(DaoPalabrasFicheros.FICHERO);
    }

    public static List<Palabra> leerFichero(String fichero) throws IOException {
        ArrayList<Palabra> ret = null;

        crearFicheros();
        try (Scanner sc = new Scanner(new File(fichero))) {
            ret = new ArrayList<>();
            while (sc.hasNextLine()) {
                String cadena = sc.nextLine();
                try {
                    ret.add(new Palabra(cadena));
                } catch (CategoriaException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(DaoPalabrasFicheros.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex); //no entiendo
        }
        return ret;
    }

    public static List<Palabra> leerFicheroBufferedReader(String fichero) throws IOException {
        ArrayList<Palabra> auxiliar = null;
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        auxiliar = new ArrayList<>();
        String cadena;
        do {
            cadena = br.readLine();
            if (cadena != null) {
                String[] trozos = cadena.split(";");
                try {
                    //auxiliar.add(new Palabra(Integer.parseInt(trozos[0]),Integer.parseInt(trozos[1]),trozos[2],trozos[3]));
                    auxiliar.add(new Palabra(cadena));
                } catch (CategoriaException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (cadena != null);
    return auxiliar;
}

    public static boolean escribirFichero(List<Palabra> lista, String nombreFichero) throws FileNotFoundException {
        String cadena = null;
        PrintWriter pw = new PrintWriter(nombreFichero);
        //? si no está vacía
        for (int i = 0; i < lista.size(); i++) {
            pw.println(lista.get(i).toStringFichero());
        }
        pw.close();
        return true;
    }

    public static void escribirFicheroConsola(String nombreFichero) throws FileNotFoundException {
        Scanner lector = new Scanner(System.in);
        String cadena = null;
        PrintWriter pw = new PrintWriter(nombreFichero);
        System.out.println("Introduce las líneas que quieras escribir en el fichero, para acabar pon FIN");
        do {
            cadena = lector.nextLine();
            if (!cadena.equalsIgnoreCase("fin"))
                pw.println(cadena);
        } while (!cadena.equalsIgnoreCase("fin"));
        pw.close();
    }

    public static void LeerFichero(String nombreFichero) throws FileNotFoundException {
        Scanner lector = new Scanner(new File(nombreFichero));
        String cadena;
        while (lector.hasNextLine()) {
            System.out.println(lector.nextLine());
        }
        System.out.println("Se ha alcanzado el final del fichero");
    }

    public static void escribirFicheroConsolaNoSobreescribir(String nombreFichero) throws IOException {
        Scanner lector = new Scanner(System.in);
        String cadena = null;
        /*FileWriter fw = new FileWriter(nombreFichero,true);
        PrintWriter pw1 = new PrintWriter(fw);*/
        PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero, true));

        System.out.println("Introduce las líneas que quieras escribir en el fichero, para acabar pon FIN");
        do {
            cadena = lector.nextLine();
            if (!cadena.equalsIgnoreCase("fin"))
                pw.println(cadena);
        } while (!cadena.equalsIgnoreCase("fin"));
        pw.close();
    }

    /**
     * Ejemplo de lectura de fichero binario. Pensad cómo utilizarlo para guardar y recuperar partida, guardando el objeto juego
     * en vez del ArrayList
     *
     * @return
     */
    public static Juego leerFicheroBinario() {
        Juego auxiliar = null;
        File file = new File(FICHEROB);
        if (file.exists()) {
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(file))) {
                auxiliar = (Juego) is.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(DaoPalabrasFicheros.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return auxiliar;
    }


    public static boolean escribirFicheroBinario(Juego juego) {
        boolean escrito = false;
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FICHEROB))) {
            os.writeObject(juego);
            escrito = true;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DaoPalabrasFicheros.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }
        return escrito;
    }


}
