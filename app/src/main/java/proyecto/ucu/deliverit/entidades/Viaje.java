package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by DevlierIT on 20/02/2017.
 */

public class Viaje implements BaseColumns {
    public static final String TABLE_NAME = "viaje";
    public static final String COLUMN_NAME_CALIFICAION = "calificacion";
    public static final String COLUMN_NAME_PRECIO = "precio";
    public static final String COLUMN_NAME_DELIVERY = "delivery";
    public static final String COLUMN_NAME_SUCURSAL = "sucursal";
    public static final String COLUMN_NAME_RESTAURANT = "restaurant";

    public static String ID = "id";
    public static String PRECIO = "precio";
    public static String SUCURSAL = "sucursal";

    private Integer id;
    private Integer precio;
    private Sucursal sucursal;
    private Short calificacion;
    private Restaurant restaurant;

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

    public Short getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Short calificacion) {
        this.calificacion = calificacion;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
