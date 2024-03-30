package domain;
import java.util.Scanner;
import common.Constantes;
import org.yaml.snakeyaml.scanner.ScannerException;

public class Juego {
    //pensar en los atributos que definen el estado del juego en ese instante para que que si lo paran se pueda recuperar
    private Palabra aAdivinar; //o el String directamente
    private Jugador jugador;
    private int intentos;
    private int dificultad; //opcional, aquí o por elemento.

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        String input;
        String output;

        imprimirOutput(output);
        input = sc.nextLine();
        if (input.isEmpty()) {
            System.out.println(Constantes.ERROR);
            jugar();
        } else if (input.length() == 1) {
            for (int i = 0; i < aAdivinar.toString().length(); i++) {

            }
        }
    }

    private void imprimirOutput(char[] output) {
        for (int i = 0; i < ft_strlen(output); i++) {
            if (output[i]  == '\0')
        }
    }
    private int ft_strlen(char[] string) {
        int i = 0;

        while (string[i] != '\0')
            i++;
        return i;
    }
}
