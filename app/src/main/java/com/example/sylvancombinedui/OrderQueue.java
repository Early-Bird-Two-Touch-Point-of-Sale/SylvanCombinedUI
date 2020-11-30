package com.example.sylvancombinedui;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

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
        Cursor cursor = DB.getData();
        mOrderQueueList = new ArrayList<>();
        mOrderQueueList.add(new OrderQueueItem("Order #1"));
        mOrderQueueList.add(new OrderQueueItem("Order #2"));
        mOrderQueueList.add(new OrderQueueItem("Order #3"));
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
                Toast.makeText(OrderQueue.this, itemMessage, Toast.LENGTH_SHORT).show();
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
