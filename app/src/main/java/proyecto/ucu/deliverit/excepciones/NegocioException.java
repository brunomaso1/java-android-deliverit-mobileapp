package proyecto.ucu.deliverit.excepciones;

public class NegocioException extends Exception {
    private String msg;

    public NegocioException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
