package domain;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import common.Constantes;
import lombok.Getter;
import service.GestionPalabras;

@Getter
public class Juego {
    private Palabra aAdivinar;
    private Jugador jugador;
    private int intentos;
    char[]  sol;

    public Juego(Palabra aAdivinar, Jugador jugador) {
        this.aAdivinar = aAdivinar;
//        System.out.println(aAdivinar.getIncognita());
        this.jugador = jugador;
        this.intentos = 0;
        this.sol = new char[aAdivinar.getIncognita().length()];
        Arrays.fill(this.sol, '\0');
        imprimirSol(this.sol, aAdivinar.getIncognita().length());
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
        imprimirSol(this.sol, len);
        return String.valueOf(sol);
    }
    private void imprimirSol(char[] sol, int len) {
        for (int i = 0; i < len; i++) {
            if (sol[i]  == '\0') {
                System.out.print("_");
            }else {
                System.out.print(sol[i]);
            }
        }
        System.out.println();
    }
}
