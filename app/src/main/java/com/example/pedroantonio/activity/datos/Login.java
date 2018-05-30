package com.example.pedroantonio.activity.datos;

public class Login {
    private String Username;
    private String Correo;
    private String Password;

    public Login() {
    }

    public Login(String username, String correo, String password) {
        Username = username;
        Correo = correo;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
