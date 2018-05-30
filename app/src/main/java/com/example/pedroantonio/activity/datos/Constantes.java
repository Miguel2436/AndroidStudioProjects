package com.example.pedroantonio.activity.datos;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Constantes {
    private final String IP = "http://35.231.227.16/webService/";
    private final String passwordP="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)([A-Za-z\\d]|[^ ]){8,}";;
    private Pattern patternPassword=Pattern.compile(passwordP);
    private final String FileNameSharedPreferences="Apptivity";
    public String getIP() {
        return IP;
    }
    public String getFileNameSharedPreferences() {
        return FileNameSharedPreferences;
    }

    public boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public boolean validarPassword(String password)
    {
        if(patternPassword.matcher(password).matches()) return true;
        else return false;
    }
    public boolean validarPasswordIgual(String password, String password2)
    {
        if(password.equals(password2)) return true;
        else return false;
    }
}
