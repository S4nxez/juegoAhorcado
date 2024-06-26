package domain;


import common.CategoriaException;
import common.Comprobacion;
import dao.Palabras;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Palabra implements Comparable<Palabra>, Serializable {
    private int id;
    private int level;
    private String incognita;
    private String categoria;

    /**
     *
     * @param id identicador único de cada elemento, el String incognita puede tener espacios, mayúsculas, mínúsculas que no lo hagan único
     * @param level indica la dificultad que le asignamos a la incognita a adivinar por el tamaño de caracteres por ejemplo
     * @param incognita palabra a adivinar
     * @param categoria String que debe se un elemento de Categoria, si no lo es se lanzará CategoriaEx y así no se creará el objeto
     * @throws CategoriaException arroja esta excepción si la categoría no es una de las establecidas
     */
    public Palabra(int id, int level, String incognita, String categoria) throws CategoriaException {
        this.id = id;
        this.level = level;
        this.incognita = incognita.toLowerCase();
        Comprobacion.categoriaOk(categoria);
        this.categoria = categoria;
    }
    public Palabra(int level, String incognita, String categoria) {
        this.id = Palabras.getAutonumerico();
        Palabras.setAutonumerico(Palabras.getAutonumerico()+1);
        this.level = level;
        this.incognita = incognita.toLowerCase();
        this.categoria = categoria;
    }

    public Palabra(String cadena) throws CategoriaException {
        String[] trozos = cadena.split(";");
        this.id = Integer.parseInt(trozos[0]);
        this.level = Integer.parseInt(trozos[1]);
        this.incognita = trozos[2];
        Comprobacion.categoriaOk(trozos[3]);
        this.categoria = trozos[3];
    }

    public boolean setCategoria(String categoria) throws CategoriaException {
        Comprobacion.categoriaOk(categoria);
        this.categoria = categoria;
        return true;
    }

    @Override
    public int compareTo(Palabra o) {
        return Integer.compare(this.id,o.id);
    }

    @Override
    public String toString() {
        return id + ";" + level + ";" + incognita + ";" + categoria + "\n";
    }
    public String toStringFichero() {
        return id + ";" + level + ";"+ incognita+ ";"+ categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palabra palabra = (Palabra) o;
        return Objects.equals(incognita, palabra.incognita) && Objects.equals(categoria, palabra.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incognita, categoria);
    }
}
