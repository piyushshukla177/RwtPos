package com.service.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.ChallanList;
import com.service.rwtpos.BillDetailsAcitvity;
import com.service.rwtpos.ChallanDetailsActivity;
import com.service.rwtpos.R;

import java.util.ArrayList;

public class ChallanListAdapter extends RecyclerView.Adapter<ChallanListAdapter.ChallanListViewHolder> {
    private ArrayList<ChallanList> challan_list;
    private Context context;

    public class ChallanListViewHolder extends RecyclerView.ViewHolder {
        public TextView challan_invoice_tv, challan_date_tv, Netpayable_tv, payment_mode_tv, view_detail_tv;

        public ChallanListViewHolder(View itemView) {
            super(itemView);
            challan_invoice_tv = itemView.findViewById(R.id.challan_invoice_tv);
            challan_date_tv = itemView.findViewById(R.id.challan_date_tv);
            Netpayable_tv = itemView.findViewById(R.id.Netpayable_tv);
            payment_mode_tv = itemView.findViewById(R.id.payment_mode_tv);
            view_detail_tv = itemView.findViewById(R.id.view_detail_tv);
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
        holder.view_detail_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChallanDetailsActivity.class);
                        intent.putExtra("challan_no", currentItem.getChallan_invoice());
                        intent.putExtra("challan_date", currentItem.getChallan_date());
                        intent.putExtra("net_payable", currentItem.getNet_payable());
                        intent.putExtra("sgst", currentItem.getSgst());
                        intent.putExtra("cgst", currentItem.getCgst());
                        intent.putExtra("total", currentItem.getTotal());
                        intent.putExtra("round_off", currentItem.getRound_off());
                        intent.putExtra("taxable", currentItem.getTaxable_amt());
                        intent.putExtra("id", currentItem.getId());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return challan_list.size();
    }
}
