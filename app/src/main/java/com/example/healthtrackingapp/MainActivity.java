package com.example.healthtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etEmail,etPassword;
    Button btnGiris,btnKayit;
    RequestQueue requestQueue;
    StringRequest request;
    RadioGroup rdGroup;

    private static final String url="http://192.168.43.61:80/HealthTracking/user_controller.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnGiris=findViewById(R.id.btnGiris);
        btnKayit=findViewById(R.id.btnKayit);
        rdGroup=findViewById(R.id.rdGroup);

        requestQueue=Volley.newRequestQueue(this);
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int secilenRadio=rdGroup.getCheckedRadioButtonId();
                switch (secilenRadio)
                {
                    case R.id.rdHasta:
                        {
                        request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.names().get(0).equals("success"))
                                    {
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                    }else
                                    {
                                        Toast.makeText(getApplicationContext(), "Error",jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Oluyor mu? ", error.toString());
                            }
                        }){
                            protected Map<String,String>getParams() throws AuthFailureError
                        {
                            HashMap<String,String> hashMap=new HashMap<String, String>();
                            hashMap.put("email",etEmail.getText().toString());
                            hashMap.put("password",etPassword.getText().toString());

                            return hashMap;
                        }
                    };
                        requestQueue.add(request);

                        break;
                    }
                    case R.id.rdDoktor:
                    {
                        request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.names().get(0).equals("success"))
                                    {
                                        // Toast.makeText(getApplicationContext(),"Success",jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),DoktorActivity.class));
                                    }else
                                    {
                                        // Toast.makeText(getApplicationContext(), "Error",jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            protected Map<String,String>getParams() throws AuthFailureError
                            {
                                HashMap<String,String> hashMap=new HashMap<String, String>();
                                hashMap.put("email",etEmail.getText().toString());
                                hashMap.put("password",etPassword.getText().toString());

                                return hashMap;
                            }
                        };
                        requestQueue.add(request);

                        break;
                    }
                }


            }
        });
        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}