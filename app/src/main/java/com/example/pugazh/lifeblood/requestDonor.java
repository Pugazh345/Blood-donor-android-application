package com.example.pugazh.lifeblood;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class requestDonor extends AppCompatActivity {


    TextView selDnrName,selDnrBg,selDnrAddr;
    Button reqSelBtn;

    DatabaseReference mSelDnrDatabase,mReqDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_donor);

        final String user_id = getIntent().getStringExtra("user_id");

        mSelDnrDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mReqDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).push();

        selDnrName = (TextView)findViewById(R.id.selDnrName);
        selDnrBg = (TextView)findViewById(R.id.selDnrBg);
        selDnrAddr = (TextView)findViewById(R.id.selDnrAddr);
         reqSelBtn = (Button)findViewById(R.id.reqSelBtn);
        mSelDnrDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dnrName = dataSnapshot.child("name").getValue().toString();
                String dnrBg = dataSnapshot.child("blood_group").getValue().toString();
                String dnrAddr = dataSnapshot.child("address").getValue().toString();

                selDnrName.setText(dnrName);
                selDnrBg.setText(dnrBg);
                selDnrAddr.setText(dnrAddr);

                reqSelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("reqStatus","received");
                        mReqDatabase.setValue(userMap);
                        Toast.makeText(requestDonor.this, "Request Sent Successfully", Toast.LENGTH_LONG);

                    }


                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
