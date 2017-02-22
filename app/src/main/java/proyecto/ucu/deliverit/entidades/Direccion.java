package proyecto.ucu.deliverit.entidades;

/**
 * Created by JMArtegoytia on 20/02/2017.
 */

public class Direccion {

    private Integer id;
    private String calle;
    private Integer nroPuerta;
    private String esquina;
    private Double latitud;
    private Double longitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNroPuerta() {
        return nroPuerta;
    }

    public void setNroPuerta(Integer nroPuerta) {
        this.nroPuerta = nroPuerta;
    }

    public String getEsquina() {
        return esquina;
    }

    public void setEsquina(String esquina) {
        this.esquina = esquina;
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
