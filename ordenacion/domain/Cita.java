package ordenacion.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Registro Cita: id;apellido;fechaHora (YYYY-MM-DDTHH:MM)
 * Comparable por fechaHora.
 * Autor: R
 */
public class Cita implements Record {
    private final String id;
    private final String apellido;
    private final LocalDateTime fechaHora;
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public Cita(String id, String apellido, LocalDateTime fechaHora) {
        this.id = id;
        this.apellido = apellido;
        this.fechaHora = fechaHora;
    }

    public static List<Cita> fromRows(List<String[]> rows) {
        List<Cita> out = new ArrayList<>();
        boolean first = true;
        for (String[] r : rows) {
            if (first) { first=false; continue; }
            String id = r[0].trim();
            String ap = r[1].trim();
            LocalDateTime dt = LocalDateTime.parse(r[2].trim(), F);
            out.add(new Cita(id, ap, dt));
        }
        return out;
    }

    @Override
    public int compareTo(Record o) {
        if (!(o instanceof Cita)) return 0;
        Cita other = (Cita) o;
        return this.fechaHora.compareTo(other.fechaHora);
    }

    @Override
    public String display() { return id + ";" + apellido + ";" + fechaHora.format(F); }
}
