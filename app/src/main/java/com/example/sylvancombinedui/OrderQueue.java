package com.example.sylvancombinedui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderQueue extends AppCompatActivity{
    private ArrayList<OrderQueueItem> mOrderQueueList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderqueue);

        createOrderQueueList();
        buildRecyclerView();
    }

    public void delItem(int position){
        mOrderQueueList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createOrderQueueList(){
        mOrderQueueList = new ArrayList<>();
        mOrderQueueList.add(new OrderQueueItem("Order Example"));
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.orderQueueRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(OrderQueue.this);
        mAdapter = new OrderQueueAdapter(mOrderQueueList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
