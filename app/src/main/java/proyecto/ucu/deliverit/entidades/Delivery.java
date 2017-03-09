package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class Delivery implements BaseColumns {
    public static final String TABLE_NAME = "delivery";

    public static final String COLUMN_NAME_TOKEN = "token";
    public static final String COLUMN_NAME_VEHICULO = "vehiculo";
    public static final String COLUMN_NAME_USUARIO = "usuario";
    public static final String COLUMN_NAME_UBICACION = "ubicacion";

    private Integer id;
    private String token;
    private Vehiculo vehiculo;
    private Usuario usuario;
    private Ubicacion ubicacion;
    private Short calificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Short getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Short calificacion) {
        this.calificacion = calificacion;
    }
}
