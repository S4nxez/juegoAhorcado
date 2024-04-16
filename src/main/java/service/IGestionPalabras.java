package service;

import common.CategoriaException;
import domain.Juego;
import domain.Palabra;

import java.io.IOException;
import java.util.List;

public interface IGestionPalabras {
    List<Palabra> getListaPalabras();
    boolean insertarPalabra(Palabra Palabra);
    /**
     *
     * @param id
     * @param categoria si la categoria no es válida se lanzará una excepción y no será insertado el elememto
     * @return
     * @throws CategoriaException
     */
    boolean modificarCategoria(int id, String categoria) throws CategoriaException;
    List<Palabra> cargarFichero() throws IOException;
    boolean escribirFichero();
    boolean escribirFicheroBinario(Juego juego);
    Juego cargarFicheroBinario();
    boolean eliminarPalabra(int id);
    List<Palabra> consultaNivelDificultad(int dificultad, String categoria);
    void setLista(List<Palabra> lista);
    void eliminarBinario();
}
