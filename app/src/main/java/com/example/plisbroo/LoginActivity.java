package com.example.plisbroo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    TextView tvRegis;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.eEmail);
        password = findViewById(R.id.ePass);
        tvRegis = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.bLogin);
        
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You are Logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HalamanUtama.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPass = password.getText().toString();

                System.out.println("aaaaa"+strEmail);
                System.out.println("aaaaa"+strPass);
                if (strEmail.isEmpty()){
                    email.setError("Email error");
                    email.requestFocus();
                }
                else if (strPass.isEmpty()){
                    password.setError("Password error");
                    password.requestFocus();
                }
                else if (strEmail.isEmpty() && strPass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi dulu", Toast.LENGTH_SHORT).show();
                }
                else if (!(strEmail.isEmpty() && strPass.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent i = new Intent(LoginActivity.this, HalamanUtama.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error woy", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
