package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

public class Sucursal implements BaseColumns {
    public static final String TABLE_NAME = "sucursal";
    public static final String COLUMN_NAME_DIRECCION = "direccion";
    public static final String COLUMN_NAME_RESTAURANT = "restaurant";

    private Integer id;
    private Direccion direccion;
    private Restaurant restaurant;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
