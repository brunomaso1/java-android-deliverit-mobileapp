package proyecto.ucu.deliverit.utiles;

/**
 * Created by Juancho on 31/01/2017.
 */

public class Valores {
    public static long TIMEOUT_WS = 20;
    public static long TIMER_VIAJES = 60000;

    public static String CODIGO_EXITO = "0";
    public static String CODIGO_ERROR_NULO_O_VACIO = "-1";
    public static String CODIGO_ERROR_PASSWORD = "-2";
    public static String CODIGO_ERROR_TELEFONO = "-3";
    public static String MENSAJE_EXITO = "EXITO";
    public static String MENSAJE_ERROR = "ERROR";
    public static String DESCRIPCION_EXITO = "La operación se realizó correctamente";
    public static String DESCRIPCION_ERROR_VACIO_O_NULO = "Campo vacío o nulo";
    public static String DESCRIPCION_ERROR_LARGO_PASSWORD = "El largo de la contraseña debe ser mayor o igual a 8 caracteres";
    public static String DESCIPRCION_ERROR_PASSWORD_SIN_NUMERO = "La contraseña debe contener al menos un número";
    public static String DESCIPRCION_ERROR_PASSWORD_SIN_LETRA = "La contraseña debe contener al menos una letra";
    public static String DESCIPRCION_ERROR_PASSWORD_SIN_CARACTER_ESPECIAL
            = "La contraseña debe contener al menos un caracter especial";
    public static String DESCIPRCION_ERROR_TELEFONO = "El celular ingresado no es válido";
}
