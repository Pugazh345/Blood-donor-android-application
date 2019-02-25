package com.example.pugazh.lifeblood;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class requestorPortal extends AppCompatActivity {

    private Spinner bloodgroup;
    private EditText reqaddressEditText,reqPhoneNo;
    private Button reqBtn;
    String dnrName,dnrAddr,dnrCmp;
    String reqBg;
    String reqAddr,reqPhone;
    private DatabaseReference mDnrDatabase,mReqDatabase;
   ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestor_portal);
        bloodgroup=(Spinner)findViewById(R.id.bloodgroup);
        reqaddressEditText=(EditText)findViewById(R.id.reqAddresseditText);
        reqPhoneNo = (EditText) findViewById(R.id.reqPhn);
        reqBtn = (Button) findViewById(R.id.reqBtn);

        reqBg = String.valueOf(bloodgroup.getSelectedItem());
         reqAddr = reqaddressEditText.getText().toString().trim();
        reqPhone = reqPhoneNo.getText().toString().trim();

         reqBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(requestorPortal.this,matchingDnrList.class);
                 intent.putExtra("bg",reqBg);
                 intent.putExtra("addr",reqAddr);
                 intent.putExtra("reqPhone",reqPhone);
                 startActivity(intent);
             }
         });
//        mDnrDatabase = FirebaseDatabase.getInstance().getReference();
//        mReqDatabase = FirebaseDatabase.getInstance().getReference().child("Requesters");
//
//      final   ArrayList<String> list=new ArrayList<>();
//        reqBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String bg =  String.valueOf(bloodgroup.getSelectedItem());
//                final String ad = reqaddressEditText.getText().toString().trim();
//                Query query = mDnrDatabase.child("Users").orderByChild("blood_group").equalTo(bg);
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(requestorPortal.this);
////                        builderSingle.setIcon(R.drawable.ic_launcher);
//                        builderSingle.setTitle("Select One Name:-");
//
//                        arrayAdapter = new ArrayAdapter<String>(requestorPortal.this, android.R.layout.select_dialog_singlechoice);
//
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//
//                          if(postSnapshot.child("address").getValue().toString().equalsIgnoreCase(ad)){
//                              dnrAddr=postSnapshot.child("address").getValue().toString();
//                              dnrName = postSnapshot.child("name").getValue().toString();
//                              dnrCmp = dnrName + "-" + dnrAddr;
//                              arrayAdapter.add(dnrCmp);
//                          }
//
//                        }
//
//
//                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String strName = arrayAdapter.getItem(which);
//                                AlertDialog.Builder builderInner = new AlertDialog.Builder(requestorPortal.this);
//                                builderInner.setMessage(strName);
//                                builderInner.setTitle("Your Selected Donor is");
//                                builderInner.setPositiveButton("Send Blood Request", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog,int which) {
////                                        dialog.dismiss();
//
//                                    }
//
//                                });
//                                builderInner.show();
//                            }
//                        });
//                        builderSingle.show();
//
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
//
//
//            }
//        });
    }
}
