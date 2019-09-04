package com.example.healthtrackingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ShowActivity extends AppCompatActivity {
    TextView txtGoster;
    RequestQueue requestQueue;
    private static final String url="http://192.168.43.61:80/HealthTracking/goster.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        txtGoster=findViewById(R.id.txtGoster);
        requestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("sonuc");
                    System.out.print("append"+jsonArray);
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        String bpm=jsonArray.get(i).toString();
                        txtGoster.append("Dr Yorumu: "+bpm+"\n");

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
    }
}
