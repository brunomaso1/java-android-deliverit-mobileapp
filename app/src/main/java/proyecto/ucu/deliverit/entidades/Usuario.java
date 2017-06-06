package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

public class Usuario implements BaseColumns {
    public static final String TABLE_NAME = "usuario";
    public static final String COLUMN_NAME_NOMBRE_USUARIO = "nombreUsuario";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_MAIL = "mail";
    public static final String COLUMN_NAME_TELEFONO = "telefono";
    public static final String COLUMN_NAME_CUENTA_RED_PAGOS = "cuentaRedPagos";
    public static final String COLUMN_NAME_FOTO_PERFIL = "fotoPerfil";

    private Integer id;
    private String nombre;
    private String password;
    private String telefono;
    private String mail;
    private Integer cuentaRedPagos;
    private String foto;


    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getCuentaRedPagos() {
        return cuentaRedPagos;
    }

    public void setCuentaRedPagos(Integer cuentaRedPagos) {
        this.cuentaRedPagos = cuentaRedPagos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
