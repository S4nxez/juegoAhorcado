package domain;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import common.Constantes;
import lombok.Getter;
import service.GestionPalabras;

@Getter
public class Juego {
    //pensar en los atributos que definen el estado del juego en ese instante para que que si lo paran se pueda recuperar
    private Palabra aAdivinar; //o el String directamente
    private Jugador jugador;
    private int intentos;
    public Juego(Palabra aAdivinar, Jugador jugador) {
        this.aAdivinar = aAdivinar;
        this.jugador = jugador;
        this.intentos = 0;
    }
    public String jugar(String input) {
        int     len = aAdivinar.getIncognita().length() - 1;
        char[]  sol = new char[len];

        Arrays.fill(sol, '\0');
        imprimirSol(sol, len);
        if (input.length() == 1) {
            for (int i = 0; i < len; i++) {
                if (aAdivinar.getIncognita().charAt(i) == input.charAt(0)) {
                    sol[i] = input.charAt(0);
                }
                i++;
            }
            comprobar(sol,len);
        } else if (input.length() == len + 1) {
            input.equalsIgnoreCase(aAdivinar.getIncognita());
        } else {
            System.out.println(Constantes.ERROR);
        }
        intentos++;
        return sol.toString();
    }
    private boolean comprobar(char[] sol, int len) {
        boolean ret = true;

        for (int i = 0; i < len; i++)
            if (sol[i] == '\0') ret = false;
        return ret;
    }
    private void imprimirSol(char[] sol, int len) {
        for (int i = 0; i < len; i++) {
            if (sol[i]  == '\0') {
                System.out.print("_");
            }else {
                System.out.print(sol[i]);
            }
        }
    }


}
