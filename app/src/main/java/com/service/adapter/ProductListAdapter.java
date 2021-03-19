package com.service.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.ProductListModel;
import com.service.rwtpos.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
    private ArrayList<ProductListModel> product_list;
    private Context context;

    public class ProductListViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_tv;
        MaterialBetterSpinner batch_spinner;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            product_name_tv = itemView.findViewById(R.id.product_name_tv);
            batch_spinner = itemView.findViewById(R.id.batch_spinner);
        }
    }

    public ProductListAdapter(Context context, ArrayList<ProductListModel> list) {
        product_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        ProductListViewHolder evh = new ProductListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductListViewHolder holder, int i) {
        ArrayList batch_list = new ArrayList<>();
        final ProductListModel currentItem = product_list.get(i);
//        Log.e("strName",);
        String str_name = currentItem.getStr_product();
        holder.product_name_tv.setText(currentItem.getPro_print_name());

        for (int j = 0; j < currentItem.getBatch_list().size(); j++) {
            ProductListModel.Batch b = currentItem.getBatch_list().get(j);
            batch_list.add(String.valueOf(b.getPrice()));
        }
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, batch_list);
        holder.batch_spinner.setAdapter(stateAdapter);
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }
}
