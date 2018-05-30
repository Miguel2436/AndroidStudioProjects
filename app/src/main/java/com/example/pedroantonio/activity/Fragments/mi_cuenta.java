package com.example.pedroantonio.activity.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pedroantonio.activity.R;
import com.example.pedroantonio.activity.VolleySingleton;
import com.example.pedroantonio.activity.datos.Constantes;
import com.example.pedroantonio.activity.java1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mi_cuenta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mi_cuenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mi_cuenta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Constantes constantes;
    private VolleySingleton volleySingleton;
    private EditText nombre;
    private EditText app;
    private EditText apm;
    private EditText username;
    private EditText password;
    private TextView correo;
    private Button guardarCambios;

    String nombreS= null;
    String appS=null;
    String apmS=null;
    String usernameS= null;
    String passwordS=null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public mi_cuenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mi_cuenta.
     */
    // TODO: Rename and change types and number of parameters
    public static mi_cuenta newInstance(String param1, String param2) {
        mi_cuenta fragment = new mi_cuenta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);

        Button back = (Button)view.findViewById(R.id.btnback);
        nombre=(EditText) view.findViewById(R.id.edtcambionombre);
        app=(EditText) view.findViewById(R.id.edtcambioap);
        apm=(EditText) view.findViewById(R.id.edtcambioam);
        username=(EditText) view.findViewById(R.id.edtcambiousername);
        correo=(TextView) view.findViewById(R.id.correocuenta);
        password=(EditText)view.findViewById(R.id.edtcambiocontrasena);
        guardarCambios=(Button)view.findViewById(R.id.btnguadarcambioscuenta);

        guardarCambios.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cambiarDatos();
                    }
                }

        );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), java1.class);
                startActivity(intent);
            }
        });
    //OBTENER ID DE USUARIO DE ARCHIVO SHAREDPREFERNCES
       final SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Preferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String idUsuario="";
        idUsuario=sharedPreferences.getString("IdUsuario", "");

        String url="http://35.231.227.16/webService/consultarDatosClienteAndroid.php";
        Map<String,String> map=new HashMap<String,String>();
        map.put("idUsuario",idUsuario );

        JSONObject jo=new JSONObject(map);
        //Toast.makeText(getContext(), jo.toString(), Toast.LENGTH_SHORT).show();
    //ENVIAR PETICION DE VOLLEY A SERVER
        volleySingleton.getInstance(this.getActivity().getApplicationContext()).addToRequestQueue(


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
                                if(respuesta.equals("Éxito")) {


                                    try {
                                        nombreS = jsonObject.getString("nombre");
                                        appS = jsonObject.getString("app");
                                        apmS = jsonObject.getString("apm");
                                        usernameS = jsonObject.getString("username");
                                    } catch (JSONException e) {
                                        Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    nombre.setText(nombreS);
                                    app.setText(appS);
                                    apm.setText(apmS);
                                    username.setText(usernameS);
                                    correo.setText(sharedPreferences.getString("Usuario",""));
                                    password.setText(sharedPreferences.getString("Password", ""));


                                }else
                                Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause=error.toString();
                                Toast.makeText(getContext(), errorCause, Toast.LENGTH_SHORT).show();
                            }
                        })
        );

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void cambiarDatos()
    {
        nombreS=nombre.getText().toString();
        appS=app.getText().toString();
        apmS=apm.getText().toString();
        usernameS=username.getText().toString();
        passwordS=password.getText().toString();

        if(TextUtils.isEmpty(nombreS)){
            nombre.setError(getString(R.string.error_campo_vacio));
            nombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(appS)){
            app.setError(getString(R.string.error_campo_vacio));
            app.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(apmS)){
            apm.setError(getString(R.string.error_campo_vacio));
            apm.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(usernameS)){
            username.setError(getString(R.string.error_campo_vacio));
            username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passwordS)){
            password.setError(getString(R.string.error_campo_vacio));
            password.requestFocus();
            return;
        }
        /*if(!constantes.validarPassword(passwordS))
        {
            password.setError("Contraseña no válida");
            password.requestFocus();
            return;
        }*/



        final SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Preferencias", MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        String idUsuario="";
        idUsuario=sharedPreferences.getString("IdUsuario", "");

        String url="http://35.231.227.16/webService/cambiarDatosClienteAndroid.php";
        Map<String,String> map=new HashMap<String,String>();
        map.put("idUsuario",idUsuario );
        map.put("nombre",nombreS );
        map.put("app",appS );
        map.put("apm",apmS);
        map.put("username",usernameS);
        map.put("password",passwordS);

        JSONObject jo=new JSONObject(map);

        volleySingleton.getInstance(this.getActivity().getApplicationContext()).addToRequestQueue(


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
                                if(respuesta.equals("Éxito")) {



                                    nombre.setText(nombreS);
                                    app.setText(appS);
                                    apm.setText(apmS);
                                    username.setText(usernameS);
                                    correo.setText(sharedPreferences.getString("Usuario",""));
                                    editor.putString("Password", passwordS);
                                    editor.commit();
                                    password.setText(passwordS);


                                }else
                                    Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause=error.toString();
                                Toast.makeText(getContext(), errorCause, Toast.LENGTH_SHORT).show();
                            }
                        })
        );


    }
}
