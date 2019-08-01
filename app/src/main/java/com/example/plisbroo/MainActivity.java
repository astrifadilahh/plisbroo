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

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button btnCreate;
    TextView tvLogin;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.eEmail);
        password = findViewById(R.id.ePass);
        tvLogin = findViewById(R.id.tvLogin);
        btnCreate = findViewById(R.id.bCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this, "Isi dulu", Toast.LENGTH_SHORT).show();
                }
                else if (!(strEmail.isEmpty() && strPass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            System.out.println("aaaaaa masuk woy");
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,  "gagal, coba lagi", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error woy", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
