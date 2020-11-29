package com.example.sylvancombinedui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderQueueAdapter extends RecyclerView.Adapter<OrderQueueAdapter.OrderQueueViewHolder>{
    private ArrayList<OrderQueueItem> mOrderQueueList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class OrderQueueViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public OrderQueueViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.orderTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        
                    }
                }
            });
        }
    }

    public OrderQueueAdapter(ArrayList<OrderQueueItem> orderQueueList) {
        mOrderQueueList = orderQueueList;
    }

    @NonNull
    @Override
    public OrderQueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_queue_item, parent, false);
        OrderQueueViewHolder oqvh = new OrderQueueViewHolder(v, mListener);
        return oqvh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderQueueViewHolder holder, int position) {
        OrderQueueItem currentItem = mOrderQueueList.get(position);

        holder.mTextView.setText(currentItem.getOrderTitle());
    }

    @Override
    public int getItemCount() {
        return mOrderQueueList.size();
    }
}
