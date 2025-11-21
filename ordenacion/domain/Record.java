package ordenacion.domain;

/**
 * Interfaz para registros que se pueden comparar por la "clave" deseada.
 * Autor: R
 */
public interface Record extends Comparable<Record> {
    /**
     * Devuelve una representación simple para logs.
     */
    String display();
}
