package dao;


import common.Categoria;
import common.CategoriaException;
import domain.Palabra;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class Palabras {
    private final ArrayList<Palabra> palabras;
    private static int autonumerico;

    public Palabras() {
        this.palabras = new ArrayList<>();
        //leerFichero();
    }

    public Palabras(ArrayList<Palabra> Palabras) {
        this.palabras = Palabras;
    }

    public static void setAutonumerico(int i) {
        autonumerico = i;
    }

    public List<Palabra> getListaPalabras() {
        return palabras;
    }

    public int getSize() {
        return palabras.size();
    }

    public static int getAutonumerico() {
        return autonumerico;
    }

    public void setListaPalabras(List<Palabra> Palabras) {
        this.palabras.clear();
        this.palabras.addAll(Palabras);
        setAutonumerico(Palabras.size());
    }

    public boolean eliminaPalabra(int i) {
        boolean salir = false;
        for (int j = 0; j < palabras.size() && !salir; j++) {
            if (palabras.get(j).getId()==i) {
                palabras.remove(j);
                salir = true;
            }
        }
        return salir;
    }

    public Palabra get(int i) {
        return palabras.get(i);
    }
}

