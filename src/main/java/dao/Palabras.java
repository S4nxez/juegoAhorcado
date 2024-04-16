package dao;


import domain.Palabra;

import java.util.ArrayList;
import java.util.List;

public class Palabras {
    private final ArrayList<Palabra> palabras;
    private static int autonumerico;

    public Palabras() {
        this.palabras = new ArrayList<>();
    }

    public Palabras(ArrayList<Palabra> Palabras) {
        this.palabras = Palabras;
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
    public static void setAutonumerico(int auto) {
        autonumerico = auto;
    }

    public void setListaPalabras(List<Palabra> Palabras) {
        this.palabras.clear();
        this.palabras.addAll(Palabras);
        autonumerico = Palabras.size();
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

