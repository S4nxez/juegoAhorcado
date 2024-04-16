package dao;

import common.CategoriaException;
import domain.Juego;
import domain.Palabra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DaoPalabrasImplementacion implements DaoPalabras {

    protected final Palabras lista;

    public DaoPalabrasImplementacion() {
        this.lista = new Palabras();
    }

    @Override
    public boolean insertarPalabra(Palabra Palabra) {
        return this.lista.getListaPalabras().add(Palabra);
    }

    @Override
    public List<Palabra> getPalabrasNivelCategoria(int nivel, String categoria) {
        List<Palabra> ret = new ArrayList<>();
        for (int i = 0; i < lista.getSize(); i++) {
            if (this.lista.get(i).getLevel() == nivel && this.lista.get(i).getCategoria().equalsIgnoreCase(categoria))
                ret.add(this.lista.get(i));
        }
        return ret;
    }

    @Override
    public List<Palabra> getPalabras(boolean ascendente) {
        List<Palabra> aux = lista.getListaPalabras();
        Collections.sort(aux);
        if (!ascendente)
            Collections.reverse(aux);
        return aux;
    }

    @Override
    public boolean eliminarPalabra(int id) {
        return lista.eliminaPalabra(id);
    }

    @Override
    public void setPalabras(List<Palabra> lista) {
        this.lista.setListaPalabras(lista);
    }

    @Override
    public Juego leerFicheroBinario() {
        return DaoPalabrasFicheros.leerFicheroBinario();
    }

    @Override
    public List<Palabra> cargarFichero() throws IOException {
        return DaoPalabrasFicheros.leerFichero();
    }

    @Override
    public boolean escribirFicheroBinario(Juego juego) {
        return DaoPalabrasFicheros.escribirFicheroBinario(juego);
    }

    @Override
    public boolean escribirFichero(List<Palabra> lista, String nombreFichero) throws FileNotFoundException {
        return DaoPalabrasFicheros.escribirFichero(lista, nombreFichero);
    }

    @Override
    public void eliminarBinario() {
        DaoPalabrasFicheros.eliminarBinario();
    }

    @Override
    public boolean modificarCategoria(int id, String categoria) throws CategoriaException {
        return this.lista.get(id).setCategoria(categoria);
    }

}
