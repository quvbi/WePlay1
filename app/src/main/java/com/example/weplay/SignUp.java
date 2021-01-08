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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {

   private EditText user;
    private EditText pass, cpass;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
        user = findViewById(R.id.editTextTextPersonName11);
        pass = findViewById(R.id.editTextTextPassword);
        cpass = findViewById(R.id.editTextTextPassword2);


        sign = findViewById(R.id.button5);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser != null){
                    Toast.makeText(SignUp.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUp.this, CreaJugador.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(SignUp.this, "please sign up", Toast.LENGTH_SHORT).show();
                }
            }
        };

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email  = user.getText().toString();
                String pwd = pass.getText().toString();
                String cpwd= pass.getText().toString();
                if (email.isEmpty()) {
                    user.setError("Please enter email");
                    user.requestFocus();
                } else if (pwd.isEmpty()) {
                    pass.setError("enter password");
                    pass.requestFocus();
                } else if(!cpwd.equals(pwd)) {
                    cpass.setError("Passwords don't match");
                    cpass.requestFocus();
                }else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Unable to sign in", Toast.LENGTH_SHORT).show();

                            } else {
                              // String user_id = mAuth.getCurrentUser().getUid();
                               // DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);







                                Intent intent = new Intent(SignUp.this, CreaJugador.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "ERROR OCURRED!", Toast.LENGTH_SHORT).show();

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