package com.example.weplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Transaction;

public class MainMenu extends AppCompatActivity   {
    private static final String FRAGMENT_TAG = "fragmento";


    FirstFragment usuarioF;
    SecondFragment clubF;
    EmpezarPartidoFragment partidoF;
    EquiposFragment equipoF;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        usuarioF = new FirstFragment();
        clubF = new SecondFragment();
        partidoF = new EmpezarPartidoFragment();
        equipoF = new EquiposFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout4, usuarioF, FRAGMENT_TAG);
        transaction.commit();

    }

    private void swap(Fragment newFragment){
        FragmentManager manager = getSupportFragmentManager();
        Fragment f= manager.findFragmentByTag(FRAGMENT_TAG);


        if(f == newFragment)
            return;

        if(f != null){
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(f);


            transaction.add(R.id.frameLayout4, newFragment, FRAGMENT_TAG);
            transaction.commit();
        }
    }

    public void logOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void showUsuario(View v){
        swap(usuarioF);
    }

    public  void showClub(View v){
        swap(clubF);
    }

    public void showPartido(View v){
        swap(partidoF);
    }

    public  void showEquipo(View v){
        swap(equipoF);
    }



}
