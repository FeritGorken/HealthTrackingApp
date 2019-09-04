package com.example.healthtrackingapp;


import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EcgActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String url="http://192.168.43.61:80/HealthTracking/pulse_oximeter.php";
    RequestQueue requestQueue;
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (Bluetooth.connectedThread != null) {
            Bluetooth.connectedThread.write("Q");}//Stop streaming
        super.onBackPressed();
    }
    static boolean Lock;//whether lock the x-axis to 0-5
    static boolean AutoScrollX;//auto scroll to the last x value
    static boolean Stream;//Start or stop streaming
    int old_interval=0;
    int new_interval=0;
    int mean_interval=20;
    Context context=this;
    //Button init
    Button bXminus;
    Button bXplus;
    ToggleButton tbLock;
    ToggleButton tbScroll;
    ToggleButton tbStream;
    //GraphView init
    static LinearLayout GraphView;
    static com.jjoe64.graphview.GraphView graphView;
    static GraphViewSeries Series;
    //graph value
    private static double graph2LastXValue = 0;
    private static int Xview=10;
    Button bConnect, bDisconnect;

    String strIncom;             // create string from bytes array
    String bpm;
    String ibi;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);


            switch (msg.what) {
                case Bluetooth.SUCCESS_CONNECT:
                    Bluetooth.connectedThread = new Bluetooth.ConnectedThread((BluetoothSocket) msg.obj);
                    Toast.makeText(getApplicationContext(), "Connected!", Toast.LENGTH_SHORT).show();
                    String s = "successfully connected";
                    Bluetooth.connectedThread.start();
                    System.out.println("Baglandi");
                    break;
                case Bluetooth.MESSAGE_READ:
                    System.out.println("okunuyor");
                    if (true) {
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, 5);                 // create string from bytes array
                        String bpm = new String(readBuf, 0, 80);
                        String ibi = new String(readBuf, 0, 80);

                       // System.out.println("veri" + new String(readBuf));


                    if(tbStream.isChecked()){
                        System.out.println("okunur");
                        byte[] readBuf = (byte[]) msg.obj;
                         strIncom = new String(readBuf, 0, 5, StandardCharsets.UTF_8);
                         bpm=new String(readBuf, 0, 9, StandardCharsets.UTF_8);
                         ibi=new String(readBuf, 0, 9, StandardCharsets.UTF_8);
                        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String,String>getParams() throws AuthFailureError
                            {

                                HashMap<String,String> hashMap=new HashMap<String, String>();
                                //hashMap.put("username",etAd.getText().toString());
                                hashMap.put("Pulse",bpm);
                                return hashMap;
                            }
                        };


                            requestQueue.add(request);



                        //ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(ibi);
                       System.out.println("veri: " + strIncom);
                        System.out.println("BPM: " + bpm);
                        System.out.println("IBI: " + ibi);

                        System.out.println("strincom " + strIncom);
                        //Log.d("strIncom", strIncom);
                        //if (strIncom.indexOf('.')==2 && strIncom.indexOf('s')==0){
                        if (strIncom.indexOf(',')==2 && strIncom.indexOf('i')==0){
                            System.out.println("geldi24");
                            strIncom = strIncom.replace("s", "");
                            if (isFloatNumber(strIncom)){ System.out.println("geldi25");
                                Series.appendData(new GraphView.GraphViewData(graph2LastXValue,Double.parseDouble(strIncom)),AutoScrollX);
                                //X-axis control
                                if (graph2LastXValue >= Xview && Lock == true){
                                    Series.resetData(new GraphView.GraphViewData[] {});
                                    graph2LastXValue = 0;
                                    System.out.println("geldi18");
                                }else graph2LastXValue += 0.1;

                                if(Lock == true)
                                    graphView.setViewPort(0, Xview);

                                else
                                    graphView.setViewPort(graph2LastXValue-Xview, Xview);

                                //refresh
                                GraphView.removeView(graphView);
                                GraphView.addView(graphView);
                                System.out.println("geldi19");

                            }
                        }

                        int temp1,temp2;
                        temp1=bpm.indexOf('b');
                        temp2=bpm.indexOf(',');
                        System.out.println("geldi20");
                        if(temp1>=0 && temp2>=temp1){
                            System.out.println("geldi22");
                            bpm = bpm.substring(temp1+1,temp2);
                            TextView BPM = (TextView) findViewById (R.id.BPM);
                            BPM.setText(bpm);
                            BPM.setBackgroundResource(R.drawable.heartbeat);
                            System.out.println("geldi15");
                        }else{
                            TextView BPM = (TextView) findViewById (R.id.BPM);
                            BPM.setBackgroundResource(R.drawable.heartbeat2);
                            System.out.println("geldi16");
                        }
                        temp1=ibi.indexOf('i');
                        temp2=ibi.indexOf('e');
                        System.out.println("geldi14");

                        if(temp1>=0 && temp2>=temp1){
                            ibi = ibi.substring(temp1+1,temp2);
                            old_interval=new_interval;
                            new_interval=Integer.parseInt(ibi);
                            TextView IBI = (TextView) findViewById (R.id.IBI);
                            IBI.setText(ibi);
                            int timeDifference= java.lang.Math.abs(old_interval-new_interval);
                            mean_interval=(mean_interval+timeDifference)/2;
                            System.out.println("geldi21");
                            if(timeDifference>(mean_interval+200)){
                                TextView arrythmia = (TextView) findViewById (R.id.arrythmiaStatus);
                                arrythmia.setText("TRUE");
                                arrythmia.setBackgroundResource(R.color.white);
                                System.out.println("geldi3");

                            }else{
                                TextView arrythmia = (TextView) findViewById (R.id.arrythmiaStatus);
                                arrythmia.setText("FALSE"); //String.valueOf(mean_interval)
                                arrythmia.setBackgroundResource(R.color.black);
                                System.out.println("geldi17");
                            }
                            System.out.println("geldi13");
                            mean_interval=(mean_interval+timeDifference)/2;
                        }
                    }

                    break;
            }

    }
        public boolean isFloatNumber(String num){
            //Log.d("checkfloatNum", num);
            try{
                Double.parseDouble(num);
            } catch(NumberFormatException nfe) {
                return false;
            }
            return true;
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestQueue= Volley.newRequestQueue(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide title
        this.getWindow().setFlags(WindowManager.LayoutParams.
                FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hide Status bar
        setContentView(R.layout.activity_ecg);
        LinearLayout background = (LinearLayout)findViewById(R.id.bg);
        background.setBackgroundColor(Color.BLACK);
        init();
        ButtonInit();



    }

    public void init(){
        Bluetooth.gethandler(mHandler);
        //init graphview
        System.out.println("geldi1");
        GraphView = (LinearLayout) findViewById(R.id.Graph);
        // init example series data-------------------
        Series = new GraphViewSeries("Signal",
                new GraphViewSeries.GraphViewStyle(Color.YELLOW, 2),//color and thickness of the line
                new GraphView.GraphViewData[] {new GraphView.GraphViewData(0, 0)});
        graphView = new LineGraphView(
                this // context
                , "Electrocardiogram AUTH" // heading
        );
        System.out.println("geldi4");
        graphView.setViewPort(0, Xview);
        graphView.setScrollable(true);
        graphView.setScalable(true);
        graphView.setShowLegend(true);
        graphView.setLegendAlign(com.jjoe64.graphview.GraphView.LegendAlign.BOTTOM);
        graphView.setManualYAxis(true);
        graphView.setManualYAxisBounds(5, 0);
        graphView.addSeries(Series); // data
        GraphView.addView(graphView);
        System.out.println("geldi5");
    }
    void ButtonInit(){
        System.out.println("geldi2");
        bConnect = (Button)findViewById(R.id.bConnect);
        bConnect.setOnClickListener(this);
        bDisconnect = (Button)findViewById(R.id.bDisconnect);
        bDisconnect.setOnClickListener(this);
        //X-axis control button
        bXminus = (Button)findViewById(R.id.bXminus);
        bXminus.setOnClickListener(this);
        bXplus = (Button)findViewById(R.id.bXplus);
        bXplus.setOnClickListener(this);
        //
        tbLock = (ToggleButton)findViewById(R.id.tbLock);
        tbLock.setOnClickListener(this);
        tbScroll = (ToggleButton)findViewById(R.id.tbScroll);
        tbScroll.setOnClickListener(this);
        tbStream = (ToggleButton)findViewById(R.id.tbStream);
        tbStream.setOnClickListener(this);
        //init toggleButton
        Lock=true;
        AutoScrollX=true;
        Stream=true;
        System.out.println("geldi6");
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("geldi8");
        switch(v.getId()){
            case R.id.bConnect:
                startActivity(new Intent("android.intent.action.BT1"));
                break;
            case R.id.bDisconnect:
                Bluetooth.disconnect();
                break;
            case R.id.bXminus:
                if (Xview<30) Xview++; System.out.println("geldi7");
                break;
            case R.id.bXplus:
                if (Xview>1) Xview--; System.out.println("geldi12");
                break;
            case R.id.tbLock:
                if (tbLock.isChecked()){
                    Lock = true;
                }else{
                    Lock = false;
                }
                System.out.println("geldi11");
                break;
            case R.id.tbScroll:
                if (tbScroll.isChecked()){
                    AutoScrollX = true;
                }else{
                    AutoScrollX = false;
                }
                System.out.println("geldi10");
                break;

            //case R.id.tbStream:
//                if (tbStream.isChecked()){
//                    if (Bluetooth.connectedThread != null)
//                        Bluetooth.connectedThread.write("E");
//                }else{
//                    if (Bluetooth.connectedThread != null)
//                        Bluetooth.connectedThread.write("Q");
//                }
            //break;
        }
        System.out.println("geldi9");
    }

}