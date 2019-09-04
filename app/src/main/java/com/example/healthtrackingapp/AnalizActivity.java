package com.example.healthtrackingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalizActivity extends AppCompatActivity {
    //private static final String url="http://192.168.1.36:80/HealthTracking/analiz.php";
    TextView txtAnaliz;
    TextView txtEcg;
    Button btnListele;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analiz);
        txtAnaliz=findViewById(R.id.txtAnaliz);
        txtEcg=findViewById(R.id.txtEcg);
        btnListele=findViewById(R.id.btnListele);
        requestQueue= Volley.newRequestQueue(this);
       btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parse();
            }
        });
    }
    private void parse()
    {
        String url="http://192.168.43.61:80/HealthTracking/analiz.php";
        String url2="http://192.168.43.61:80/HealthTracking/record.php";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("sonuc");
                    System.out.print("append"+jsonArray);
                  for(int i=0; i<jsonArray.length(); i++)
                    {
                        String bpm=jsonArray.get(i).toString();
                        txtAnaliz.append("Bpm pulse "+bpm+"\n");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        JsonObjectRequest request2=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("sonuc");
                    System.out.print("append"+jsonArray);
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        String bpm=jsonArray.get(i).toString();
                        txtEcg.append("Ecg Değerleri "+bpm+"\n");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
        requestQueue.add(request2);
    }

}
