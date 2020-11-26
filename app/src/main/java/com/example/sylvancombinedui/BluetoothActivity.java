package com.example.sylvancombinedui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BluetoothActivity extends AppCompatActivity {

    Button BTOn, BTOff;

    BluetoothAdapter FirstBluetoothAdapter;
    Intent BluetoothEnabledIntent;
    int REQUEST_CODE_FOR_ENABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        FirstBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothEnabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        REQUEST_CODE_FOR_ENABLE = 1;

        BTOn = findViewById(R.id.btnBluetoothOn);
        BTOff = findViewById(R.id.btnBluetoothOff);

        bluetoothONMethod();
        bluetoothOFFMethod();
    }

    //BluetoothActivity hostDBAdapter = new DbAdapter(getApplicationContext());
    //performerDBAdapter.open();
    //refer to this stackoverflow article for an interesting idea:
    // https://stackoverflow.com/questions/7053809/share-sqlite-database-between-2-android-apps#7935032

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_ENABLE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth is enabled.", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth enable process stopped.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bluetoothOFFMethod() {
        BTOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirstBluetoothAdapter.isEnabled()) {
                    FirstBluetoothAdapter.disable();
                }
            }
        });
    }

    private void bluetoothONMethod() {
        BTOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirstBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
                } else {
                    if (!FirstBluetoothAdapter.isEnabled()) {
                        startActivityForResult(BluetoothEnabledIntent, REQUEST_CODE_FOR_ENABLE);
                    }
                }
            }
        });
    }
}


