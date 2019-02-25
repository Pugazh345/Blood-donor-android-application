package com.example.pugazh.lifeblood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class donorLogin extends AppCompatActivity {
    private FirebaseAuth auth;
    Button loginBtn;
    EditText loginEmailEditText,loginPasswordEditText;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_donor_login2);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        mProgress = new ProgressDialog(this);
        loginEmailEditText=(EditText)findViewById(R.id.EmailLoginEditText);
        loginPasswordEditText=(EditText)findViewById(R.id.PassLoginEditText);
        mProgress.setTitle("Logging In...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();

                String email = loginEmailEditText.getText().toString().trim();
                final String password = loginPasswordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(donorLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    mProgress.dismiss();
                                    Toast.makeText(donorLogin.this, "Log in not successful", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    mProgress.dismiss();
                                    Intent intent = new Intent(donorLogin.this, donorPortal.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
