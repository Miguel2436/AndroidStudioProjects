package com.example.pedroantonio.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pedroantonio.activity.Fragments.me_interesa;
import com.example.pedroantonio.activity.Fragments.mi_agenda;
import com.example.pedroantonio.activity.Fragments.mi_cuenta;
import com.example.pedroantonio.activity.Fragments.mis_visitas;
import com.example.pedroantonio.activity.datos.Establecimiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;


public class java1 extends AppCompatActivity implements mi_agenda.OnFragmentInteractionListener, mi_cuenta.OnFragmentInteractionListener, me_interesa.OnFragmentInteractionListener, mis_visitas.OnFragmentInteractionListener {
    ubicacionInicial ubicacionInicial;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista1);

       /* mi_agenda miagenda = new mi_agenda();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, miagenda);

        mi_cuenta micuenta = new mi_cuenta();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, micuenta);*/

       Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Apptivity");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.agenda:
                mi_agenda miAgenda = new mi_agenda();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, miAgenda);
                transaction.commit();
                break;
            case R.id.visita:
                mis_visitas misVisitas = new mis_visitas();
                FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                transaction4.replace(R.id.contenedor, misVisitas);
                transaction4.commit();
                break;
            case R.id.interes:
                me_interesa meInteresa = new me_interesa();
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.contenedor, meInteresa);
                transaction3.commit();
                break;
            case R.id.cuenta:
                mi_cuenta miCuenta = new mi_cuenta();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.contenedor, miCuenta);
                transaction2.commit();
                break;
            case R.id.refresh:
                Intent intent2 = new Intent(java1.this, java1.class);
                startActivity(intent2);
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    public void finishactivity(View view){
        app.finishapp(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
