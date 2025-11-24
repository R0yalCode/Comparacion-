package ordenacion.app;

import ordenacion.util.AnsiColors;

/**
 * Punto de entrada de la aplicación.
 * @author R
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(AnsiColors.green("=== Benchmark de Ordenación (Burbuja / Selección / Inserción) ==="));
        MenuCLI menu = new MenuCLI();
        menu.loop();
    }
}
