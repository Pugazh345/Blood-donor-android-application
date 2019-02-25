package com.example.pugazh.lifeblood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import javax.xml.transform.dom.DOMLocator;


public class matchingDnrList extends AppCompatActivity {

    private RecyclerView mDnrList;
    DatabaseReference mDnrDatabase;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    String bg,addr,phn;
Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_dnr_list);

         bg=getIntent().getStringExtra("reqBg");
         addr=getIntent().getStringExtra("reqAddr");
        phn=getIntent().getStringExtra("reqPhone");

        mDnrList =  findViewById(R.id.mDnrList);
        mDnrList.setHasFixedSize(true);
        mDnrList.setLayoutManager(new LinearLayoutManager(this));
        mDnrDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        query = mDnrDatabase.orderByChild("blood_group");


        FirebaseRecyclerOptions<Donors> options = new FirebaseRecyclerOptions.Builder<Donors>()
                .setQuery(query, Donors.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Donors, donorsViewHolder>(options) {
            @Override
            public donorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.donors_single_layout, parent, false);

                return new donorsViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(donorsViewHolder holder, int position, Donors model) {
                holder.setName(model.getName());
                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(matchingDnrList.this,requestDonor.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                    }
                });
//                holder.setName(model.getLastName());
            }


        };

        mDnrList.setAdapter(firebaseRecyclerAdapter);
//        firebaseRecyclerAdapter.startListening();
    }
//
//    public void onDataChange(DataSnapshot snapshot) {
//
//        query = mDnrDatabase.orderByChild("blood_group").equalTo(bg); ; // Update query
//        firebaseRecyclerAdapter.notifyDataSetChanged(); // Update UI
//    }
    @Override
     protected void onStart(){
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

@Override
    protected void onStop(){
        super.onStop();
       firebaseRecyclerAdapter.stopListening();
}

 public  static class donorsViewHolder extends RecyclerView.ViewHolder{
    View mView;

    public donorsViewHolder(View itemview){
        super(itemview);
        mView =itemview;
    }
    public void setName(String name){
        TextView donorName = mView.findViewById(R.id.donor_single_name);
        donorName.setText(name);
    }
 }
}
