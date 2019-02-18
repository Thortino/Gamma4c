package com.example.snitch.ProfSide;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.snitch.bluetooth3.R;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class AppelloActivity extends AppCompatActivity {

    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> dispositivi; //elenco dei dispositivi trovati, l'elenco è posto in un "set" che è una collezione di dati
    private ListView lv;
    private ArrayAdapter<String> adapter = null;
    private static final int BLUETOOTH_ON=1000;
    private BroadcastReceiver mReceiver;
    private final IntentFilter deviceFoundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND); //intent che ha il compito di trovare dispositivi


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


    }  //fine onCreate()
}
