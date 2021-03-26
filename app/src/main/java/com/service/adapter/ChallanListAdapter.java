package com.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.ChallanList;
import com.service.rwtpos.R;

import java.util.ArrayList;

public class ChallanListAdapter extends RecyclerView.Adapter<ChallanListAdapter.ChallanListViewHolder> {
    private ArrayList<ChallanList> challan_list;
    private Context context;

    public class ChallanListViewHolder extends RecyclerView.ViewHolder {
        public TextView challan_invoice_tv, challan_date_tv, Netpayable_tv, payment_mode_tv;

        public ChallanListViewHolder(View itemView) {
            super(itemView);
            challan_invoice_tv = itemView.findViewById(R.id.challan_invoice_tv);
            challan_date_tv = itemView.findViewById(R.id.challan_date_tv);
            Netpayable_tv = itemView.findViewById(R.id.Netpayable_tv);
            payment_mode_tv = itemView.findViewById(R.id.payment_mode_tv);
        }
    }

    public ChallanListAdapter(Context context, ArrayList<ChallanList> list) {
        challan_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ChallanListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.challan_list_layout, parent, false);
        ChallanListViewHolder evh = new ChallanListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChallanListViewHolder holder, int i) {
        final ChallanList currentItem = challan_list.get(i);
        holder.challan_invoice_tv.setText(currentItem.getChallan_invoice());
        holder.challan_date_tv.setText(currentItem.getChallan_date());
        holder.Netpayable_tv.setText(currentItem.getNet_payable());
        holder.payment_mode_tv.setText(currentItem.getMode_of_transport());
    }

    @Override
    public int getItemCount() {
        return challan_list.size();
    }
}
