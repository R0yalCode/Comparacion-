package ordenacion.service;

import ordenacion.algorithm.Instrumentation;
import ordenacion.algorithm.SortAlgorithms;
import ordenacion.domain.Record;
import ordenacion.persistence.HistoryRepository;
import ordenacion.util.AnsiColors;
import ordenacion.util.StatsUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Orquesta la carga del CSV, preparación de arrays y ejecución de los algoritmos.
 * Autor: R
 */
public class SortService {
    private final CsvLoader loader = new CsvLoader();

    /**
     * Evaluar los 3 algoritmos y mostrar resultados en consola.
     * @param csvPath ruta al csv
     * @param repetitions R repeticiones (ej: 10)
     * @param history repositorio para guardar entradas de historial
     * @throws Exception en errores de carga/parseo
     */
    public void evaluateAll(String csvPath, int repetitions, HistoryRepository history) throws Exception {
        List<? extends Record> records = loader.load(csvPath);
        System.out.println(AnsiColors.blue("Registros cargados: " + records.size()));

        // Extraer claves o objetos comparables (definimos comparar usando Record::compareTo)
        Record[] base = records.toArray(new Record[0]);

        // Ejecutar cada algoritmo y mostrar resumen
        runAndShow("Inserción", base, repetitions, (arr, instr) -> SortAlgorithms.insertionSort(arr, instr), history, csvPath);
        runAndShow("Selección", base, repetitions, (arr, instr) -> SortAlgorithms.selectionSort(arr, instr), history, csvPath);
        runAndShow("Burbuja", base, repetitions, (arr, instr) -> SortAlgorithms.bubbleSort(arr, instr), history, csvPath);
    }

    public void evaluateSingle(String csvPath, String algorithm, int repetitions, HistoryRepository history) throws Exception {
        List<? extends Record> records = loader.load(csvPath);
        System.out.println(AnsiColors.blue("Registros cargados: " + records.size()));
        Record[] base = records.toArray(new Record[0]);

        switch (algorithm.toLowerCase()) {
            case "insercion":
                runAndShow("Inserción", base, repetitions, (arr, instr) -> SortAlgorithms.insertionSort(arr, instr), history, csvPath);
                break;
            case "seleccion":
                runAndShow("Selección", base, repetitions, (arr, instr) -> SortAlgorithms.selectionSort(arr, instr), history, csvPath);
                break;
            case "burbuja":
                runAndShow("Burbuja", base, repetitions, (arr, instr) -> SortAlgorithms.bubbleSort(arr, instr), history, csvPath);
                break;
            default:
                throw new IllegalArgumentException("Algoritmo desconocido");
        }
    }

    private interface Runner {
        void run(Record[] array, Instrumentation instr);
    }

    private void runAndShow(String name, Record[] base, int R, Runner runner, HistoryRepository history, String csvPath) {
        System.out.println(AnsiColors.magenta("\n=== Ejecutando: " + name + " ==="));
        // Ejecutar R repeticiones, descartando las 3 primeras (si R>3)
        int discard = Math.min(3, R);
        List<Long> times = new ArrayList<>();
        List<Instrumentation> instrs = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            // crear copia de base
            Record[] copy = Arrays.copyOf(base, base.length);
            Instrumentation instr = new Instrumentation();
            long t0 = System.nanoTime();
            runner.run(copy, instr);
            long t1 = System.nanoTime();
            long elapsed = t1 - t0;
            times.add(elapsed);
            instrs.add(instr);
            System.out.println(AnsiColors.gray("Corrida " + (i+1) + ": tiempo(ns)=" + elapsed + " comparisons=" + instr.getComparisons() + " swaps=" + instr.getSwaps()));
        }
        // Descartar primeras 'discard' corridas y calcular mediana del resto
        List<Long> filtered = times.stream().skip(discard).collect(Collectors.toList());
        long median = StatsUtils.medianLong(filtered);
        // Para counts: tomar mediana de comparisons y swaps
        long medianComp = StatsUtils.medianLong(instrs.stream().skip(discard).map(Instrumentation::getComparisons).collect(Collectors.toList()));
        long medianSwaps = StatsUtils.medianLong(instrs.stream().skip(discard).map(Instrumentation::getSwaps).collect(Collectors.toList()));

        System.out.println(AnsiColors.green(String.format("Resultado %s: n=%d  tiempo_mediana(ns)=%d  comparisons_mediana=%d  swaps_mediana=%d",
                name, base.length, median, medianComp, medianSwaps)));

        // Guardar en historial
        history.addEntry(csvPath, name, base.length, median, medianComp, medianSwaps);
    }
}
