package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by Juancho on 31/01/2017.
 */

public class Delivery implements BaseColumns {
    public static final String TABLE_NAME = "delivery";
    public static final String COLUMN_NAME_NOMBRE_USUARIO = "nombreUsuario";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_MAIL = "mail";
    public static final String COLUMN_NAME_TELEFONO = "telefono";
    public static final String COLUMN_NAME_NOMBRE = "nombre";
    public static final String COLUMN_NAME_CUENTA_RED_PAGOS = "cuentaRedPagos";
    public static final String COLUMN_NAME_FOTO_PERFIL = "fotoPerfil";
}
