package domain;
import java.io.Serializable;
import java.util.Arrays;

import common.Constantes;
import lombok.Getter;

@Getter
public class Juego implements Serializable {
    private final Palabra aAdivinar;
    private final Jugador jugador;
    private int intentos;
    char[]  sol;

    public Juego(Palabra aAdivinar, Jugador jugador) {
        this.aAdivinar = aAdivinar;
        this.jugador = jugador;
        this.intentos = 0;
        this.sol = new char[aAdivinar.getIncognita().length()];
        Arrays.fill(this.sol, '\0');
    }
    public String jugar(String input) {
        int     len = aAdivinar.getIncognita().length();
        boolean flag = true;

        if (input.length() == 1) {
            for (int i = 0; i < len; i++) {
                if (aAdivinar.getIncognita().charAt(i) == input.charAt(0)) {
                    this.sol[i] = input.charAt(0);
                    flag = false;
                }
            }
        } else if (input.length() == len) {
            if (input.equalsIgnoreCase(aAdivinar.getIncognita())){
                this.sol = aAdivinar.getIncognita().toCharArray();
                this.intentos = 0;
            }
        } else {
            flag = false;
            System.out.println(Constantes.ERROR);
        }
        if (flag)
            intentos++;
        return String.valueOf(sol);
    }

}
