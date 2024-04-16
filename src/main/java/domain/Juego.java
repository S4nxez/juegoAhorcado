package domain;
import java.io.Serializable;
import java.util.Arrays;

import common.Constantes;
import lombok.Getter;

@Getter
public class Juego implements Serializable {
    private final Palabra aAdivinar;
    private int intentos;
    char[]  sol;

    public Juego(Palabra aAdivinar) {
        this.aAdivinar = aAdivinar;
        this.intentos = 0;
        this.sol = new char[aAdivinar.getIncognita().length()];
        Arrays.fill(this.sol, '\0');
    }
    public String jugar(String input) {
        boolean flag = true;
        int     len = aAdivinar.getIncognita().length();

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
