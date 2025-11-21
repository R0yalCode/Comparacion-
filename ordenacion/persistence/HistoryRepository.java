package ordenacion.persistence;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio simple en memoria + persistente (archivo) para historial de ejecuciones.
 * Formato interno: CSV semicolon
 * Autor: R
 */
public class HistoryRepository {
    private final List<String> entries = new ArrayList<>();
    private final Path defaultFile = Paths.get("history.txt");

    public HistoryRepository() {
        // cargar si existe (no obligatorio)
        if (Files.exists(defaultFile)) {
            try {
                List<String> lines = Files.readAllLines(defaultFile, StandardCharsets.UTF_8);
                entries.addAll(lines);
            } catch (IOException ignored) {}
        }
    }

    /**
     * Añade una entrada al historial (y guarda en archivo por defecto).
     */
    public synchronized void addEntry(String csvPath, String algorithm, int n, long tiempoNs, long comparisons, long swaps) {
        String line = String.format("%s;%s;%s;n=%d;tiempo_ns=%d;comparisons=%d;swaps=%d",
                Instant.now().toString(), csvPath, algorithm, n, tiempoNs, comparisons, swaps);
        entries.add(line);
        // append to file
        try {
            Files.writeString(defaultFile, line + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // no lanzar para que no rompa la evaluación
            e.printStackTrace();
        }
    }

    public synchronized String listAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Historial de ordenamientos ===\n");
        for (String e : entries) sb.append(e).append("\n");
        return sb.toString();
    }

    public synchronized void exportToFile(String pathStr) {
        Path out = Paths.get(pathStr);
        try {
            Files.createDirectories(out.getParent() == null ? Paths.get(".") : out.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (String e : entries) bw.write(e + System.lineSeparator());
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo exportar historial: " + ex.getMessage(), ex);
        }
    }
}
