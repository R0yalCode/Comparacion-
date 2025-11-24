package ordenacion.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Registro Paciente: id;apellido;prioridad
 * Comparable por apellido (alfabético).
 * Autor: R
 */
public class Paciente implements Record {
    private final String id;
    private final String apellido;
    private final int prioridad;

    public Paciente(String id, String apellido, int prioridad) {
        this.id = id;
        this.apellido = apellido;
        this.prioridad = prioridad;
    }

    public static List<Paciente> fromRows(List<String[]> rows) {
        List<Paciente> out = new ArrayList<>();
        boolean first = true;
        for (String[] r : rows) {
            if (first) { first=false; continue; }
            String id = r[0].trim();
            String ap = r[1].trim();
            int pr = Integer.parseInt(r[2].trim());
            out.add(new Paciente(id, ap, pr));
        }
        return out;
    }

    @Override
    public int compareTo(Record o) {
        if (!(o instanceof Paciente)) return 0;
        Paciente other = (Paciente) o;
        return this.apellido.compareToIgnoreCase(other.apellido);
    }

    @Override
    public String display() { return id + ";" + apellido + ";" + prioridad; }
}
