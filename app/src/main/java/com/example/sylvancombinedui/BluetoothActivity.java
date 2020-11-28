package com.example.sylvancombinedui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.BreakIterator;
import java.util.Set;
import java.util.UUID;

import java.lang.Thread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BluetoothActivity extends AppCompatActivity {

    Button BTOn, BTOff, BTShow, BTDiscover, BTSendFile;
    ListView BTConnections;
    TextView BTConnection, ScanText;
    IntentFilter scanIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
    BroadcastReceiver scanModeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED))
            {
                int modeValue = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                if(modeValue == BluetoothAdapter.SCAN_MODE_CONNECTABLE)
                {
                    ScanText.setText("This device is not in discovery mode but still connect to other devices");
                } else if (modeValue == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    ScanText.setText("This device is in discovery mode");
                } else if (modeValue == BluetoothAdapter.SCAN_MODE_NONE){
                    ScanText.setText("This device is not in discovery mode and can not receive new connections");
                } else
                {
                    ScanText.setText("Error, Bluetooth Connectivity may not be enabled for this device");
                }
            }
        }
    };

    BluetoothAdapter FirstBluetoothAdapter;
    Intent BluetoothEnabledIntent;
    int REQUEST_CODE_FOR_ENABLE;

    //Reserved for security purposes
    UUID Bluetooth_UUID = UUID.fromString("f6af5cda-bc4f-44aa-a718-a326bc6623ed");

    SendReceive sendReceive;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        FirstBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothEnabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        REQUEST_CODE_FOR_ENABLE = 1;

        BTOn = findViewById(R.id.btnBluetoothOn);
        BTOff = findViewById(R.id.btnBluetoothOff);
        BTShow = findViewById(R.id.btnBluetoothShow);
        BTDiscover = findViewById(R.id.btnBluetoothDiscover);
        BTSendFile = findViewById(R.id.btnBluetoothSendFile);

        BTConnections = findViewById(R.id.ConnectionListView);
        BTConnection = findViewById(R.id.ConnectionTextView);
        ScanText = findViewById(R.id.ConnectionDiscoveryView);

        bluetoothONMethod();
        bluetoothOFFMethod();
        bluetoothCONNECTMethod();

        Intent discoverableIntent = new Intent(FirstBluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(FirstBluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 180);

        registerReceiver(scanModeReceiver, scanIntentFilter);

        AsyncThread asyncThread = new AsyncThread();
        asyncThread.start();

        BTSendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Actual database file will go here --- if design works properly
            }
        });
    }

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

    private void bluetoothCONNECTMethod() {
        BTShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> bt = FirstBluetoothAdapter.getBondedDevices();
                String[] strings = new String[bt.size()];
                int index = 0;

                if(bt.size() > 0)
                {
                    for (BluetoothDevice device : bt)
                    {
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    BTConnections.setAdapter(arrayAdapter);
                }
            }
        });
    }

    Handler threadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case STATE_LISTENING:
                    BTConnection.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    BTConnection.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    BTConnection.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    BTConnection.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuffer = (byte[]) msg.obj;
                    String tempMsg = new String(readBuffer, 0, msg.arg1);
                    BTConnection.setText("File Received");
                    break;
            }
            return true;
        }
    });

    private class AsyncThread extends Thread
    {
        public void run()
        {
            for (int i = 0; i<50; i++)
            {
                Message msg = Message.obtain();
                msg.arg1 = i;
                threadHandler.sendMessage(msg);

                try {
                    sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive (BluetoothSocket socket)
        {
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;
            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e){
                e.printStackTrace();
            }

            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true)
            {
                try {
                    bytes = inputStream.read(buffer);
                    threadHandler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try{
                outputStream.write(bytes);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //placeholder for clickable bluetooth button
    //final View imageButton = findViewById(R.id.imageButton);
    //imageButton.setOnClickListener(new OnClickListener(){
    //    @Override
    //    public void onClick(View view) {
    //        // do whatever we wish!
    //    }
    //});
}


