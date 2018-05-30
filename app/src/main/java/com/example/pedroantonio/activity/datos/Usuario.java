package com.example.pedroantonio.activity.datos;

public class Usuario {
    private String nombre;
    private  String ApellidoPaterno;
    private  String ApellidoMaterno;
    private  String Imagen;
    private  boolean Tipo;
    private  int Login;

    public Usuario()
    {

    }

    public Usuario(String nombre, String apellidoPaterno, String apellidoMaterno, String imagen, boolean tipo) {
        this.nombre = nombre;
        ApellidoPaterno = apellidoPaterno;
        ApellidoMaterno = apellidoMaterno;
        Imagen = imagen;
        Tipo = tipo;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        ApellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        ApellidoMaterno = apellidoMaterno;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public boolean isTipo() {
        return Tipo;
    }

    public void setTipo(boolean tipo) {
        Tipo = tipo;
    }

    public int getLogin() {
        return Login;
    }

    public void setLogin(int login) {
        Login = login;
    }
}
