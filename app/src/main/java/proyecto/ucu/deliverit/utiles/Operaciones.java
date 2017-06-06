package proyecto.ucu.deliverit.utiles;

import android.util.Base64;

public class Operaciones {

    public static Retorno validarDatos(String nombreUsuario, String password, String telefono, Integer cuentaRedPagos) {
        Retorno retorno = new Retorno();
        if (nombreUsuario == null || nombreUsuario.equals("")) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
        } else if (password == null || password.equals("")) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
            retorno.setDescripcion(Valores.DESCRIPCION_ERROR_VACIO_O_NULO);
        } else if (telefono == null || telefono.equals("")) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
        } else if (cuentaRedPagos == null || cuentaRedPagos == 0) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
        } else {
            retorno = validarPassword(password);

            if (retorno.getCodigo().equals(Valores.CODIGO_EXITO)) {
                retorno = validarTelefono(telefono);
            }
        }

        return retorno;
    }

    public static Retorno validarPassword(String password) {
        Retorno retorno = new Retorno();

        if (password == null || password.equals("")) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
            retorno.setDescripcion(Valores.DESCRIPCION_ERROR_VACIO_O_NULO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
        } else if (password.length() < 8) {
            retorno.setCodigo(Valores.CODIGO_ERROR_PASSWORD);
            retorno.setDescripcion(Valores.DESCRIPCION_ERROR_LARGO_PASSWORD);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
        } else if (!tieneNumero(password)) {
            retorno.setCodigo(Valores.CODIGO_ERROR_PASSWORD);
            retorno.setDescripcion(Valores.DESCIPRCION_ERROR_PASSWORD_SIN_NUMERO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
        } else if (!tieneLetra(password)) {
            retorno.setCodigo(Valores.CODIGO_ERROR_PASSWORD);
            retorno.setDescripcion(Valores.DESCIPRCION_ERROR_PASSWORD_SIN_LETRA);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
        } else if (!tieneCaracteresEspeciales(password)) {
            retorno.setCodigo(Valores.CODIGO_ERROR_PASSWORD);
            retorno.setDescripcion(Valores.DESCIPRCION_ERROR_PASSWORD_SIN_CARACTER_ESPECIAL);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
        } else {
            retorno.setCodigo(Valores.CODIGO_EXITO);
            retorno.setMensaje(Valores.MENSAJE_EXITO);
            retorno.setDescripcion(Valores.DESCRIPCION_EXITO);
        }
        return retorno;
    }

    public static Retorno validarTelefono (String telefono) {
        Retorno retorno = new Retorno();

        if (telefono == null || telefono.equals("")) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
            retorno.setDescripcion(Valores.DESCRIPCION_ERROR_VACIO_O_NULO);
        } else if (telefono.length() != 9) {
            retorno.setCodigo(Valores.CODIGO_ERROR_TELEFONO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
            retorno.setDescripcion(Valores.DESCIPRCION_ERROR_TELEFONO);
        } else if (!(String.valueOf(telefono.charAt(0)).equals("0")) || !(String.valueOf(telefono.charAt(1)).equals("9"))) {
            retorno.setCodigo(Valores.CODIGO_ERROR_TELEFONO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
            retorno.setDescripcion(Valores.DESCIPRCION_ERROR_TELEFONO);
        } else {
            retorno.setCodigo(Valores.CODIGO_EXITO);
            retorno.setMensaje(Valores.MENSAJE_EXITO);
            retorno.setDescripcion(Valores.DESCRIPCION_EXITO);
        }
        return retorno;
    }

    public static Retorno validarLatitudLongitud (Double latitud, Double longitud) {
        Retorno retorno = new Retorno();

        if (latitud == null || longitud == null) {
            retorno.setCodigo(Valores.CODIGO_ERROR_NULO_O_VACIO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
            retorno.setDescripcion(Valores.DESCRIPCION_ERROR_VACIO_O_NULO);
        } else if (latitud >= 0 || longitud >= 0) {
            retorno.setCodigo(Valores.CODIGO_ERROR_VALOR_INCORRECTO);
            retorno.setMensaje(Valores.MENSAJE_ERROR);
            retorno.setDescripcion(Valores.DESCRIPCION_ERROR_VALOR_INCORRECTO);
        } else {
            retorno.setCodigo(Valores.CODIGO_EXITO);
            retorno.setMensaje(Valores.MENSAJE_EXITO);
            retorno.setDescripcion(Valores.DESCRIPCION_EXITO);
        }

        return retorno;
    }

    public static boolean tieneNumero(String cadena) {
        return cadena.matches(".*\\d+.*");
    }

    public static boolean tieneLetra(String cadena) {
        return cadena.matches(".*[a-z].*");
    }

    public static boolean tieneCaracteresEspeciales(String cadena) {
        return cadena.matches(".*[^a-z0-9].*");
    }

    public static byte[] decodeImage (String base64img) {
        return Base64.decode(base64img, Base64.DEFAULT);
    }

}
