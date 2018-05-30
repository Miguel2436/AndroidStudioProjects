package com.example.pedroantonio.activity.datos;

public class Establecimiento {
    private String idEstablecimiento;
    private String latitud;
    private  String longitud;

    public Establecimiento() {
    }

    public Establecimiento(String idEstablecimiento, String latitud, String longitud) {
        this.idEstablecimiento = idEstablecimiento;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdEstablecimiento() {
        return idEstablecimiento;
    }

    public void setIdEstablecimiento(String idEstablecimiento) {
        this.idEstablecimiento = idEstablecimiento;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
