package com.example.pugazh.lifeblood;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class donorRegister extends AppCompatActivity {
    private static final String TAG = "FirebaseEmailPassword";

    Button signupbtn, movetoLoginBtn, verifyEmailBtn;
    EditText EmaileditText, PasseditText, BloodeditText, AddresseditText, nameEditText;
    TextView txtStatus, txtDetail;
    Spinner bloodgroup;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    AlertDialog.Builder builder;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_register);

        mProgress = new ProgressDialog(this);

        signupbtn = (Button) findViewById(R.id.signupBtn);
        movetoLoginBtn = (Button) findViewById(R.id.movetoLoginBtn);
        EmaileditText = (EditText) findViewById(R.id.EmaileditText);
        PasseditText = (EditText) findViewById(R.id.PasseditText);
        nameEditText = (EditText) findViewById(R.id.NameeditText);
        bloodgroup = (Spinner) findViewById(R.id.bloodgroup);
        AddresseditText = (EditText) findViewById(R.id.AddresseditText);
        txtStatus = (TextView) findViewById(R.id.txtstatus);

        mAuth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(this);


        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        signupbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mProgress.show();


                final String userEmail, userPass, userName, userBloodgroup, userAddress;

                userEmail = EmaileditText.getText().toString().trim();
                userPass = PasseditText.getText().toString().trim();
                userName = nameEditText.getText().toString().trim();
                userBloodgroup = String.valueOf(bloodgroup.getSelectedItem());
                userAddress = AddresseditText.getText().toString().trim();


                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)) {

                    mAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = currentuser.getUid();
                                builder.setMessage("To complete the registration , verify your email , Click verify email button to receive verification mail.")
                                        .setCancelable(false)
                                        .setPositiveButton("Verify Email", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                sendEmailVerification();
                                                finish();
                                            }
                                        });
//
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("name", userName);
                                userMap.put("blood_group",userBloodgroup);
                                userMap.put("address", userAddress);

                                mDatabase.setValue(userMap);

                                mProgress.dismiss();

                                //Creating dialog box
                                AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.setTitle("Donor Registration");
                                alert.show();
                                setContentView(R.layout.activity_donor_register);
//                                Toast.makeText(donorRegister.this, "Donor Added successfully", Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                mProgress.dismiss();
                                Toast.makeText(donorRegister.this, "Enter correct values or donor exists already", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

            }
        });
        movetoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(donorRegister.this, donorLogin.class));
            }
        });


    }

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        } else {
                            Log.e(TAG, "sendEmailVerification failed!", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



