package com.example.pugazh.lifeblood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
     Button donorBtn,reqBtn;
     ImageView blLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donorBtn =(Button)findViewById(R.id.donorBtn);
        reqBtn = (Button)findViewById(R.id.reqBtn);



        donorBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,donorRegister.class));
            }
        });

        reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,requestorPortal.class));
            }
        });
    }
}
