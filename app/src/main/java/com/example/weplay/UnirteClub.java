package com.example.weplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UnirteClub extends AppCompatActivity {

    ImageButton joinClub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirte_club);

        joinClub = findViewById(R.id.imageButton5);
    }

    public void joinClub(View v){
        Intent myIntent = new Intent(this, MainMenu.class);
        startActivity(myIntent);
    }


}