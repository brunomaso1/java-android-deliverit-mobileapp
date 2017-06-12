package proyecto.ucu.deliverit.custom_adapters;

public class Sidebar {
    private int idIcono;
    private String texto;

    public Sidebar(int idIcono, String texto) {
        this.idIcono = idIcono;
        this.texto = texto;
    }

    public int getIdIcono() {
        return idIcono;
    }

    public void setIdIcono(int idIcono) {
        this.idIcono = idIcono;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
