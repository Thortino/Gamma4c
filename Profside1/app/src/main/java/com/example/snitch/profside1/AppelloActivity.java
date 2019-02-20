package com.example.snitch.profside1;

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

import java.util.Set;

public class AppelloActivity extends AppCompatActivity
{
    Studente s1=new Studente("ted","84:c7:ea:a5:c6:9a");

    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> dispositivi; //elenco dei dispositivi trovati, l'elenco è posto in un "set" che è una collezione di dati
    private ListView lv;
    private ArrayAdapter<String> adapter = null;
    private static final int BLUETOOTH_ON=1000;
    private BroadcastReceiver mReceiver;
    private final IntentFilter deviceFoundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND); //intent che ha il compito di trovare dispositivi

    //  int statoPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
    //  int statoPermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED);

    // BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        btAdapter = BluetoothAdapter.getDefaultAdapter();  //crea l'oggetto che colloquia con bluetooth del mio dispositivo
        if (!btAdapter.isEnabled())                        //se il bluetooth è disabilitato chiedo di abilitarlo
        {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //uso un intent per lanciare la richiesta di attivazione del bluetooth (avvia una subactivity), ma ancora non la lancia
            startActivityForResult(turnOn, BLUETOOTH_ON);       //startActivityForResult � un metodo che ci permette di recuperare qualcosa
            //in questo caso l'attivazione o meno del bluetooth tramite l'actvity predefinita Bluetooth_on, dopodich� implemento onActivityResult
        }

        /*
        Il BroadcastReceiver � un componente Android in grado di rimanere in ascolto su una gruppo specifico di
        messaggi chiamati Intent Broadcast, a loro volta inviati tramite il metodo sendBroadcast(), che sono
        utilizzati per notificare alle applicazioni in ascolto determinati eventi come ad esempio:
        informazioni sulla batteria scarica, cambiamenti della connessione, chiamate e sms in arrivo.

        A questo proposito, anche il cambiamento di stato del Bluetooth � identificabile tramite un BroadcastReceiver,
        in quanto l�evento � notificato per mezzo di un Intent Broadcast.



         */

        mReceiver = new BroadcastReceiver() {    //....viene attivato dal button2

            public void onReceive(Context context, Intent intent) {
                Log.e("br","broadcast attivo");  //messaggio sull'ide
                Toast.makeText(getApplicationContext(), "broadcast attivo " ,   //messaggio sull'activity
                        Toast.LENGTH_SHORT).show();

                String action = intent.getAction();        //recupero quale azione ha avuto luogo dall'attivazione del bluetooth in una stringa

                // when a new device is discovered, add it to the TextArea
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {  //se l'azione corrisponde a quella di trovare dispositivi

                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); //dall'intent attraverso il metodo get.. recupera i dati del dispositivo

                    Log.e("br","dispositivo trovato");
                    Toast.makeText(getApplicationContext(), "dispositivo trovato " ,
                            Toast.LENGTH_SHORT).show();
//                    for(BluetoothDevice dv : dispositivi)
                    adapter.add(device.getName() + "\n" + device.getAddress());  //aggiungo alla lista
                }


            }
        };



        // bottone che recupera i dispositivi gi� associati e disponibili
        Button button;
        button=(Button)findViewById(R.id.button);
        // operazioni da fare su click
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!btAdapter.isEnabled())
                {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, BLUETOOTH_ON);
                }
                else
                    load();
            }
        });

        // bottone2, trova dispostivi con bluetooth attivato nelle vicinanze
        Button button2;
        button2=(Button)findViewById(R.id.button2);
        // operazioni da fare su click
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!btAdapter.isEnabled())  //controlla ulteriormente se il bluetooth � attivo
                {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, BLUETOOTH_ON);
                }
                else  //questo else � sbagliato, ci vorrebbero le parentesi, oppure togli proprio l'if
                    Log.e("br","carico il broadcast");
                registerReceiver( mReceiver, deviceFoundFilter ); //attiva il broadcast receveir
                btAdapter.startDiscovery(); //scopri dispositivi
                Toast.makeText(getApplicationContext(), "startDiscovery " ,
                        Toast.LENGTH_SHORT).show();


/*
                registerReceiver( mReceiver, deviceFoundFilter ); //attiva il broadcast receveir
                btAdapter.startDiscovery(); //scopri dispositivi
                Toast.makeText(getApplicationContext(), "startDiscovery " ,
                        Toast.LENGTH_SHORT).show();

*/
            }
        });


        //list view
        lv = (ListView)findViewById(R.id.lista);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);

        //gestione della listview, � solo un esempio per far vedere che la lista � "cliccabile"
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id)
            {
                Toast.makeText(getApplicationContext(), "Hai selezionato l'elemento " + position,
                        Toast.LENGTH_SHORT).show();
                String listElement = (String)parent.getItemAtPosition(position);


            }
        });





    }  //fine onCreate()




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  //metodo che dice come gestire l'attivazione del bluetooth (fa apparire solo un messaggio)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==BLUETOOTH_ON && resultCode==RESULT_OK)
        {
            Toast.makeText(getApplicationContext(), "sono nell'activity result",
                    Toast.LENGTH_SHORT).show();
            // load();
        }
    }




    private void load() //carica dispositivi trovati nella lista degli associati
    {
        dispositivi = btAdapter.getBondedDevices();  //dispositivi gi� registarti
        adapter.clear();
        for(BluetoothDevice bt : dispositivi)
            adapter.add(bt.getName()+ "\n" + bt.getAddress()); //aggiunge alla lista il dispositivo
    }
}
