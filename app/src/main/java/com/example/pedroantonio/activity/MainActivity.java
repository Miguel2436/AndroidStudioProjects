package com.example.pedroantonio.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pedroantonio.activity.datos.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Constantes constantes=new Constantes();
    VolleySingleton volleySingleton;


    EditText edtuser, edtpass;
    int bandera= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Button ingresar = (Button) findViewById(R.id.btningresar);
            Button registrar = (Button) findViewById(R.id.registrarse);
            edtuser = (EditText) findViewById(R.id.edtusuario);
            edtpass = (EditText) findViewById(R.id.edtcontr);

            ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ing();
                }
            });
            registrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reg();
                }
            });

    }

    public void ing(){
        //Datos
        String usuario = ((EditText)findViewById(R.id.edtusuario)).getText().toString();
        String contrasena = ((EditText)findViewById(R.id.edtcontr)).getText().toString();

        if(TextUtils.isEmpty(usuario)){
            edtuser.setError(getString(R.string.error_campo_vacio));
            edtuser.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(contrasena)){
            edtpass.setError(getString(R.string.error_campo_vacio));
            edtpass.requestFocus();
            return;
        }



        iniciarSesion(usuario,contrasena);
        //Comparacion, aqui se debe de hacer la comparacion con su DB

    }
    public void pantallaInicio()
    {
        Intent intent = new Intent(MainActivity.this, MapsActivityBusqueda.class);
        startActivity(intent);
    }
    public void inicio()
    {
        Intent intent=new Intent(MainActivity.this, inicio_user.class);
    }
    public void reg(){
        Intent intent = new Intent(MainActivity.this, registrarse.class);
        startActivity(intent);
    }
    public void iniciarSesion(final String usuario, final String password)
    {
        String url=constantes.getIP()+"iniciarSesionAndroid.php";

        //HashMap<String, String> map = new HashMap<>();
        // volleySingleton=new VolleySingleton();
        Map<String,String> map=new HashMap<String,String>();
        map.put("usuario", usuario);
        map.put("password", password);

        JSONObject jo=new JSONObject(map);

        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(


                new JsonObjectRequest
                        (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject jsonObject = response;

                                String respuesta="";
                                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                                try {
                                    respuesta=jsonObject.getString("mensaje");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(respuesta.equals("Exito")) {
                                    String idUsuario= null;
                                    try {
                                        idUsuario = jsonObject.getString("idUsuario");
                                    } catch (JSONException e) {
                                        idUsuario="No encontrado";
                                    }

                                    bandera = 1;
                                    SharedPreferences sharedPreferences= getSharedPreferences("Preferencias",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("Usuario", usuario);
                                    editor.putString("Password", password);
                                    editor.putString("IdUsuario",idUsuario);
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(), "Holis", Toast.LENGTH_SHORT).show();
                                    pantallaInicio();
                                }else bandera=2;
                                Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause=error.toString();
                                Toast.makeText(getApplicationContext(), errorCause, Toast.LENGTH_SHORT).show();
                            }
                        })
        );



    }
}
