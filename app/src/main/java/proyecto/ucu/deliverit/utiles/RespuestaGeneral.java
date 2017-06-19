package proyecto.ucu.deliverit.utiles;

public class RespuestaGeneral {
    public static final int CODIGO_OK = 0;
    public static final int CODIGO_ERROR = -1;
    public static final int CODIGO_ERROR_VALOR_NULO = -2;
    public static final int CODIGO_ERROR_VALOR_INCORRECTO = -3;
    public static final int CODIGO_ERROR_YA_EXISTE = -4;

    private Integer codigo;
    private String mensaje;
    private String objeto;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
}
