package proyecto.ucu.deliverit.entidades;

/**
 * Created by DeliverIT on 20/02/2017.
 */

public class Ubicacion {

    private Short id;
    private Double latitud;
    private Double longitud;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
