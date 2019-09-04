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

public class YorumActivity extends AppCompatActivity {
    Button btnYorumYap;
    private static final String url="http://192.168.43.61:80/HealthTracking/message.php";
    EditText etYorum;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorum);
        btnYorumYap=findViewById(R.id.btnYorumYap);
        etYorum=findViewById(R.id.etYorum);
        requestQueue= Volley.newRequestQueue(this);

        btnYorumYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yorumyap();
            }
        });
    }
    public void yorumyap()
    {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(YorumActivity.this, "Yorum Başarılı", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DoktorActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Oluyor mu? ", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("Comment",etYorum.getText().toString());


                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
