package com.example.sylvancombinedui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class InventoryActivity extends AppCompatActivity {

    EditText itemID, orderID, itemName;
    Button insert, update, delete, view, export;
    DBHelper DB;
    DBImportExport IE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        itemID = findViewById(R.id.itemID);
        orderID = findViewById(R.id.orderID);
        itemName = findViewById(R.id.itemName);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        export = findViewById(R.id.btnExport);
        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemIDtxt = itemID.getText().toString();
                String orderIDtxt = orderID.getText().toString();
                String itemNameTXT = itemName.getText().toString();

                Boolean checkInsertData = DB.insertuserdata(itemIDtxt, orderIDtxt, itemNameTXT,
                        "N/A", "N/A", "N/A", "N/A");
                if(checkInsertData==true){
                    Toast.makeText(InventoryActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InventoryActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = itemID.getText().toString();
                String contactTXT = orderID.getText().toString();
                String dobTXT = itemName.getText().toString();

                Boolean checkUpdateData = DB.updateuserdata(nameTXT, contactTXT, dobTXT);
                if(checkUpdateData==true){
                    Toast.makeText(InventoryActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InventoryActivity.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = itemID.getText().toString();

                Boolean checkDeleteData = DB.deletedata(nameTXT);
                if(checkDeleteData==true){
                    Toast.makeText(InventoryActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InventoryActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Cursor res = DB.getdata();
               if(res.getCount() == 0) {
                   Toast.makeText(InventoryActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
               }
               StringBuffer buffer = new StringBuffer();
               while(res.moveToNext()) {
                   buffer.append("Item ID : " +res.getString(0) + "\n");
                   buffer.append("Order ID : " +res.getString(1) + "\n");
                   buffer.append("Item Name : " +res.getString(2) + "\n");
                   buffer.append("Item Toppings : " +res.getString(3) + "\n");
                   buffer.append("Item Holds : " +res.getString(4) + "\n");
                   buffer.append("Other : " +res.getString(5) + "\n");
                   buffer.append("Price : " +res.getString(6) + "\n");
                   buffer.append("Date Added : " +res.getString(7) + "\n\n");
               }

                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
               builder.setCancelable(true);
               builder.setTitle("User Entries");
               builder.setMessage(buffer.toString());
               builder.show();

            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String CurrentTime = "" + currentTime + "";

                DBImportExport.ExportImportDB checkExportData = new DBImportExport.ExportImportDB();
                Toast.makeText(InventoryActivity.this,
                        "Database was successfully backed up on " + CurrentTime + " ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
