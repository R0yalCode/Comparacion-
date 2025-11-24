package ordenacion.util;

import java.util.Collections;
import java.util.List;

/**
 * Utilidades estadísticas simples.
 * Autor: R
 */
public class StatsUtils {
    public static long medianLong(List<Long> values) {
        if (values.isEmpty()) return 0L;
        Collections.sort(values);
        int n = values.size();
        if (n % 2 == 1) return values.get(n / 2);
        return (values.get(n/2 - 1) + values.get(n/2)) / 2;
    }
}
