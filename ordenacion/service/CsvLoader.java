package ordenacion.service;

import ordenacion.domain.Cita;
import ordenacion.domain.Inventario;
import ordenacion.domain.Paciente;
import ordenacion.domain.Record;
import ordenacion.persistence.CsvReader;

import java.util.List;

/**
 * Servicio auxiliar para convertir CSV en listas de domain.Record.
 * Autor: R
 */
public class CsvLoader {
    /**
     * Detecta el esquema del CSV leyendo encabezado y devuelve lista de registros.
     * Es compatible con:
     *  - id;apellido;fechaHora  (citas)
     *  - id;apellido;prioridad  (pacientes)
     *  - id;insumo;stock       (inventario)
     *
     * @param path ruta al archivo CSV
     * @return lista de Record (Cita/Paciente/Inventario) en memoria
     * @throws Exception en caso de fallo de parseo
     */
    public List<? extends Record> load(String path) throws Exception {
        List<String[]> rows = CsvReader.readCsv(path);
        if (rows.isEmpty()) throw new IllegalArgumentException("CSV vacío");

        String[] header = rows.get(0);
        if (header.length < 3) throw new IllegalArgumentException("Encabezado inválido");

        String h0 = header[0].trim().toLowerCase();
        String h1 = header[1].trim().toLowerCase();
        String h2 = header[2].trim().toLowerCase();

        if (h0.startsWith("id") && h1.startsWith("apellido") && h2.startsWith("fech")) {
            return Cita.fromRows(rows);
        } else if (h0.startsWith("id") && h1.startsWith("apellido") && h2.startsWith("prior")) {
            return Paciente.fromRows(rows);
        } else if (h0.startsWith("id") && h1.startsWith("insumo") && h2.startsWith("stock")) {
            return Inventario.fromRows(rows);
        } else {
            throw new IllegalArgumentException("Formato de CSV no reconocido por encabezado: " + String.join(";", header));
        }
    }
}
