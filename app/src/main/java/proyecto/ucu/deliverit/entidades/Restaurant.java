package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

public class Restaurant implements BaseColumns {
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_NAME_RAZON_SOCIAL = "razonSocial";
    public static final String COLUMN_NAME_FOTO = "foto";

    private Integer id;
    private String razonSocial;
    private Usuario usuario;
    private String foto;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
