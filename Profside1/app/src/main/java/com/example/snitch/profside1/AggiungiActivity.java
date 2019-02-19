package com.example.snitch.profside1;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;


public class AggiungiActivity extends AppCompatActivity
{
    ListView listViewDetected;
    Button buttonSearch,buttonOn,buttonDesc,buttonOff;
    ArrayAdapter<String> adapter,detectedAdapter;
    static HandleSeacrh handleSeacrh;
    BluetoothDevice bdDevice;
    BluetoothClass bdClass;
    private ButtonClicked clicked;
    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;
    ListItemClicked listItemClicked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);
        listViewDetected = (ListView) findViewById(R.id.listViewDetected);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonDesc = (Button) findViewById(R.id.buttonDesc);
        buttonOff = (Button) findViewById(R.id.buttonOff);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        clicked = new ButtonClicked();
        handleSeacrh = new HandleSeacrh();
        /*
         * the above declaration is just for getting the paired bluetooth devices;
         * this helps in the removing the bond between paired devices.
         */
        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
        detectedAdapter = new ArrayAdapter<String>(AggiungiActivity.this, android.R.layout.simple_list_item_single_choice);
        listViewDetected.setAdapter(detectedAdapter);
        listItemClicked = new ListItemClicked();
        detectedAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        buttonOn.setOnClickListener(clicked);
        buttonSearch.setOnClickListener(clicked);
        buttonDesc.setOnClickListener(clicked);
        buttonOff.setOnClickListener(clicked);
        listViewDetected.setOnItemClickListener(listItemClicked);

    }
    class ListItemClicked implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            bdDevice = arrayListBluetoothDevices.get(position);
            //bdClass = arrayListBluetoothDevices.get(position);
            Log.i("Log", "The dvice : "+bdDevice.toString());
            /*
             * here below we can do pairing without calling the callthread(), we can directly call the
             * connect(). but for the safer side we must usethe threading object.
             */
            //callThread();
            //connect(bdDevice);
            Boolean isBonded = false;
            try {
                isBonded = createBond(bdDevice);
                if(isBonded)
                {
                    //arrayListpaired.add(bdDevice.getName()+"\n"+bdDevice.getAddress());
                    //adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }//connect(bdDevice);
            Log.i("Log", "The bond is created: "+isBonded);
        }
    }
    private Boolean connect(BluetoothDevice bdDevice) {
        Boolean bool = false;
        try {
            Log.i("Log", "service method is called ");
            Class cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class[] par = {};
            Method method = cl.getMethod("createBond", par);
            Object[] args = {};
            bool = (Boolean) method.invoke(bdDevice);//, args);// this invoke creates the detected devices paired.
            //Log.i("Log", "This is: "+bool.booleanValue());
            //Log.i("Log", "devicesss: "+bdDevice.getName());
        } catch (Exception e) {
            Log.i("Log", "Inside catch of serviceFromDevice Method");
            e.printStackTrace();
        }
        return bool.booleanValue();
    };


    public boolean removeBond(BluetoothDevice btDevice)
            throws Exception
    {
        Class btClass = Class.forName("android.bluetooth.BluetoothDevice");
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    public boolean createBond(BluetoothDevice btDevice)
            throws Exception
    {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    class ButtonClicked implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonOn:
                    onBluetooth();
                    break;
                case R.id.buttonSearch:
                    arrayListBluetoothDevices.clear();
                    startSearching();
                    break;
                case R.id.buttonDesc:
                    makeDiscoverable();
                    break;
                case R.id.buttonOff:
                    offBluetooth();
                    break;
                default:
                    break;
            }
        }
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message msg = Message.obtain();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Toast.makeText(context, "ACTION_FOUND", Toast.LENGTH_SHORT).show();

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try
                {
                    //device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                    //device.getClass().getMethod("cancelPairingUserInput", boolean.class).invoke(device);
                }
                catch (Exception e) {
                    Log.i("Log", "Inside the exception: ");
                    e.printStackTrace();
                }

                if(arrayListBluetoothDevices.size()<1) // this checks if the size of bluetooth device is 0,then add the
                {                                           // device to the arraylist.
                    detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                    arrayListBluetoothDevices.add(device);
                    detectedAdapter.notifyDataSetChanged();
                }
                else
                {
                    boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                    for(int i = 0; i<arrayListBluetoothDevices.size();i++)
                    {
                        if(device.getAddress().equals(arrayListBluetoothDevices.get(i).getAddress()))
                        {
                            flag = false;
                        }
                    }
                    if(flag == true)
                    {
                        detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                        arrayListBluetoothDevices.add(device);
                        detectedAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };
    private void startSearching() {
        Log.i("Log", "in the start searching method");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        AggiungiActivity.this.registerReceiver(myReceiver, intentFilter);
        bluetoothAdapter.startDiscovery();
    }
    private void onBluetooth() {
        if(!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();
            Log.i("Log", "Bluetooth is Enabled");
        }
    }
    private void offBluetooth() {
        if(bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.disable();
        }
    }
    private void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.i("Log", "Discoverable ");
    }
    class HandleSeacrh extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:

                    break;

                default:
                    break;
            }
        }
    }
}