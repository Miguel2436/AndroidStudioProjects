package com.example.pedroantonio.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pedroantonio.activity.datos.Establecimiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import java.util.List;

public class ubicacionInicial extends Activity {

    private VolleySingleton volleySingleton;
    List<Establecimiento> list;

    public void obtenerEstablecimientos()
    {
        String url="http://35.231.227.16/webService/obtenerEstablecimientosAndroid.php";


        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(


                new JsonArrayRequest(Request.Method.POST, url, (String) null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray jsonArray = response;
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = null;
                            try {
                                object = jsonArray.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                String  idEstablecimiento =object.getString("idEstablecimiento");
                                String  latitud =object.getString("latitud");
                                String  longitud =object.getString("longitud");

                                Establecimiento establecimiento=new Establecimiento(idEstablecimiento,latitud,longitud);

                                list.add(establecimiento);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
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
