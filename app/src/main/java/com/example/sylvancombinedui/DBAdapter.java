package com.example.sylvancombinedui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import androidx.core.app.ActivityCompat;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class DBAdapter{
    int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter FirstBluetoothAdapter;

    //DBAdapter hostDBAdapter = new DbAdapter(getApplicationContext());
    //performerDBAdapter.open();
    //refer to this stackoverflow article for an interesting idea:
    // https://stackoverflow.com/questions/7053809/share-sqlite-database-between-2-android-apps#7935032

}
