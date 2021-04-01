package com.service.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.bottom_sheet.EditDemandSheet;
import com.service.bottom_sheet.EditItemSheet;
import com.service.model.ProductListModel;
import com.service.rwtpos.CreateBillActivity;
import com.service.rwtpos.CreateDemandActivity;
import com.service.rwtpos.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
    private ArrayList<ProductListModel> product_list;
    private Context context;

    public class ProductListViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_tv, unit_tv, total_tv;
        ImageView menu_imageview;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            unit_tv = itemView.findViewById(R.id.unit_tv);
            product_name_tv = itemView.findViewById(R.id.product_name_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
            menu_imageview = itemView.findViewById(R.id.menu_imageview);
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
        holder.product_name_tv.setText(currentItem.getPro_print_name());

        if (context instanceof CreateDemandActivity) {
//            holder.total_tv.setText("₹ " + Float.parseFloat(currentItem.getSale_price()) * Integer.parseInt(currentItem.getSelected_qty()));
            holder.unit_tv.setText("Quantity : " + currentItem.getSelected_qty());
        } else {
            holder.total_tv.setText("₹ " + Float.parseFloat(currentItem.getSale_price()) * Integer.parseInt(currentItem.getSelected_qty()));
            holder.unit_tv.setText("Rate " + currentItem.getSelected_qty() + " X " + currentItem.getSale_price());
        }
//        holder.total_tv.setText("₹ " + Float.parseFloat(currentItem.getSale_price()) * Integer.parseInt(currentItem.getSelected_qty()));
//        holder.unit_tv.setText("Rate " + currentItem.getSelected_qty() + " X " + currentItem.getSale_price());
        holder.menu_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.menu_imageview);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.item_edit_delete_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Delete")) {
                                    removeAt(i);
                                } else if (item.getTitle().equals("Edit")) {
                                    if (context instanceof CreateBillActivity) {
                                        EditItemSheet bottomSheetDialogFragment = new EditItemSheet();
                                        bottomSheetDialogFragment.product_name_txt = currentItem.getPro_print_name();
                                        bottomSheetDialogFragment.price_txt = currentItem.getSale_price();
                                        bottomSheetDialogFragment.qty_txt = currentItem.getSelected_qty();
                                        bottomSheetDialogFragment.discount_percentage_txt = currentItem.getDiscount_percentage();
                                        bottomSheetDialogFragment.batch_list = currentItem.getBatch_list();
                                        bottomSheetDialogFragment.index = i;
                                        bottomSheetDialogFragment.show(((CreateBillActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                                    } else if (context instanceof CreateDemandActivity) {
                                        EditDemandSheet bottomSheetDialogFragment = new EditDemandSheet();
                                        bottomSheetDialogFragment.qty_txt = currentItem.getSelected_qty();
                                        bottomSheetDialogFragment.index = i;
                                        bottomSheetDialogFragment.show(((CreateDemandActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                                    }
                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
                    }
                }
        );
//        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, batch_list);
//        holder.batch_spinner.setAdapter(stateAdapter);
    }

    public void removeAt(int position) {
        product_list.remove(position);
        notifyDataSetChanged();
        if (product_list.size() == 0) {
            if (context instanceof CreateBillActivity) {
                ((CreateBillActivity) context).hideAdditionalFields();
            } else if (context instanceof CreateDemandActivity) {
                ((CreateDemandActivity) context).hideAdditionalFields();
            }

        }
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }
}
