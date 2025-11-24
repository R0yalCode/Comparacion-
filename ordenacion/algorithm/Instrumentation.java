package ordenacion.algorithm;

/**
 * Contador de instrumentación para algoritmos de ordenación.
 * Mantiene comparisons y swaps; útil para análisis.
 * Autor: R
 */
public class Instrumentation {
    private long comparisons = 0;
    private long swaps = 0;

    public void incComparisons() { comparisons++; }
    public void incSwaps() { swaps++; }

    public void addComparisons(long v) { comparisons += v; }
    public void addSwaps(long v) { swaps += v; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
}
