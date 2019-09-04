package com.example.healthtrackingapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {
    Button btnKaydet,btnDanisma,btnAnaliz,btnEskiKayit,btnBluetooth;
    TextView hastaAd;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       btnKaydet = findViewById(R.id.btnKaydet);
        btnAnaliz = findViewById(R.id.btnAnaliz);
        btnDanisma = findViewById(R.id.btnDanisma);
        btnEskiKayit = findViewById(R.id.btnEskiKayit);
    btnBluetooth = findViewById(R.id.btnBluetooth);
//        hastaAd = findViewById(R.id.hastaAd);


        btnEskiKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AnalizActivity.class);
                startActivity(intent);
            }
        });
        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Bluetooth.class);
                startActivity(intent);
            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Bluetooth.class);
                startActivity(intent);
            }
        });

        btnAnaliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AnalizActivity.class);
                startActivity(intent);
            }
        });
        btnDanisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ShowActivity.class);
                startActivity(intent);
            }
        });









    }



}


