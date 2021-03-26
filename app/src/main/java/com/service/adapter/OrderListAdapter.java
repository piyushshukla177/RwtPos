package com.service.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.OrderListModel;
import com.service.response_model.BillListModel;
import com.service.rwtpos.BillDetailsAcitvity;
import com.service.rwtpos.OrderListActivity;
import com.service.rwtpos.R;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<OrderListModel> category_list;
    private Context context;

    public class OrderListViewHolder extends RecyclerView.ViewHolder {
        public TextView bill_number_tv, date_tv, total_tv, payment_mode_tv, edit_bill_tv, view_detail_tv;

        public OrderListViewHolder(View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
            payment_mode_tv = itemView.findViewById(R.id.payment_mode_tv);
            bill_number_tv = itemView.findViewById(R.id.bill_number_tv);
            edit_bill_tv = itemView.findViewById(R.id.edit_bill_tv);
            view_detail_tv = itemView.findViewById(R.id.view_detail_tv);
        }
    }

    public OrderListAdapter(Context context, ArrayList<OrderListModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_layout, parent, false);
        OrderListViewHolder evh = new OrderListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListViewHolder holder, int i) {
        final OrderListModel currentItem = category_list.get(i);
        holder.bill_number_tv.setText(currentItem.getBill_no() + "");
        holder.date_tv.setText(currentItem.getBill_date() + "");
        holder.payment_mode_tv.setText(currentItem.getPayment_type() + "");
        holder.total_tv.setText(currentItem.getNet_payable() + "");

        if (context instanceof OrderListActivity) {
            holder.edit_bill_tv.setVisibility(View.GONE);
            holder.view_detail_tv.setVisibility(View.GONE);
        }
        holder.view_detail_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BillDetailsAcitvity.class);
                        intent.putExtra("productlist", currentItem.getProduct_list());
                        intent.putExtra("bill_no", currentItem.getBill_no());
                        intent.putExtra("bill_date", currentItem.getBill_date());
                        intent.putExtra("customer_name", currentItem.getCustomer_name());
                        intent.putExtra("customer_mobile", currentItem.getCustomer_mobile());
                        intent.putExtra("net_payable", currentItem.getNet_payable());
                        intent.putExtra("sgst", currentItem.getSgst());
                        intent.putExtra("cgst", currentItem.getCgst());
                        intent.putExtra("payment_type", currentItem.getPayment_type());
                        intent.putExtra("total", currentItem.getTotal());
                        intent.putExtra("round_off", currentItem.getRound_off());
                        intent.putExtra("total", currentItem.getTotal());
                        intent.putExtra("taxable", currentItem.getTaxable());
                        intent.putExtra("discount", currentItem.getTotal_discount());
                        intent.putExtra("id", currentItem.getId());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }
}
