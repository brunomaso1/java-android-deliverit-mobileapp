package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

public class Pedido implements BaseColumns {
    public static final String TABLE_NAME = "pedido";
    public static final String COLUMN_NAME_VIAJE = "viaje";
    public static final String COLUMN_NAME_DETALLE = "detalle";
    public static final String COLUMN_NAME_CLIENTE = "cliente";

    private Integer id;
    private Viaje viaje;
    private String detalle;
    private Cliente cliente;

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
