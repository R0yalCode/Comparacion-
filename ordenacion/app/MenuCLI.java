package ordenacion.app;

import ordenacion.service.SortService;
import ordenacion.persistence.HistoryRepository;
import ordenacion.util.AnsiColors;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Interfaz de texto (CLI) del programa.
 * Muestra el menú principal, recibe rutas y llama a servicios.
 * Autor: R
 */
public class MenuCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final SortService sortService = new SortService();
    private final HistoryRepository history = new HistoryRepository();

    // Ruta por defecto donde se espera encontrar los 4 CSV que ya generaste
private final Path defaultDatasetsPath = Paths.get(System.getProperty("user.dir")).resolve("resources");

    public void loop() {
        while (true) {
            System.out.println();
            System.out.println(AnsiColors.cyan("Menú principal:"));
            System.out.println("1) Usar CSVs ya guardados (ruta por defecto: " + defaultDatasetsPath + ")");
            System.out.println("2) Cargar CSV manualmente (introduce ruta)");
            System.out.println("3) Ver historial de ordenamientos");
            System.out.println("4) Exportar historial a TXT");
            System.out.println("0) Salir");
            System.out.print(AnsiColors.yellow("Selecciona una opción: "));
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    optionUseDefault();
                    break;
                case "2":
                    optionLoadManual();
                    break;
                case "3":
                    System.out.println(history.listAll());
                    break;
                case "4":
                    System.out.print("Ruta destino (ejemplo: resources/history_export.txt): ");
                    String out = scanner.nextLine().trim();
                    history.exportToFile(out);
                    System.out.println(AnsiColors.green("Historial exportado a: " + out));
                    break;
                case "0":
                    System.out.println(AnsiColors.green("Saliendo..."));
                    return;
                default:
                    System.out.println(AnsiColors.red("Opción inválida."));
            }
        }
    }

    private void optionUseDefault() {
        System.out.println(AnsiColors.cyan("Archivos disponibles en: " + defaultDatasetsPath));
        System.out.println("1) citas_100.csv");
        System.out.println("2) citas_100_casi_ordenadas.csv");
        System.out.println("3) pacientes_500.csv");
        System.out.println("4) inventario_500_inverso.csv");
        System.out.print("Selecciona qué archivo usar (1-4): ");
        String sel = scanner.nextLine().trim();
        Path file = null;
        switch (sel) {
            case "1": file = defaultDatasetsPath.resolve("citas_100.csv"); break;
            case "2": file = defaultDatasetsPath.resolve("citas_100_casi_ordenadas.csv"); break;
            case "3": file = defaultDatasetsPath.resolve("pacientes_500.csv"); break;
            case "4": file = defaultDatasetsPath.resolve("inventario_500_inverso.csv"); break;
            default:
                System.out.println(AnsiColors.red("Selección incorrecta."));
                return;
        }
        callEvaluationMenu(file.toString());
    }

    private void optionLoadManual() {
        System.out.print("Introduce la ruta completa del CSV: ");
        String path = scanner.nextLine().trim();
        callEvaluationMenu(path);
    }

    private void callEvaluationMenu(String csvPath) {
        System.out.println(AnsiColors.cyan("Archivo seleccionado: " + csvPath));
        System.out.println("1) Evaluar los 3 algoritmos");
        System.out.println("2) Evaluar con Inserción");
        System.out.println("3) Evaluar con Selección");
        System.out.println("4) Evaluar con Burbuja");
        System.out.print("Selecciona: ");
        String opt = scanner.nextLine().trim();

        // Default R = 10 (repeticiones), descartar 3 -> mediana de las 7 restantes
        int R = 10;
        try {
            switch (opt) {
                case "1":
                    sortService.evaluateAll(csvPath, R, history);
                    break;
                case "2":
                    sortService.evaluateSingle(csvPath, "insercion", R, history);
                    break;
                case "3":
                    sortService.evaluateSingle(csvPath, "seleccion", R, history);
                    break;
                case "4":
                    sortService.evaluateSingle(csvPath, "burbuja", R, history);
                    break;
                default:
                    System.out.println(AnsiColors.red("Opción inválida."));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.red("Error durante la evaluación: " + e.getMessage()));
            e.printStackTrace();
        }
    }
}
