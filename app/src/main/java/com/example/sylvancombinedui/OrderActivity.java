package com.example.sylvancombinedui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    final Context context = this;
    final int MENU_REQUEST = 55;
    final int MENU_RESULT = 77;
    final String TAG = "2";

    private Button ScanBtn;
    private Button AddBtn;
    private Button OrderBtn;
    private Button MenuBtn;
    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<OrderItem> dummyOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //these are self-explanatory
        createOrderList();
        buildRecyclerView();
        setButtons();

        //for the Scanner
        ScanBtn = findViewById(R.id.scanButton);
        ScanBtn.setOnClickListener(this);
    }

    public void addItem(int position) {
        dummyOrderList.add(dummyOrderList.size(), new OrderItem(R.drawable.ic_android,
                "New Item at Position " + position, "Line 2", "Line 3", "Line 4"));
        mAdapter.notifyItemInserted(dummyOrderList.size());
    }

    public void delItem (int position) {
        dummyOrderList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createOrderList() {
        dummyOrderList = new ArrayList<>();

        /*dummyOrderList.add(new OrderItem(R.drawable.ic_android, "Dummy Item #1", "5555555555555"));
        dummyOrderList.add(new OrderItem(R.drawable.ic_car, "Dummy Item #2", "5555555555556"));
        dummyOrderList.add(new OrderItem(R.drawable.ic_sun, "Dummy Item #3", "5555555555557"));*/
    }

    public void buildRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.orderRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OrderAdapter(dummyOrderList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                delItem(position);
            }
        });
    }

    public void setButtons() {
        //to manually edit list
        AddBtn = findViewById(R.id.addButton);
        OrderBtn = findViewById(R.id.orderButton);
        MenuBtn = findViewById(R.id.menuButton);

        MenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // load the barcode_dialog_prompt_user.xml layout and inflate to view
                LayoutInflater layoutinflater = LayoutInflater.from(context);
                View promptUserView = layoutinflater.inflate(R.layout.barcode_dialog_prompt_user, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptUserView);

                final EditText userAnswer = (EditText) promptUserView.findViewById(R.id.userBarcode);

                alertDialogBuilder.setTitle("Manual Barcode Entry");

                //set HashMap of possible values
                final HashMap<String, String> itemMap
                        = new HashMap<>();
                itemMap.put("01212901", "Pepsi");
                itemMap.put("078000000283", "7-Up (2-litre)");
                itemMap.put("078000113457", "Sunkissed");
                itemMap.put("078000092455", "Dejablue");
                itemMap.put("011423941276", "Repel: Clothing and Gear");
                itemMap.put("639277292063", "Max Block (30 spf)");
                itemMap.put("078000052459", "Mug Root Beer");
                itemMap.put("070602489008", "Rocky Road S'Mores");
                itemMap.put("041419420010", "Combos Stuffed Snacks");
                itemMap.put("03405408","KitKat White Chocolate");
                itemMap.put("03424607", "KitKat Bar");

                // set positive button for the manual entry
                alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String userCode = userAnswer.getText().toString();
                        if (userCode.length() == 14 || userCode.length() == 13 || userCode.length() == 12 || userCode.length() == 8) {
                            if (itemMap.containsKey(userCode)) {
                                dummyOrderList.add(dummyOrderList.size(),
                                        new OrderItem(R.drawable.ic_android, itemMap.get(userCode),
                                                userCode, "Other", "Price"));
                                mAdapter.notifyItemInserted(dummyOrderList.size());
                                Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, "Item not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(context, "Invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // all set and time to build and show up!
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        OrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrderActivity.this, "Ordered", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //open menu
    public void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivityForResult(intent, MENU_REQUEST);
    }

    @Override
    public void onClick(View view) {
        scanCode();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // if we're getting the result from a menu
        if (requestCode == MENU_REQUEST){
            if(resultCode == MENU_RESULT){
                //get vars from MenuActivity
                String foodItem = data.getStringExtra("Food Item");
                String optToppings = data.getStringExtra("Optional Toppings");
                String holds = data.getStringExtra("Holds");
                String other = data.getStringExtra("EditText");
                //Toast.makeText(context, foodItem +
                //        "\n" + optToppings + "\n" + holds, Toast.LENGTH_SHORT).show();

                dummyOrderList.add(dummyOrderList.size(), new OrderItem(R.drawable.ic_android, foodItem,
                        "Toppings: " + optToppings + ", Holds: " + holds, other, "Price"));
                mAdapter.notifyItemInserted(dummyOrderList.size());
            }
        }
        else {//else it's coming from the Scanner
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            final HashMap<String, String> itemMap
                    = new HashMap<>();
            itemMap.put("01212901", "Pepsi");
            itemMap.put("078000000283", "7-Up (2-litre)");
            itemMap.put("078000113457", "Sunkissed");
            itemMap.put("078000092455", "Dejablue");
            itemMap.put("011423941276", "Repel: Clothing and Gear");
            itemMap.put("639277292063", "Max Block (30 spf)");
            itemMap.put("078000052459", "Mug Root Beer");
            itemMap.put("070602489008", "Rocky Road S'Mores");
            itemMap.put("041419420010", "Combos Stuffed Snacks");
            itemMap.put("03405408", "KitKat White Chocolate");
            itemMap.put("03424607", "KitKat Bar");

            if (result != null) {
                if (result.getContents() != null) {
                    if (result.getContents().length() == 14 || result.getContents().length() == 13 || result.getContents().length() == 12 ||
                            result.getContents().length() == 8) {
                        if (itemMap.containsKey(result.getContents())) {
                            try {
                                dummyOrderList.add(dummyOrderList.size(), new OrderItem(R.drawable.ic_android, itemMap.get(result.getContents()),
                                        result.getContents(), "Other", "Price"));
                                mAdapter.notifyItemInserted(dummyOrderList.size());
                                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                            } catch(Exception e){
                                Log.e(TAG, e.getMessage(), e);
                            }
                        } else {
                            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}