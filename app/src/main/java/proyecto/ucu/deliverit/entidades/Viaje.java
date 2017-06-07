package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

import java.sql.Timestamp;

public class Viaje implements BaseColumns {
    public static final String TABLE_NAME = "viaje";
    public static final String COLUMN_NAME_PRECIO = "precio";
    public static final String COLUMN_NAME_SUCURSAL = "sucursal";
    public static final String COLUMN_NAME_ESTADO = "estado";
    public static final String COLUMN_FECHA = "fecha";

    public static String ID = "id";
    public static String PRECIO = "precio";
    public static String SUCURSAL = "sucursal";

    private Integer id;
    private Integer precio;
    private Sucursal sucursal;
    private EstadoViaje estado;
    private Timestamp fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public EstadoViaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoViaje estado) {
        this.estado = estado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
