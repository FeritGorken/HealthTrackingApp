package com.example.healthtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String url="http://192.168.43.61:80/HealthTracking/person.php";
    EditText etAd,etSoyad,etEmail,etPassword,etCinsiyet,etKilo,etBoy;
    Button btnKayit;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etAd=findViewById(R.id.etAd);
        etSoyad=findViewById(R.id.etSoyad);
        etPassword=findViewById(R.id.etPassword);
        etEmail=findViewById(R.id.etEmail);
        etCinsiyet=findViewById(R.id.etCinsiyet);
        etKilo=findViewById(R.id.etKilo);
        etBoy=findViewById(R.id.etBoy);
        btnKayit=findViewById(R.id.btnKayit);
        requestQueue= Volley.newRequestQueue(this);

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verileriKaydet();
            }
        });
    }
    public void verileriKaydet()
    {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Oluyor mu? ", error.toString());
            }
        }){
            @Override
            protected Map<String,String>getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("username",etAd.getText().toString());
                hashMap.put("surname",etSoyad.getText().toString());
                hashMap.put("email",etEmail.getText().toString());
                hashMap.put("password",etPassword.getText().toString());
                hashMap.put("gender",etCinsiyet.getText().toString());
                hashMap.put("weight",etKilo.getText().toString());
                hashMap.put("height",etBoy.getText().toString());

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
