package com.example.device_peripherasl_barcode_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public String eDynamoSerial(){
        String eDynamoProductName = "USB Swipe Reader";
        String eDynamoSerial = "";
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        if (deviceIterator.hasNext()) {
            for (UsbDevice usbDevice : deviceList.values()) {
                if (usbDevice.getProductName().equalsIgnoreCase(eDynamoProductName)) {
                    eDynamoSerial = usbDevice.getSerialNumber();
                    break;
                }
                else {
                    eDynamoSerial = "eDynamo Not Attached";
                }
            }
        }
        else {
            eDynamoSerial = "Not Attached";
        }
        return eDynamoSerial;
    };

    public String DynawaveSerial(){
        String DynawaveProductName = "USB Contactless Reader";
        String DynawaveSerial = "";
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        if (deviceIterator.hasNext()) {
            for (UsbDevice usbDevice : deviceList.values()) {
                if (usbDevice.getProductName().equalsIgnoreCase(DynawaveProductName)) {
                    DynawaveSerial = usbDevice.getSerialNumber();
                    break;
                }
                else {
                    DynawaveSerial = "eDynamo Not Attached";
                }
            }
        }
        else {
            DynawaveSerial = "Not Attached";
        }
        return DynawaveSerial;
    };

    public String UsbDevice(){


        String serial = "";


        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        if (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            serial =device.getSerialNumber();
        }
        else{
            serial = "Not Attached";
        }
        return serial;
    }

    public String getAndroidSerialNumber() {
        String deviceSerial = "";
        deviceSerial = Build.SERIAL;
        return deviceSerial;
    };

    public String getDeviceName(){
        String DeviceName = "";

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        if (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            DeviceName = device.getProductName();
        }
        else{
            DeviceName = "N/A";
        }
        return DeviceName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView imageView;
        ImageView imageView2;
        TextView textView;
        TextView textView2;

        String DeviceName = getDeviceName();
        String eDynamoSerial = eDynamoSerial();
        String DynawaveSerial = DynawaveSerial();
        String AndroidSerial= getAndroidSerialNumber();


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(AndroidSerial, BarcodeFormat.CODE_128,400,100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView = findViewById(R.id.AndroidTabletSerialBarcode);
            imageView.setImageBitmap(bitmap);
            textView = findViewById(R.id.DeviceSerialText);
            textView.setText("Android Serial Number:" + AndroidSerial);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        MultiFormatWriter multiFormatWriter2 = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix2 = multiFormatWriter2.encode(eDynamoSerial, BarcodeFormat.CODE_128,400,100);
            BarcodeEncoder barcodeEncoder2 = new BarcodeEncoder();
            Bitmap bitmap2 = barcodeEncoder2.createBitmap(bitMatrix2);
            imageView2 = findViewById(R.id.EdynamoserialBarcode);
            imageView2.setImageBitmap(bitmap2);
            textView2 = findViewById(R.id.EdynamoSerialText);
            textView2.setText("Edynamo: "+eDynamoSerial);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        MultiFormatWriter multiFormatWriter3 = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix3 = multiFormatWriter3.encode(DynawaveSerial, BarcodeFormat.CODE_128,400,100);
            BarcodeEncoder barcodeEncoder3 = new BarcodeEncoder();
            Bitmap bitmap3 = barcodeEncoder3.createBitmap(bitMatrix3);
            imageView2 = findViewById(R.id.DynawaveSerialBarcode);
            imageView2.setImageBitmap(bitmap3);
            textView2 = findViewById(R.id.DynawaveSerialText);
            textView2.setText("Dynawave: "+DynawaveSerial);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }



    /** Called when the user taps the Send button */
    public void showRSSI(View view) {
        Intent intent = new Intent(this, RSSI_Activity.class);
        startActivity(intent);
    }
}

