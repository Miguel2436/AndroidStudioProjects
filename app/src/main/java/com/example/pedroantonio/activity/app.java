package com.example.pedroantonio.activity;

import android.support.v7.app.AppCompatActivity;

public class app {

    public static void finishapp(AppCompatActivity appCompatActivity){
        appCompatActivity.finish();
    }

    public static void refreshapp(AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }
}
