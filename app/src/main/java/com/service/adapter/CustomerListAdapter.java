package com.service.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.CustomerList;
import com.service.rwtpos.BillListActivity;
import com.service.rwtpos.R;

import java.util.ArrayList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerListViewHolder> {

    private ArrayList<CustomerList> customer_list;
    private Context context;

    public class CustomerListViewHolder extends RecyclerView.ViewHolder {
        public TextView customer_name_tv, customer_mobile_tv, view_customer_detail_tv;
        CardView cardview;

        public CustomerListViewHolder(View itemView) {
            super(itemView);
            customer_name_tv = itemView.findViewById(R.id.customer_name_tv);
            customer_mobile_tv = itemView.findViewById(R.id.customer_mobile_tv);
            view_customer_detail_tv = itemView.findViewById(R.id.view_customer_detail_tv);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }

    public CustomerListAdapter(Context context, ArrayList<CustomerList> list) {
        customer_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_layout, parent, false);
        CustomerListViewHolder evh = new CustomerListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerListViewHolder holder, int i) {
        final CustomerList currentItem = customer_list.get(i);
        holder.customer_name_tv.setText(currentItem.getName());
        holder.customer_mobile_tv.setText(currentItem.getMobile_number());
        holder.cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BillListActivity.class);
                        intent.putExtra("customer_id", currentItem.getCustomer_id());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return customer_list.size();
    }

    public void filterList(ArrayList<CustomerList> filteredList) {
        customer_list = filteredList;
        notifyDataSetChanged();
    }
}
