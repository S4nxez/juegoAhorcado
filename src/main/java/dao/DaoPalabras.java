package dao;

import common.CategoriaException;
import domain.Juego;
import domain.Palabra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface DaoPalabras {
    boolean insertarPalabra(Palabra Palabra);
    List<Palabra> getPalabrasNivelCategoria(int nivel, String categoria);
    List<Palabra> getPalabras(boolean ascendente);
    boolean modificarCategoria(int id, String categoria) throws CategoriaException;
    boolean eliminarPalabra(int id) ;
    void setPalabras(List<Palabra> lista);
    Juego leerFicheroBinario();
    List<Palabra> cargarFichero() throws IOException;
    boolean escribirFicheroBinario(Juego juego);
    boolean escribirFichero(List<Palabra> lista, String nombreFichero) throws FileNotFoundException;
    void eliminarBinario();
}
