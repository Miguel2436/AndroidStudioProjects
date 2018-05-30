package com.example.pedroantonio.activity;

import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.pedroantonio.activity.datos.Constantes;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class registro_usuario extends AppCompatActivity {
   JsonObjectRequest jsonObjectRequest;
    VolleySingleton volleySingleton;
    Button registrar, ingresar;
    EditText edtnombre, edtap, edtam, edtuser, edtcorreo, edtcontrasena, edtcontrasenaconf;
    RadioButton terminos;
    Constantes constantes =new Constantes();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        // <---- Cuadros de Texto ---->
        edtnombre = (EditText)findViewById(R.id.edtnombre);
        edtap = (EditText)findViewById(R.id.edtapellidop);
        edtam = (EditText)findViewById(R.id.edtapellidom);
        edtuser = (EditText)findViewById(R.id.edtusername);
        edtcorreo = (EditText)findViewById(R.id.edtcorreoe);
        edtcontrasena = (EditText)findViewById(R.id.edtcontrasena);
        edtcontrasenaconf=(EditText)findViewById(R.id.edtcontrasenaconf);
        terminos = (RadioButton)findViewById(R.id.rbtterminos);

        // <---- Botones ---->
        registrar = (Button)findViewById(R.id.btnregistrouser);
        ingresar = (Button)findViewById(R.id.btningresareg);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ing();
            }
        });
    }

    public void reg(){
        edtnombre.setError(null);
        edtam.setError(null);
        edtap.setError(null);
        edtuser.setError(null);
        edtcorreo.setError(null);
        edtcontrasena.setError(null);
        edtcontrasenaconf.setError(null);

        String nombre = edtnombre.getText().toString();
        String ap = edtap.getText().toString();
        String am = edtam.getText().toString();
        String user = edtuser.getText().toString();
        String correo = edtcorreo.getText().toString();
        String contrasena = edtcontrasena.getText().toString();
        String contrasenaconf=edtcontrasenaconf.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            edtnombre.setError(getString(R.string.error_campo_vacio));
            edtnombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(ap)){
            edtap.setError(getString(R.string.error_campo_vacio));
            edtap.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(am)){
            edtam.setError(getString(R.string.error_campo_vacio));
            edtam.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(user)){
            edtuser.setError(getString(R.string.error_campo_vacio));
            edtuser.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(correo)){
            edtcorreo.setError(getString(R.string.error_campo_vacio));
            edtcorreo.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(contrasena)){
            edtcontrasena.setError(getString(R.string.error_campo_vacio));
            edtcontrasena.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(contrasenaconf)){
            edtcontrasenaconf.setError(getString(R.string.error_campo_vacio));
            edtcontrasenaconf.requestFocus();
            return;
        }
        if (!constantes.validarEmail(correo)){
            edtcorreo.setError("Email no válido");
            edtcorreo.requestFocus();
            return;
        }
        if(!constantes.validarPassword(contrasena))
        {
            edtcontrasena.setError("Contraseña no válida");
            edtcontrasena.requestFocus();
            return;
        }
        if(!constantes.validarPasswordIgual(contrasena, contrasenaconf))
        {
            edtcontrasenaconf.setError("Las contraseñas no coinciden");
            edtcontrasenaconf.requestFocus();
            return;
        }
        if(terminos.isChecked()){
            Intent intent = new Intent(registro_usuario.this, java1.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Acepte los Términos y Condiciones", Toast.LENGTH_SHORT).show();
        }
        registrarCliente(nombre, ap, am, user, contrasena, correo);
    }

    public void ing(){
        Intent intent = new Intent(registro_usuario.this, MainActivity.class);
        startActivity(intent);
    }


    public void registrarCliente(String nombre, String app, String apm, String username, String pass, String email)
    {
        String url=constantes.getIP()+"registroClienteAndroid.php";
        //HashMap<String, String> map = new HashMap<>();
       // volleySingleton=new VolleySingleton();
        Map<String,String> map=new HashMap<String,String>();
            map.put("Cliente", nombre);
            map.put("ApellidoPaterno", app);
            map.put("ApellidoMaterno", apm);
            map.put("Username", username);
            map.put("Contrasena", pass);
            map.put("CorreoElectronico", email);


        JSONObject jo=new JSONObject(map);

        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
         new JsonObjectRequest
                (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = response;
                        String respuesta="";
                        try {
                            respuesta=jsonObject.getString("mensaje");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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


