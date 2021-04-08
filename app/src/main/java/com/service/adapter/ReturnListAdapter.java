package com.service.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.model.ReturnListModel;
import com.service.rwtpos.R;
import com.service.rwtpos.ReturnBillDetailActivity;

import java.util.ArrayList;

public class ReturnListAdapter extends RecyclerView.Adapter<ReturnListAdapter.ReturnListViewHolder> {

    private ArrayList<ReturnListModel> return_list;
    private Context context;

    public class ReturnListViewHolder extends RecyclerView.ViewHolder {
        public TextView bill_number_tv, date_tv, discount_tv, net_payable_tv, view_detail_tv;

        public ReturnListViewHolder(View itemView) {
            super(itemView);
            bill_number_tv = itemView.findViewById(R.id.bill_number_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            discount_tv = itemView.findViewById(R.id.discount_tv);
            net_payable_tv = itemView.findViewById(R.id.net_payable_tv);
            view_detail_tv = itemView.findViewById(R.id.view_detail_tv);
        }
    }

    public ReturnListAdapter(Context context, ArrayList<ReturnListModel> list) {
        return_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ReturnListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_list_layout, parent, false);
        ReturnListViewHolder evh = new ReturnListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReturnListViewHolder holder, int i) {
        final ReturnListModel currentItem = return_list.get(i);
        holder.bill_number_tv.setText(currentItem.getReturn_invoice());
        holder.date_tv.setText(currentItem.getReturn_date());
        holder.discount_tv.setText("₹ " + currentItem.getDiscount());
        holder.net_payable_tv.setText("₹ " + currentItem.getNet_payable());
        holder.view_detail_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ReturnBillDetailActivity.class);
                        intent.putExtra("retrurn_invoice_id", currentItem.getId());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return return_list.size();
    }

    public void filterList(ArrayList<ReturnListModel> filteredList) {
        return_list = filteredList;
        notifyDataSetChanged();
    }
}
