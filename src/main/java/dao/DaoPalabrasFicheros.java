package dao;

import common.Categoria;
import common.CategoriaException;
import domain.Juego;
import domain.Palabra;
import net.datafaker.Faker;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
        List<Palabra> original = new ArrayList<>();
        int autonumerico = 0;

        try {
            Faker faker = new Faker();
            for (int i = 0; i < 30; i++) {
                original.add(new Palabra(autonumerico++, 1, faker.pokemon().name(), Categoria.pokemon.name()));
            }
            for (int i = 0; i < 30; i++) {
                original.add(new Palabra(autonumerico++, 1, faker.chiquito().terms(), Categoria.chiquito.name()));
            }
            for (int i = 0; i < 30; i++) {
                original.add(new Palabra(autonumerico++, 1, faker.simpsons().character(), Categoria.simpsons.name()));
            }
            for (int i = 0; i < 30; i++) {
                original.add(new Palabra(autonumerico++, 1, faker.southPark().characters(), Categoria.southpark.name()));
            }
            for (Palabra p: original){
                if (p.getIncognita().length() <= 6)
                    p.setLevel(1);
                else if (p.getIncognita().length() > 7)
                    p.setLevel(2);
                else
                    p.setLevel(3);
            }
        } catch (CategoriaException e) {
            System.out.println(e.getMessage());
        }
        HashSet<Palabra> sinDuplicados = new HashSet<>(original);

        return new ArrayList<>(sinDuplicados);
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

    public static boolean escribirFichero(List<Palabra> lista, String nombreFichero) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(nombreFichero);
        //? si no está vacía
        for (Palabra palabra : lista) {
            pw.println(palabra.toStringFichero());
        }
        pw.close();
        return true;
    }

    public static Juego leerFicheroBinario() {
        Juego auxiliar = null;
        File file = new File(FICHEROB);
        if (file.exists() && file.length() != 0) {
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

    public static void eliminarBinario() {
        File fichero = new File(FICHEROB);
        fichero.delete();
    }
}
