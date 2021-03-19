package com.service.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.model.OrderListModel;
import com.service.rwtpos.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<OrderListModel> category_list;
    private Context context;

    public class OrderListViewHolder extends RecyclerView.ViewHolder {
        public TextView bill_number_tv, date_tv, total_tv, payment_mode_tv;

        public OrderListViewHolder(View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
            payment_mode_tv = itemView.findViewById(R.id.payment_mode_tv);
            bill_number_tv = itemView.findViewById(R.id.bill_number_tv);
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
        holder.bill_number_tv.setText(currentItem.getBill_no()+"");
        holder.date_tv.setText(currentItem.getBill_date()+"");
        holder.payment_mode_tv.setText(currentItem.getPayment_type()+"");
        holder.total_tv.setText(currentItem.getNet_payable()+"");

    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }
}
