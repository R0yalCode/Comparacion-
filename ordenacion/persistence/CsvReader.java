package ordenacion.persistence;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Lector CSV sencillo que usa separador ';' y UTF-8 (sin BOM).
 * Devuelve filas tokenizadas (String[]). No imprime nada.
 * Autor: R
 */
public class CsvReader {
    /**
     * Lee un CSV con separador ';' y devuelve la lista de filas (incluyendo encabezado).
     * @param pathString ruta al archivo
     * @return lista de String[] (cada fila)
     * @throws Exception si hay problema de lectura
     */
    public static List<String[]> readCsv(String pathString) throws Exception {
        Path path = Path.of(pathString);
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // split simple por ';' (conserva comas internas)
                String[] tokens = line.split(";", -1);
                rows.add(tokens);
            }
        }
        return rows;
    }
}
