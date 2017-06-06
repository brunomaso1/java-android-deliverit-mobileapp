package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

public class Viaje implements BaseColumns {
    public static final String TABLE_NAME = "viaje";
    public static final String COLUMN_NAME_PRECIO = "precio";
    public static final String COLUMN_NAME_DELIVERY = "delivery";
    public static final String COLUMN_NAME_SUCURSAL = "sucursal";
    public static final String COLUMN_NAME_ESTADO = "estado";

    public static String ID = "id";
    public static String PRECIO = "precio";
    public static String SUCURSAL = "sucursal";

    private Integer id;
    private Integer precio;
    private Sucursal sucursal;
    private EstadoViaje estado;

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
}
