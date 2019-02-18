package com.example.snitch.profside1;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();//if null bluetooth non supportato

        if(bt==null)
        {
            Toast.makeText(getBaseContext(), "bluetooth non supportato", Toast.LENGTH_SHORT).show();
        }

        if (!bt.isEnabled())     //controllo che il bluettooth sia acceso
        {
            Intent enablebt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enablebt, REQUEST_ENABLE_BT);
        }



    }

    public void aggiungi(View v)
    {
        Intent i = new Intent(MainActivity.this, AggiungiActivity.class);
        MainActivity.this.startActivity(i);     //fai partire la nuova activity
    }

    public void appello(View v)
    {
        Intent i = new Intent(MainActivity.this, AppelloActivity.class);
        MainActivity.this.startActivity(i);     //fai partire la nuova activity
    }
}
