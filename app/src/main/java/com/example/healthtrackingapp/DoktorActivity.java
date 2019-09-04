package com.example.healthtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DoktorActivity extends AppCompatActivity {
    TextView txtDr;
    Button btnHsta,btnHstaAnaliz,btnYorum;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        txtDr=findViewById(R.id.txtDr);
        btnHsta=findViewById(R.id.btnHsta);
        btnYorum=findViewById(R.id.btnYorum);
        btnHstaAnaliz=findViewById(R.id.btnHstaAnaliz);
        requestQueue= Volley.newRequestQueue(this);

        btnHsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),HastaActivity.class);
                startActivity(intent);

            }
        });

    btnHstaAnaliz.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(),AnalizActivity.class);
            startActivity(intent);
        }
    });
    btnYorum.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(),YorumActivity.class);
            startActivity(intent);
        }
    });
    }
}
