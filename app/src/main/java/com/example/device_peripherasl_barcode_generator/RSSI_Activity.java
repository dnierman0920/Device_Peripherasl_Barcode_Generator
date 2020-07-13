package com.example.device_peripherasl_barcode_generator;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class RSSI_Activity extends AppCompatActivity {



    public String GetWifiDBM() {
        int dbm = 0;
        String stringdbm = "";
        Context context = getApplicationContext();


        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                dbm = wifiInfo.getRssi();
                stringdbm = Integer.toString(dbm);
            }
        }

        return stringdbm;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_s_s_i_);


        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    Thread t = new Thread();
                    while (!t.isInterrupted()) {
                        t.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView textView = findViewById(R.id.RSSI);
                                String message = GetWifiDBM();
                                textView.setText(message);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }


}
