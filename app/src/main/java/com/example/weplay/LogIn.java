package com.example.weplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {


    private EditText user;
    private EditText pass;
    private FirebaseAuth mAuth;
    Button login;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        mAuth = FirebaseAuth.getInstance();
        user = findViewById(R.id.editTextTextPersonName12);
        pass = findViewById(R.id.editTextTextPassword3);
        login = findViewById(R.id.button6);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser != null){
                    Toast.makeText(LogIn.this, "you are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LogIn.this, MainMenu.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(LogIn.this, "please login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString();
                String pwd = pass.getText().toString();
                if (email.isEmpty()) {
                    user.setError("Please enter email");
                    user.requestFocus();
                } else if (pwd.isEmpty()) {
                    pass.setError("enter password");
                    pass.requestFocus();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LogIn.this, "Login error", Toast.LENGTH_SHORT).show();

                            } else {
                                Intent intent = new Intent(LogIn.this, MainMenu.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LogIn.this, "ERROR OCURRED!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}