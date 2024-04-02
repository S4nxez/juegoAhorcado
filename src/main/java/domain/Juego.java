package domain;
import java.util.Arrays;
import java.util.Scanner;

import common.CategoriaException;
import common.Constantes;
import org.yaml.snakeyaml.scanner.ScannerException;

public class Juego {
    //pensar en los atributos que definen el estado del juego en ese instante para que que si lo paran se pueda recuperar
    private Palabra aAdivinar; //o el String directamente
    private Jugador jugador;
    private int intentos;
    private int dificultad; //opcional, aquí o por elemento.

    public Juego() {
        try {
            aAdivinar = new Palabra(1, "hola", "saludo");
        } catch (CategoriaException e) {
            throw new RuntimeException(e);
        }
        jugador = new Jugador();
        intentos = 5;
        dificultad = 1;
    }
    public void jugar() {
        Scanner sc = new Scanner(System.in);
        String  input;
        int     len = aAdivinar.getIncognita().length() - 1;
        char[]  sol = new char[len];
        boolean gana = false;
        int     vidas = 5;
        boolean flag = false;

        Arrays.fill(sol, '\0');
        while (!gana && vidas != 0) {
            imprimirSol(sol, len);
            input = sc.nextLine();
            if (input.length() == 1) {
                for (int i = 0; i < len; i++) {
                    if (aAdivinar.getIncognita().charAt(i) == input.charAt(0)) {
                        sol[i] = input.charAt(0);
                        flag = true;
                    }
                    i++;
                }
                if (!flag)
                    vidas--;
                flag = false;
                gana = comprobar(sol,len);
            } else if (input.length() == len + 1) {
                gana = input.equalsIgnoreCase(aAdivinar.getIncognita());
                if (!gana) vidas --;
            } else {
                System.out.println(Constantes.ERROR);
            }
        }
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
