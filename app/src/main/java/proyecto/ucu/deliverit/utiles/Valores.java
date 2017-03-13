package proyecto.ucu.deliverit.utiles;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class Valores {
    public static long TIMEOUT_WS = 20;
    public static long TIMER_VIAJES = 60000;

    public static final String CODIGO_EXITO = "0";
    public static final String CODIGO_ERROR_NULO_O_VACIO = "-1";
    public static final String CODIGO_ERROR_PASSWORD = "-2";
    public static final String CODIGO_ERROR_TELEFONO = "-3";
    public static final String CODIGO_ERROR_VALOR_INCORRECTO = "-4";
    public static final String MENSAJE_EXITO = "EXITO";
    public static final String MENSAJE_ERROR = "ERROR";
    public static final String DESCRIPCION_EXITO = "La operación se realizó correctamente";
    public static final String DESCRIPCION_ERROR_VACIO_O_NULO = "Campo vacío o nulo";
    public static final String DESCRIPCION_ERROR_LARGO_PASSWORD = "El largo de la contraseña debe ser mayor o igual a 8 caracteres";
    public static final String DESCIPRCION_ERROR_PASSWORD_SIN_NUMERO = "La contraseña debe contener al menos un número";
    public static final String DESCIPRCION_ERROR_PASSWORD_SIN_LETRA = "La contraseña debe contener al menos una letra";
    public static final String DESCIPRCION_ERROR_PASSWORD_SIN_CARACTER_ESPECIAL
            = "La contraseña debe contener al menos un caracter especial";
    public static final String DESCIPRCION_ERROR_TELEFONO = "El celular ingresado no es válido";
    public static final String DESCRIPCION_ERROR_VALOR_INCORRECTO = "Se ha encontrado un valor incorrecto";

    public static final int NOTIFICATION_ID = 1;
    public static final String NOTIFICATOIN_ID_TEXTO = "notificationID";
    public static final String VIAJE = "viaje";
    public static final String PEDIDOS = "pedidos";

    public static final String DIRECCION_ID = "id";
    public static final String DIRECCION_CALLE = "calle";
    public static final String DIRECCION_NRO_PUERTA = "nroPuerta";
    public static final String DIRECCION_ESQUINA = "esquina";
    public static final String DIRECCION_LATITUD = "latitud";
    public static final String DIRECCION_LONGITUD = "longitud";
    public static final String DIRECCION_APARTAMENTO = "apartamento";

    public static final String RESTARURAN_ID = "id";
    public static final String RESTAURANT_RUT = "rut";
    public static final String RESTAURANT_RAZON_SOCIAL = "razonSocial";

    public static final String SUCURSAL_ID = "id";
    public static final String SUCURSAL_DIRECCION = "direccion";
    public static final String SUCURSAL_RESTAURANT = "restaurant";
    public static final String SUCURSAL_SUCURSAL_PK = "sucursalPK";

    public static final String VIAJE_PARA_TI = "Tiene un nuevo viaje para ti!!";

    public static final String URL_WS = "http://192.168.43.171:8080/BackCore/ws/";
    public static final String URL_ACEPAR_VIAJE = URL_WS + " viaje/aceptarViaje/";
    public static final String URL_FINALIZAR_VIAJE = URL_WS + "viaje/finalizarViaje/";
    public static final String URL_OBTENER_PEDIDOS_POR_VIAJE = URL_WS + "findPedidosPorViaje/";
    public static final String BARRA_DIAGONAL = "/";
}
