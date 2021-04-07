package com.service.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.model.ProductDetailModel;
import com.service.rwtpos.R;
import java.util.ArrayList;

public class SaleReturnAdapter extends RecyclerView.Adapter<SaleReturnAdapter.SaleReturnViewHolder> {
    private ArrayList<ProductDetailModel> sale_return_list;
    private Context context;

    public class SaleReturnViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_tv, unit_tv, total_tv;

        public SaleReturnViewHolder(View itemView) {
            super(itemView);
            product_name_tv = itemView.findViewById(R.id.product_name_tv);
            unit_tv = itemView.findViewById(R.id.unit_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
        }
    }

    public SaleReturnAdapter(Context context, ArrayList<ProductDetailModel> list) {
        sale_return_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SaleReturnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_return_layout, parent, false);
        SaleReturnViewHolder evh = new SaleReturnViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SaleReturnViewHolder holder, int i) {
        final ProductDetailModel currentItem = sale_return_list.get(i);
//        holder.product_name_tv.setText(currentItem.getProduct_name());
//        holder.unit_tv.setText("Rate " + currentItem.getQuantity() + " X " + currentItem.getSale_price());
//        holder.total_tv.setText("₹ " + currentItem.getTotal());
    }

    @Override
    public int getItemCount() {
        return sale_return_list.size();
    }
}
