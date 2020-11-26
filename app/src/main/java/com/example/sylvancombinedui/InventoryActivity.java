package com.example.sylvancombinedui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
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
    Button insert, update, delete, view, export, viewOrder, dailyTotal;
    DBHelper DB;
    DBImportExport IE;
    boolean adminValid = false;
    String correctAdminUsername = "Admin";
    String correctAdminPassword = "Password";

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
        viewOrder = findViewById(R.id.btnViewOrder);
        dailyTotal = findViewById(R.id.btnDailyTotal);
        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemIDtxt = itemID.getText().toString();
                String orderIDtxt = orderID.getText().toString();
                String itemNameTXT = itemName.getText().toString();

                Boolean checkInsertData = DB.insertUserData(itemIDtxt, orderIDtxt, itemNameTXT,
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

                Boolean checkUpdateData = DB.updateUserData(nameTXT, contactTXT, dobTXT);
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

                Boolean checkDeleteData = DB.deleteData(nameTXT);
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
               Cursor res = DB.getData();
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

        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(InventoryActivity.this);

                alert.setTitle("View Order");
                alert.setMessage("Please Input the Order Number");

                // Set an EditText view to get user input
                final EditText input = new EditText(InventoryActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        Cursor res = DB.getOrder(value);
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
                        builder.setTitle("Order #" + value);
                        if(res.getCount() == 0) {
                            Toast.makeText(InventoryActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                            builder.setMessage("No Entry Exists");
                        } else {
                            builder.setMessage(buffer.toString());
                        }
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Toast.makeText(InventoryActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(InventoryActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });

        dailyTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View adminPassView = View.inflate(InventoryActivity.this, R.layout.admin_password, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(InventoryActivity.this);
                final EditText etAdminUsername = (EditText) adminPassView.findViewById(R.id.editTextAdminUsername);
                final EditText etAdminPassword = (EditText) adminPassView.findViewById(R.id.editTextAdminPassword);

                alert.setTitle("Admin Verification");
                alert.setMessage("Please Input an Admin Username/Password");
                alert.setView(adminPassView);
                alert.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String adminUsername = etAdminUsername.getText().toString();
                        final String adminPassword = etAdminPassword.getText().toString();
                        if (adminUsername.isEmpty()){
                            Toast.makeText(InventoryActivity.this, "Please Enter a Username", Toast.LENGTH_SHORT).show();
                        }
                        else if (adminPassword.isEmpty()){
                            Toast.makeText(InventoryActivity.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            adminValid = validate(adminUsername, adminPassword);
                            if (!adminValid) {
                                Toast.makeText(InventoryActivity.this, "Invalid Admin Username/Password", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(InventoryActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                //showDailyOrder();
                            }
                        }
                    }
                });
                alert.show();
            }
        });
    }

    public void showDailyOrder() {

    }

    private boolean validate(String username, String password){
        if(username.equals(correctAdminUsername) && password.equals(correctAdminPassword)){
            return true;
        }
        else {
            return false;
        }
    }
}
