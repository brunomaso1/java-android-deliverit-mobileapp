package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by DeliverIT on 20/02/2017.
 */

public class Sucursal implements BaseColumns {
    public static final String TABLE_NAME = "sucursal";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_NAME_DIRECCION = "direccion";
    public static final String COLUMN_NAME_RESTAURANT = "restaurant";

    private Integer id;
    private String nombre;
    private Direccion direccion;
    private Restaurant restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
