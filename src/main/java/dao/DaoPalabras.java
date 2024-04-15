package dao;

import common.CategoriaException;
import domain.Juego;
import domain.Palabra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface DaoPalabras {
    public boolean isEmptyPalabrasList() ;
    public boolean insertarPalabra(Palabra Palabra) ;
    public boolean insertarPalabra(int id, int level, String incognita, String categoria);
    public List<Palabra> getPalabrasCategoria(String categoria);
    public List<Palabra> getPalabrasNivelCategoria(int nivel, String categoria);
    public List<Palabra> getPalabrasNivel (int nivel);
    public List<Palabra> getPalabras(boolean ascendente);
    public boolean modificarCategoria(int id, String categoria) throws CategoriaException;
    public boolean modificarPalabra(int id, String incognita);
    public void eliminarPalabra(Palabra Palabra) ;
    public boolean eliminarPalabra(int id) ;
    void setPalabras(List<Palabra> lista);
    Juego leerFicheroBinario();
    List<Palabra> cargarFichero() throws IOException;
    boolean escribirFicheroBinario(Juego juego);
    boolean escribirFichero(List<Palabra> lista, String nombreFichero) throws FileNotFoundException;
}
