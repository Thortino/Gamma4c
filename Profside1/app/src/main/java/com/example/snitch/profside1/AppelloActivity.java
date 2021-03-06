package com.example.snitch.profside1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;
import java.util.Set;

public class AppelloActivity extends AppCompatActivity
{


    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appello);

        Studente s1=new Studente("ted","84:C7:EA:A5:C6:9A");
        Studente s2=new Studente("max","18:1E:B0:46:AD:CF");

        Registro reg=new Registro(2);

        if(!reg.aggiungi(s1)){Toast.makeText(getApplicationContext(), "errore riempimento",Toast.LENGTH_SHORT).show();}
        if(!reg.aggiungi(s2)){Toast.makeText(getApplicationContext(), "errore riempimento",Toast.LENGTH_SHORT).show();}


        listView = (ListView) findViewById(R.id.lista);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0)
        {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices)
            {

                for(int i=0;i<2;i++)
                {
                    if(device.getAddress().equals(reg.classe[i].getMac()))
                        mDeviceList.add(reg.classe[i].getName());
                }
            }
        }
        ArrayAdapter<String> adapter = null;
        listView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                listView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mDeviceList));
            }
    };
}