package com.example.plisbroo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword;
    private Button btnLogin;
    private TextView textRegis;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private Button Button2;
    private EditText editText6;
    private EditText editText7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null) {
            finish();
            startActivity(new Intent(getApplicationContext(),HalamanUtama.class));

        }

        editEmail = (EditText) findViewById(R.id.etEmail);
        editPassword = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.bLogin);
        textRegis = (TextView) findViewById(R.id.tvRegister);

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        textRegis.setOnClickListener(this);
    }

    private void userLogin(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if vallidation are ok
        //we will first show a progressbar
        progressDialog.setMessage("Register USer....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), HalamanUtama.class));
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            userLogin();
        }
        if (view == textRegis) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
