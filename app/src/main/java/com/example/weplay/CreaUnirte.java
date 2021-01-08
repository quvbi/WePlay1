package com.example.weplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CreaUnirte extends AppCompatActivity {

    ImageButton createClub;
    ImageButton joinClub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_unirte);

        createClub = findViewById(R.id.imageButton2);
        joinClub = findViewById(R.id.imageButton3);

    }

    public void createClub(View v){
        Intent myIntent = new Intent(this, CrearClub.class);
        startActivity(myIntent);
    }

    public void join(View v){
        Intent myIntent = new Intent(this, UnirteClub.class);
        startActivity(myIntent);
    }
}