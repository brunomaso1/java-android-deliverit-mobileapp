package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by DeliverIT on 20/02/2017.
 */

public class Restaurant implements BaseColumns {
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_NAME_RUT = "rut";
    public static final String COLUMN_NAME_RAZON_SOCIAL = "razonSocial";
    public static final String COLUMN_NAME_USUARIO = "usuario";

    private Integer id;
    private Integer rut;
    private String razonSocial;
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
