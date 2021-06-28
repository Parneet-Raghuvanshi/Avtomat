package com.avtomat.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ar.healthcare.UnityPlayerActivity;

public class UnityAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity);

        Intent intent = new Intent(UnityAct.this, UnityPlayerActivity.class);
        startActivity(intent);
    }
}