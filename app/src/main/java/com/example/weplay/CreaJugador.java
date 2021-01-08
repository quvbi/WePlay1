package com.example.weplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreaJugador extends AppCompatActivity {

    EditText name;
    EditText nickname;
    Button age;
    Spinner position;
    EditText tel;
    Button btnCreatePlayer;
    String user_id;
    DatabaseReference current_user_db;

    TextView ageS;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_jugador);
        //elementos de gui
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.editTextTextPersonName2);
        nickname = findViewById(R.id.editTextTextPersonName3);
        position = findViewById(R.id.spinner2);
        tel = findViewById(R.id.editTextTextPersonName6);
        btnCreatePlayer = findViewById(R.id.imageButton);

        //listas de dias, meses y años (hay que cambiarlo a date picker)
         Spinner spinner = findViewById((R.id.editTextTextPersonName4));
         final Spinner spinner1 = findViewById((R.id.editTextTextPersonName5));
         final Spinner spinner2 = findViewById((R.id.editTextTextPersonName13));

         //consigue la llave de identificaion de usuario de firebase
        user_id = mAuth.getCurrentUser().getUid();
        Toast.makeText(CreaJugador.this, current_user_db+"", Toast.LENGTH_SHORT).show();

        //crea lista de años (remplazar por date picker)
        ArrayList<Integer> arrayList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 1950; i < year; i++ ){
            arrayList.add(i);

        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        ageS = findViewById(R.id.textView19);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,
                        Toast.LENGTH_LONG).show();
                int   calcAge = getAge(Integer.parseInt(parent.getItemAtPosition(position).toString()), Integer.parseInt(spinner1.getSelectedItem().toString()), Integer.parseInt(spinner2.getSelectedItem().toString()));

                ageS.setText(""+calcAge);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });




    }

    //Funcion que calcula la edad de usuario
    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageInt;
    }
    public void addImage(View v){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    public void openCalendar(View view){
        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.layoutLineal);
        CalendarView calendar = new CalendarView(CreaJugador.this);
        parentLayout.addView(calendar,4);
    }



 //metodo que agrega datos de usuario a la base de datos firebase
    public void create(View v){
        String user_id = mAuth.getCurrentUser().getUid();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
        Map newPlayer = new HashMap();
        newPlayer.put("name", name.getText().toString());
        newPlayer.put("nickname", nickname.getText().toString());
        newPlayer.put("age", age.getText().toString());
        newPlayer.put("position", position.getSelectedItem().toString());
        newPlayer.put("tel", tel.getText().toString());
        String path = current_user_db + "";
        path = path.replace("https://weplay-a1887-default-rtdb.firebaseio.com/", "");
        current_user_db.child(path + "/users/" + user_id).setValue(newPlayer);
        Intent myIntent = new Intent(this, CreaUnirte.class);
        startActivity(myIntent);
    }
    public static final int GET_FROM_GALLERY = 3;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }
}