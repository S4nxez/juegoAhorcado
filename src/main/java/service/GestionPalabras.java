package service;

import common.CategoriaException;
import dao.DaoPalabras;
import dao.DaoPalabrasImplementacion;
import domain.Juego;
import domain.Palabra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GestionPalabras implements IGestionPalabras {

    private final DaoPalabras daoPalabras;

    public GestionPalabras() {
        this.daoPalabras = new DaoPalabrasImplementacion();
    }

    @Override
    public List<Palabra> getListaPalabras() {
        return daoPalabras.getPalabras(true);
    }

    @Override
    public boolean insertarPalabra(Palabra palabra) {
        return daoPalabras.insertarPalabra(palabra);
    }

    @Override
    public boolean modificarCategoria(int id, String categoria) throws CategoriaException {
        return daoPalabras.modificarCategoria(id, categoria);
    }

    @Override
    public List<Palabra> cargarFichero() throws IOException {
        return daoPalabras.cargarFichero();
    }

    @Override
    public boolean escribirFichero() {
        try {
            return daoPalabras.escribirFichero(daoPalabras.getPalabras(true),"Fichero");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean escribirFicheroBinario(Juego juego) {
        return daoPalabras.escribirFicheroBinario(juego);
    }

    @Override
    public Juego cargarFicheroBinario() {
        return daoPalabras.leerFicheroBinario();
    }

    @Override
    public boolean eliminarPalabra(int id) {
        return daoPalabras.eliminarPalabra(id);
    }

    @Override
    public List<Palabra> consultaNivelDificultad(int dificultad, String categoria) {
        return daoPalabras.getPalabrasNivelCategoria(dificultad, categoria);
    }

    @Override
    public void setLista(List<Palabra> lista) {
        daoPalabras.setPalabras(lista);
    }

    @Override
    public void eliminarBinario() {
        daoPalabras.eliminarBinario();
    }

}