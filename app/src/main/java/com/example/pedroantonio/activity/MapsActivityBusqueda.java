package com.example.pedroantonio.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsActivityBusqueda extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient = null;
    private PendingIntent pendingIntent = null;
    private FusedLocationProviderClient mFusedLocationClient;
    private GeofencingClient geofencingClient;
    private Geofence geofence;
    private Geocoder geocoder;
    double latitud, longitud;
    private Circle circle;
    private String direccion = "";
    private Button buscarUbicacion;
    private SeekBar seekBarRadio;
    private CircleOptions circleOptions;
    GeofencingRequest geofencingRequest;
    private String ubicacion = "";
    float radio;
    private int progreso;
    private TextView valueSeekBar;
    List<Address> fromLocationName = null;
// ..


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        geocoder = new Geocoder(getApplicationContext());
        googleApiClient = new GoogleApiClient.Builder(this).addApi
                (LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_busqueda);
        geofencingClient = LocationServices.getGeofencingClient(this);
        buscarUbicacion = (Button) findViewById(R.id.buttonBuscarUbicacion);
        valueSeekBar = (TextView) findViewById(R.id.valueSeekBar);
        seekBarRadio = (SeekBar) findViewById(R.id.seekBarRadio);
        seekBarRadio.setMax(18);


        buscarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traducir();
            }
        });
        seekBarRadio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progreso = progress += 2;
                radio = (float) (progress * 1000);
                valueSeekBar.setText(String.valueOf(progreso));

                geofencia();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void  geofencia()
    {


        circleOptions = new CircleOptions()
                .center(new LatLng(latitud, longitud))
                .radius(radio)
                .strokeWidth(3)
                .strokeColor(Color.rgb(60, 179, 113)); // In meters

        geofence = new Geofence.Builder()
                        .setRequestId("geofenciaBusqueda")
                        .setCircularRegion(
                                latitud,
                                longitud,
                                radio
                        ).setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
                        .setLoiteringDelay(100)
                        .build();

                        geofencingRequest = new GeofencingRequest.Builder()
                        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)
                        .addGeofence(geofence).build();
                        if(circle!=null){
                            circle.remove();
                        }
                        circle = mMap.addCircle(circleOptions);

            Toast.makeText(this, "geofencia", Toast.LENGTH_SHORT).show();
                if (!googleApiClient.isConnected()) {
                     Toast.makeText(this, "Error al conectar cliente", Toast.LENGTH_SHORT).show();
                } else {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        //agregarGeofence();
                        return;

                    }


                }
        Toast.makeText(this, "salimos a geofencia", Toast.LENGTH_SHORT).show();
    }
    public void agregarGeofence() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Geofences added
                // ...

                Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    latitud=location.getLatitude();
                    longitud=location.getLongitude();
                    LatLng miUbicacion= new LatLng(latitud, longitud);
                    try {

                        fromLocationName=geocoder.getFromLocation(latitud, longitud,1);
                        Address address;
                        address=fromLocationName.get(0);
                        mMap.addMarker(new MarkerOptions().position(miUbicacion).title(address.getAddressLine(0)));
                    } catch (IOException e) {
                        mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Mi ubicación"));
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                    mMap.setMinZoomPreference(10.0f);
                    mMap.setMaxZoomPreference(20.0f);
                }
            });
        } else {
            // Show rationale and request permission.
        }


        // Add a marker in Sydney and move the camera
/*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void traducir()
    { ubicacion=((EditText)findViewById(R.id.ubicacionText)).getText().toString();
       mMap.clear();
        try {

            fromLocationName = geocoder.getFromLocationName(ubicacion, 1);
            Address address;
            address=fromLocationName.get(0);
            latitud=address.getLatitude();
            longitud=address.getLongitude();
            //Toast.makeText(getApplicationContext(),address.toString(),Toast.LENGTH_SHORT).show();

            if(address.hasLatitude()&&address.hasLongitude()) {
                LatLng ubicacionResult = new LatLng(latitud,longitud);

                mMap.addMarker(new MarkerOptions().position(ubicacionResult).title(address.getAddressLine(0)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionResult));
            }else{Toast.makeText(getApplicationContext(),"No se encontraron resultados",Toast.LENGTH_SHORT).show();}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
