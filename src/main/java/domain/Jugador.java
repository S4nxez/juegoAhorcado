package domain;

import java.io.Serializable;

public class Jugador implements Serializable {
    private String nombre;
    public Jugador(String nombre) {
        this.nombre = nombre;
    }
}
