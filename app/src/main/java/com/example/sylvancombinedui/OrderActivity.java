package com.example.sylvancombinedui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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
    final String ORDER_KEY = "Order Key";
    final HashMap<String, Pair<String, Double>> ITEM_MAP
          = new HashMap<>();

    //final HashMap<Pair<String, String>, Pair<Double, String>> ITEM_MAP
     //       = new HashMap<>();

    private Button ScanBtn;
    private Button AddBtn;
    private Button OrderBtn;
    private Button MenuBtn;
    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<OrderItem> orderList;


    DBHelper DB;
    SharedPreferences pref;
    int OrderCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        DB = new DBHelper(this);
        pref = this.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        OrderCount = pref.getInt(ORDER_KEY, 0);

        //these are self-explanatory
        createOrderList();
        buildRecyclerView();
        setButtons();
        buildMap();

        //for the Scanner
        ScanBtn = findViewById(R.id.scanButton);
        ScanBtn.setOnClickListener(this);
    }

    public void buildMap() {
        /*ITEM_MAP.put(Pair.create("01212901", "Pepsi)"), Pair.create(2.09, "Y"));
        ITEM_MAP.put(Pair.create("078000000283", "7-Up (2-litre)"), Pair.create(2.39, "Y"));
        ITEM_MAP.put(Pair.create("078000113457", "Sunkissed"), Pair.create(2.39, "Y"));
        ITEM_MAP.put(Pair.create("078000092455", "Dejablue"), Pair.create(2.00, "Y"));
        ITEM_MAP.put(Pair.create("011423941276", "Repel: Clothing and Gear"), Pair.create(5.99, "Y"));
        ITEM_MAP.put(Pair.create("639277292063", "Max Block (30 spf)"), Pair.create(2.99, "Y"));
        ITEM_MAP.put(Pair.create("078000052459", "Mug Root Beer"), Pair.create(2.39, "Y")); //ask Sylvan about this
        ITEM_MAP.put(Pair.create("070602489008", "Rocky Road S'Mores"), Pair.create(1.25, "N"));
        ITEM_MAP.put(Pair.create("041419420010", "Combos Stuffed Snacks"), Pair.create(1.25, "N"));
        ITEM_MAP.put(Pair.create("03405408", "KitKat White Chocolate"), Pair.create(1.25, "N"));
        ITEM_MAP.put(Pair.create("03424607", "KitKat Bar"), Pair.create(1.25, "N"));*/

        ITEM_MAP.put("078000000283", Pair.create("Pepsi", 2.09));
        ITEM_MAP.put("078000000283", Pair.create("7-Up (2-litre)", 2.39));
        ITEM_MAP.put("078000113457", Pair.create("Sunkissed", 2.39));
        ITEM_MAP.put("078000092455", Pair.create("Dejablue", 2.00));
        ITEM_MAP.put("011423941276", Pair.create("Repel: Clothing and Gear", 5.99));
        ITEM_MAP.put("639277292063", Pair.create("Max Block (30 spf)", 2.99));
        ITEM_MAP.put("078000052459", Pair.create("Mug Root Beer", 2.39));
        ITEM_MAP.put("070602489008", Pair.create("Rocky Road S'Mores", 1.25));
        ITEM_MAP.put("041419420010", Pair.create("Combos Stuffed Snacks", 1.25));
        ITEM_MAP.put("03405408", Pair.create("KitKat White Chocolate", 1.25));
        ITEM_MAP.put("03424607", Pair.create("KitKat Bar", 1.25));
    }

    public void addItem(int position) {
        orderList.add(orderList.size(), new OrderItem(R.drawable.ic_android,
                "New Item at Position " + position, "Line 2", "Line 3", "Line 4", "Line 5"));
        mAdapter.notifyItemInserted(orderList.size());
    }

    public void delItem (int position) {
        orderList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createOrderList() {
        orderList = new ArrayList<>();

        /*orderList.add(new OrderItem(R.drawable.ic_android, "Dummy Item #1", "5555555555555"));
        orderList.add(new OrderItem(R.drawable.ic_car, "Dummy Item #2", "5555555555556"));
        orderList.add(new OrderItem(R.drawable.ic_sun, "Dummy Item #3", "5555555555557"));*/
    }

    public void buildRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.orderRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OrderAdapter(orderList);

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

                // set positive button for the manual entry
                alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String userCode = userAnswer.getText().toString();
                        if (userCode.length() == 14 || userCode.length() == 13 || userCode.length() == 12 || userCode.length() == 8) {
                            if (ITEM_MAP.containsKey(userCode)) {
                                orderList.add(orderList.size(),
                                        new OrderItem(R.drawable.ic_android, ITEM_MAP.get(userCode).first,
                                                "N/A", "N/A", "Barcode: " + userCode, "$" + ITEM_MAP.get(userCode).second.toString()));
                                mAdapter.notifyItemInserted(orderList.size());
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
                takeOrder();
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
                String foodPrice = data.getStringExtra("Price");
                //String taxable = data.getStringExtra("Taxable");
                //Toast.makeText(context, foodItem +
                //        "\n" + optToppings + "\n" + holds, Toast.LENGTH_SHORT).show();

                orderList.add(orderList.size(), new OrderItem(R.drawable.ic_android, foodItem,
                        "Toppings: " + optToppings, "Holds: " + holds, "(Note: " + other + ")", foodPrice));
                mAdapter.notifyItemInserted(orderList.size());
            }
        }
        else {//else it's coming from the Scanner
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if (result != null) {
                if (result.getContents() != null) {
                    if (result.getContents().length() == 14 || result.getContents().length() == 13 || result.getContents().length() == 12 ||
                            result.getContents().length() == 8) {
                        if (ITEM_MAP.containsKey(result.getContents())) {
                            try {
                                orderList.add(orderList.size(), new OrderItem(R.drawable.ic_android, ITEM_MAP.get(result.getContents()).first,
                                        "N/A", "N/A", "Barcode: " + result.getContents(),"$" + ITEM_MAP.get(result.getContents()).second.toString()));
                                mAdapter.notifyItemInserted(orderList.size());
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

    public void takeOrder(){
        StringBuffer orderMessage = new StringBuffer("");
        AlertDialog.Builder orderDialog = new AlertDialog.Builder(this);
        orderDialog.setTitle("Your Order");
        orderDialog.setCancelable(false);
        for (int counter = 0; counter < orderList.size(); counter++) {
            orderMessage.append("Item " + (1 + counter) + ": " + "\n");
            orderMessage.append(orderList.get(counter).getText1() + "\n");
            orderMessage.append(orderList.get(counter).getText2() + "\n");
            orderMessage.append(orderList.get(counter).getText3() + "\n");
            orderMessage.append(orderList.get(counter).getText4() + "\n");
            orderMessage.append(orderList.get(counter).getText5() + "\n" + "\n");
        }
        orderDialog.setMessage(orderMessage);
        orderDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(OrderActivity.this, "Ordered", Toast.LENGTH_SHORT).show();
                OrderCount++;

                SharedPreferences.Editor edit = pref.edit();
                edit.putInt(ORDER_KEY, OrderCount);
                edit.commit();

                for (int counter = 0; counter < orderList.size(); counter++) {
                    DB.insertUserData(OrderCount + "-" + (1 + counter),
                            Integer.toString(OrderCount), orderList.get(counter).getText1(),
                            orderList.get(counter).getText2(), orderList.get(counter).getText3(),
                            orderList.get(counter).getText4(), orderList.get(counter).getText5());
                }


                int size = orderList.size();
                if (size > 0) {
                    for (int j = 0; j < size; j++) {
                        orderList.remove(0); //have to use 0, because the orderItems will
                    }                              // migrate to that position, and you'll delete
                    mAdapter.notifyItemRangeRemoved(0, size); //every other item with j
                }
            }
        });
        orderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(OrderActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        orderDialog.show();
    }
}