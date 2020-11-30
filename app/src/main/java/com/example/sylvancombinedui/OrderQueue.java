package com.example.sylvancombinedui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderQueue extends AppCompatActivity{
    private ArrayList<OrderQueueItem> mOrderQueueList;
    private RecyclerView mRecyclerView;
    private OrderQueueAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderqueue);
        DB = new DBHelper(this);

        createOrderQueueList();
        buildRecyclerView();
    }

    public void delItem(int position){
        mOrderQueueList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createOrderQueueList(){
        Cursor res = DB.getData();
        mOrderQueueList = new ArrayList<>();

        while(res.moveToNext()) {
            if (res.getString(9).equals("N")){
                if (!mOrderQueueList.contains(new OrderQueueItem("Order #" + res.getString(1)))) {
                    mOrderQueueList.add(new OrderQueueItem("Order #" + res.getString(1)));
                }
            }
        }

        /*mOrderQueueList.add(new OrderQueueItem("Order #2"));
        mOrderQueueList.add(new OrderQueueItem("Order #3"));*/
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.orderQueueRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(OrderQueue.this);
        mAdapter = new OrderQueueAdapter(mOrderQueueList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OrderQueueAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String itemMessage = mOrderQueueList.get(position).getOrderTitle();
                String orderValue = String.valueOf(itemMessage.charAt(itemMessage.length() - 1));
                //Toast.makeText(OrderQueue.this, itemMessage, Toast.LENGTH_SHORT).show();
                Cursor res = DB.getOrder(orderValue);
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Item ID : " + res.getString(0) + "\n");
                    buffer.append("Order ID : " + res.getString(1) + "\n");
                    buffer.append("Item Name : " + res.getString(2) + "\n");
                    buffer.append("Item Toppings : " + res.getString(3) + "\n");
                    buffer.append("Item Holds : " + res.getString(4) + "\n");
                    buffer.append("Other : " + res.getString(5) + "\n");
                    buffer.append("Price : " + res.getString(6) + "\n");
                    buffer.append("Date Added : " +res.getString(7) + "\n");
                    buffer.append("Taxable : " +res.getString(8) + "\n");
                    buffer.append("Processed : " +res.getString(9) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderQueue.this);
                builder.setCancelable(true);
                builder.setTitle("Order #" + orderValue);
                if (res.getCount() == 0) {
                    Toast.makeText(OrderQueue.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
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

            @Override
            public void onProcessClick(int position) {
                String itemMessage = mOrderQueueList.get(position).getOrderTitle();
                Toast.makeText(OrderQueue.this, itemMessage + " has been Processed", Toast.LENGTH_SHORT).show();
                delItem(position);
            }

            @Override
            public void onDeleteClick(int position) {
                String itemMessage = mOrderQueueList.get(position).getOrderTitle();
                Toast.makeText(OrderQueue.this, itemMessage + " has been Cancelled", Toast.LENGTH_SHORT).show();
                delItem(position);
            }
        });
    }
}
