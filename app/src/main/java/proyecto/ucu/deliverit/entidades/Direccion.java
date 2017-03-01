package proyecto.ucu.deliverit.entidades;

import android.provider.BaseColumns;

/**
 * Created by DeliverIT on 20/02/2017.
 */

public class Direccion implements BaseColumns {
    public static final String TABLE_NAME = "direccion";
    public static final String COLUMN_NAME_CALLE = "calle";
    public static final String COLUMN_NAME_NRO_PUERTA = "nroPuerta";
    public static final String COLUMN_NAME_ESQUINA = "esquina";
    public static final String COLUMN_NAME_APARTAMENTO = "apartamento";
    public static final String COLUMN_NAME_LATITUD = "latitud";
    public static final String COLUMN_NAME_LONGITUD = "longitud";

    private Integer id;
    private String calle;
    private Integer nroPuerta;
    private Short apartamento;
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

    public Short getApartamento() {
        return apartamento;
    }

    public void setApartamento(Short apartamento) {
        this.apartamento = apartamento;
    }
}
