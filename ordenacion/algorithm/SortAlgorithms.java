package ordenacion.algorithm;

import ordenacion.domain.Record;

/**
 * Implementación de Burbuja (con corte temprano), Selección e Inserción.
 * Los métodos modifican directamente el array y actualizan Instrumentation.
 * Autor: R
 */
public class SortAlgorithms {

    /**
     * Burbuja con corte temprano.
     * @param arr array de Record (modificado in-place)
     * @param instr instrumentación (comparisons, swaps)
     */
    public static void bubbleSort(Record[] arr, Instrumentation instr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                instr.incComparisons();
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1, instr);
                    swapped = true;
                }
            }
            if (!swapped) break; // corte temprano
        }
    }

    /**
     * Selección (selection sort).
     */
    public static void selectionSort(Record[] arr, Instrumentation instr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                instr.incComparisons();
                if (arr[j].compareTo(arr[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) swap(arr, i, minIdx, instr);
        }
    }

    /**
     * Inserción (insertion sort).
     */
    public static void insertionSort(Record[] arr, Instrumentation instr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Record key = arr[i];
            int j = i - 1;
            // mover elementos mayores una posición a la derecha
            while (j >= 0) {
                instr.incComparisons();
                if (arr[j].compareTo(key) > 0) {
                    arr[j + 1] = arr[j];
                    instr.incSwaps(); // contamos movimiento como swap/shift
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
            // No contamos la escritura final como swap adicional
        }
    }

    private static void swap(Record[] arr, int i, int j, Instrumentation instr) {
        Record tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        instr.incSwaps();
    }
}
