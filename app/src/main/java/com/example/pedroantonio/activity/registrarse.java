package com.example.pedroantonio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class registrarse extends AppCompatActivity {

    Button usuario, establecimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        usuario = (Button)findViewById(R.id.btnusuario);
        establecimiento = (Button)findViewById(R.id.btnestablecimiento);

        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresausuario();
            }
        });

        establecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresaestablecimiento();
            }
        });
    }

    public void ingresausuario(){
        Intent intent = new Intent(registrarse.this, registro_usuario.class);
        startActivity(intent);
    }

    public void ingresaestablecimiento(){
        Intent intent = new Intent(registrarse.this, registro_establecimiento.class);
        startActivity(intent);
    }
}
