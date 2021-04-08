package com.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.InventoryModel;
import com.service.rwtpos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.InventoryListViewHolder> {

    private ArrayList<InventoryModel> inventory_list;
    private Context context;

    public class InventoryListViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_textview, mrp_tv, stock_tv, stock_type_tv;
        public ImageView product_imageview;
        RelativeLayout stock_relative;

        public InventoryListViewHolder(View itemView) {
            super(itemView);
            product_imageview = itemView.findViewById(R.id.product_imageview);
            product_name_textview = itemView.findViewById(R.id.product_name_textview);
            mrp_tv = itemView.findViewById(R.id.mrp_tv);
            stock_tv = itemView.findViewById(R.id.stock_tv);
            stock_type_tv = itemView.findViewById(R.id.stock_type_tv);
            stock_relative = itemView.findViewById(R.id.stock_relative);
        }
    }

    public InventoryListAdapter(Context context, ArrayList<InventoryModel> list) {
        inventory_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public InventoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_list_layout, parent, false);
        InventoryListViewHolder evh = new InventoryListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final InventoryListViewHolder holder, int i) {
        final InventoryModel currentItem = inventory_list.get(i);
        Picasso.get().load(currentItem.getProduct_pic()).resize(400, 400).into(holder.product_imageview);
        holder.product_name_textview.setText(currentItem.getStr_product());
        holder.mrp_tv.setText(currentItem.getSale_price());
        holder.stock_tv.setText(currentItem.getAvailablestock());

        try {
            if (Integer.parseInt(currentItem.getAvailablestock()) == 0) {
                holder.stock_type_tv.setText("Out of Stock");
                holder.stock_relative.setBackground(ContextCompat.getDrawable(context, R.drawable.textview_shape_red));
            }
            else if (Integer.parseInt(currentItem.getAvailablestock()) < Integer.parseInt(currentItem.getMinmum_stock())) {
                holder.stock_type_tv.setText("Low Stock");
                holder.stock_relative.setBackground(ContextCompat.getDrawable(context, R.drawable.textview_shape_yellow));
            }

            else {
                holder.stock_type_tv.setText("In Stock");
                holder.stock_relative.setBackground(ContextCompat.getDrawable(context, R.drawable.textview_shape_green));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return inventory_list.size();
    }
}
