package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by JMArtegoytia on 03/03/2017.
 */

public class Vehiculo implements BaseColumns {
    public static final String VEHICULO_POR_DEFECTO_SPINNER = "Automotor";

    public static final String TABLE_NAME = "vehiculo";
    public static final String COLUMN_NAME_DESCRIPCION = "descripcion";

    private Short id;
    private String descripcion;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
