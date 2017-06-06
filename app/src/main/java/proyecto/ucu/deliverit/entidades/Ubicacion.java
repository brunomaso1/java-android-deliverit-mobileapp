package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

public class Ubicacion implements BaseColumns {
    public static final String TABLE_NAME = "ubicacion";
    public static final String COLUMN_NAME_LATITUD = "latitud";
    public static final String COLUMN_NAME_LONGITUD = "longitud";

    private Short id;
    private Double latitud;
    private Double longitud;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
