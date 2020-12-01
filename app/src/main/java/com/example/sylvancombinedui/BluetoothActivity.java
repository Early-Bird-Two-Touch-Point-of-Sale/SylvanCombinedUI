package com.example.sylvancombinedui;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BluetoothActivity extends AppCompatActivity {
    Button BTNOpenDialog, BTNUp, BTNSend;
    TextView TXTFolder;
    EditText dataPath;
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;
    File root, fileroot, curFolder;
    private List<String> fileList = new ArrayList<String>();
    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;
    BluetoothAdapter SecondBTAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        dataPath = (EditText)findViewById(R.id.FilePath);
        BTNOpenDialog = (Button)findViewById(R.id.OpenDialog);
        BTNSend = (Button)findViewById(R.id.sendBtooth);
        BTNOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPath.setText("");
                showDialog(CUSTOM_DIALOG_ID);
            }
        });

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;
        BTNSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendViaBluetooth();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(BluetoothActivity.this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("File Selector");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                TXTFolder = (TextView) dialog.findViewById(R.id.folder);
                BTNUp = (Button) dialog.findViewById(R.id.up);
                BTNUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });
                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if (selected.isDirectory()) {
                            ListDir(selected);
                        } else if (selected.isFile()) {
                            getselectedFile(selected);
                        } else {
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });
                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    public void getselectedFile(File f){
        dataPath.setText(f.getAbsolutePath());
        fileList.clear();
        dismissDialog(CUSTOM_DIALOG_ID);
    }

    public void ListDir(File f) {
        if (f.equals(root)) {
            BTNUp.setEnabled(false);
        } else {
            BTNUp.setEnabled(true);
        }
        curFolder = f;
        TXTFolder.setText(f.getAbsolutePath());
        dataPath.setText(f.getAbsolutePath());
        File[] files = f.listFiles();
        fileList.clear();

        for (File file : files) {
            fileList.add(file.getPath());
        }
        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        dialog_ListView.setAdapter(directoryList);
    }

    public void exit(View V) {
        SecondBTAdapter.disable();
        Toast.makeText(this, "Bluetooth is now disabled", Toast.LENGTH_LONG).show();
        finish();
    }

    public void sendViaBluetooth() {
        if(!dataPath.equals(null)){
            if(SecondBTAdapter == null) {
                Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_LONG).show();
            } else {
                enableBluetooth();
            }
        } else {
            Toast.makeText(this, "Select a file.", Toast.LENGTH_LONG).show();
        }
    }

    public void enableBluetooth() {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("*/*");
            File file = new File(dataPath.getText().toString());

            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(i, 0);
            if (list.size() > 0) {
                String packageName = null;
                String className = null;
                boolean found = false;

                for (ResolveInfo info : list) {
                    packageName = info.activityInfo.packageName;
                    if (packageName.equals("com.example.sylvancombinedui")) {
                        className = info.activityInfo.name;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(this, "Bluetooth not found", Toast.LENGTH_LONG).show();
                } else {
                    i.setClassName(packageName, className);
                    startActivity(i);
                }
            }
        } else {
            Toast.makeText(this, "Bluetooth Connection Cancelled", Toast.LENGTH_LONG).show();
        }
    }
}



/*public class BluetoothActivity extends AppCompatActivity {

    Button BTOn, BTOff, BTShow, BTDiscover, BTSendFile, BTServerListen;
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
    BluetoothDevice[] btArray;
    Intent BluetoothEnabledIntent;
    int REQUEST_CODE_FOR_ENABLE;

    //Reserved for security purposes
    private static final UUID Bluetooth_UUID = UUID.fromString("f6af5cda-bc4f-44aa-a718-a326bc6623ed");
    private static final String APP_NAME = "EarlyBird2TouchPOS";

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

        findViewByIDMethod();

        FirstBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothEnabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        REQUEST_CODE_FOR_ENABLE = 1;

        bluetoothONMethod();
        bluetoothOFFMethod();
        bluetoothCONNECTMethod();

        Intent discoverableIntent = new Intent(FirstBluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(FirstBluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 180);

        registerReceiver(scanModeReceiver, scanIntentFilter);

        AsyncThread asyncThread = new AsyncThread();
        asyncThread.start();

        if(!FirstBluetoothAdapter.isEnabled())
        {
            Intent enableIntent = new Intent(FirstBluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_CODE_FOR_ENABLE);
        }

        BTSendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Actual database file will go here --- if design works properly
            }
        });
    }

    private void findViewByIDMethod()
    {
        BTOn = findViewById(R.id.btnBluetoothOn);
        BTOff = findViewById(R.id.btnBluetoothOff);
        BTShow = findViewById(R.id.btnBluetoothShow);
        BTDiscover = findViewById(R.id.btnBluetoothDiscover);
        BTSendFile = findViewById(R.id.btnBluetoothSendFile);
        BTServerListen = findViewById(R.id.btnBluetoothServerListen);

        BTConnections = findViewById(R.id.ConnectionListView);

        BTConnection = findViewById(R.id.ConnectionTextView);
        ScanText = findViewById(R.id.ConnectionDiscoveryView);
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
                btArray = new BluetoothDevice[bt.size()];
                int index = 0;

                if(bt.size() > 0)
                {
                    for (BluetoothDevice device : bt)
                    {
                        btArray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    BTConnections.setAdapter(arrayAdapter);
                }
            }
        });

        BTServerListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerClass serverClass = new ServerClass();
                serverClass.start();
            }
        });

        BTConnections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass = new ClientClass(btArray[i]);
                clientClass.start();

                BTConnection.setText("Connecting");
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

    private class ServerClass extends Thread
    {
        private BluetoothServerSocket serverSocket;

        public ServerClass(){
            try {
                serverSocket = FirstBluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, Bluetooth_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            BluetoothSocket socket = null;

            while (socket == null)
            {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    threadHandler.sendMessage(message);

                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    threadHandler.sendMessage(message);
                }

                if(socket != null)
                {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    threadHandler.sendMessage(message);

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();

                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread
    {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass (BluetoothDevice device1)
        {
            device = device1;

            try {
                socket = device.createRfcommSocketToServiceRecord(Bluetooth_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            try {
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                threadHandler.sendMessage(message);

                sendReceive = new SendReceive(socket);
                sendReceive.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                threadHandler.sendMessage(message);
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

    } */

    //placeholder for clickable bluetooth button
    //final View imageButton = findViewById(R.id.imageButton);
    //imageButton.setOnClickListener(new OnClickListener(){
    //    @Override
    //    public void onClick(View view) {
    //        // do whatever we wish!
    //    }
    //});
//}


