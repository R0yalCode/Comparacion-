package ordenacion.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Registro Inventario: id;insumo;stock
 * Comparable por stock (numérico).
 * Autor: R
 */
public class Inventario implements Record {
    private final String id;
    private final String insumo;
    private final int stock;

    public Inventario(String id, String insumo, int stock) {
        this.id = id;
        this.insumo = insumo;
        this.stock = stock;
    }

    public static List<Inventario> fromRows(List<String[]> rows) {
        List<Inventario> out = new ArrayList<>();
        boolean first = true;
        for (String[] r : rows) {
            if (first) { first=false; continue; }
            String id = r[0].trim();
            String in = r[1].trim();
            int st = Integer.parseInt(r[2].trim());
            out.add(new Inventario(id, in, st));
        }
        return out;
    }

    @Override
    public int compareTo(Record o) {
        if (!(o instanceof Inventario)) return 0;
        Inventario other = (Inventario) o;
        return Integer.compare(this.stock, other.stock);
    }

    @Override
    public String display() { return id + ";" + insumo + ";" + stock; }
}
