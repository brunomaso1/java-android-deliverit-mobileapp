package proyecto.ucu.deliverit.entidades;

/**
 * Created by JMArtegoytia on 20/02/2017.
 */

public class Viaje {

    public static String ID = "id";
    public static String PRECIO = "precio";
    public static String SUCURSAL = "sucursal";

    private Integer id;
    private Integer precio;
    private Sucursal sucursal;

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
}
