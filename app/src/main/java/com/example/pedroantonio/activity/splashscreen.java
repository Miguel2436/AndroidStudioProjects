package com.example.pedroantonio.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class splashscreen extends Activity {

    boolean conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences= getSharedPreferences("Preferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String usuario="";
        usuario=sharedPreferences.getString("Usuario", "");
        if (!usuario.isEmpty())
        {
         pantallaInicio();

        }else {
        //TextView description;
        //Typeface AYearWithoutRain;
        //description = (TextView)findViewById(R.id.textViewDesc);
        //AYearWithoutRain = Typeface.createFromAsset(getAssets(),"AYearWithoutRain.ttf");
        //description.setTypeface(AYearWithoutRain);

        if(!isConnected(splashscreen.this)){
            buildDialog(splashscreen.this).show();

        }
        else{
            setContentView(R.layout.activity_splashscreen);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intet = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(intet);
                }
            }, 3000);
        }}
       // checkconection();
    }

    public boolean isConnected(Context context){

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else return false;
    }

    public AlertDialog.Builder buildDialog(Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No hay conexión a internet");
        builder.setMessage("Necesitas una señal Wi-Fi o datos activados para acceder. Presiona Ok para salir");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    finish();
            }
        });
        return builder;
    }

    /*public void checkconection(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activenetwork = cm.getActiveNetworkInfo();
        boolean isconected = activenetwork.isConnectedOrConnecting();

        switch (activenetwork.getType()){
            case ConnectivityManager.TYPE_WIFI:
                Intent intent = new Intent(splashscreen.this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                Toast.makeText(splashscreen.this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                break;
        }


    }*/
    public void pantallaInicio()
    {
        Intent intent = new Intent(splashscreen.this, java1.class);
        startActivity(intent);
    }
}
