package com.example.pedroantonio.activity;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class registro_establecimiento extends AppCompatActivity {

    TextView txvinicio, txvfin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_establecimiento);
        txvinicio = (TextView)findViewById(R.id.horarioinicio);
        txvfin = (TextView)findViewById(R.id.horariofin);
    }

    public void timeInicio(View view){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog
                (registro_establecimiento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txvinicio.setText(hourOfDay+":"+minute);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void timeFin(View view){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog
                (registro_establecimiento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txvfin.setText(hourOfDay+":"+minute);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }
}
