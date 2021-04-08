package com.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.service.model.ProductDetailModel;
import com.service.model.ReturnProductListModel;
import com.service.rwtpos.R;
import com.service.rwtpos.SalesReturnActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SaleReturnAdapter extends RecyclerView.Adapter<SaleReturnAdapter.SaleReturnViewHolder> {
    public ArrayList<ReturnProductListModel> sale_return_list;
    private Context context;

    public class SaleReturnViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_tv, quantity_tv, rate_tv, total_tv;
        CheckBox return_checkbox;
        ElegantNumberButton qty_input_et;

        public SaleReturnViewHolder(View itemView) {
            super(itemView);
            product_name_tv = itemView.findViewById(R.id.product_name_tv);
            quantity_tv = itemView.findViewById(R.id.quantity_tv);
            rate_tv = itemView.findViewById(R.id.rate_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
            return_checkbox = itemView.findViewById(R.id.return_checkbox);
            qty_input_et = itemView.findViewById(R.id.qty_input_et);
        }
    }

    public SaleReturnAdapter(Context context, ArrayList<ReturnProductListModel> list) {
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
        final ReturnProductListModel currentItem = sale_return_list.get(i);
        holder.product_name_tv.setText(currentItem.getProduct());
        holder.quantity_tv.setText("Sold Qty " + currentItem.getQuantity());
        holder.rate_tv.setText("Price " + currentItem.getBatch());
        holder.total_tv.setText("Total " + currentItem.getFinal_Price());
        holder.qty_input_et.setRange(1, Integer.parseInt(currentItem.getQuantity()));
        holder.return_checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.qty_input_et.setVisibility(View.VISIBLE);
                            currentItem.setSelected_return_qty(String.valueOf(1));
                            holder.qty_input_et.setNumber(String.valueOf(1));
                            calculate_Total();
                        } else {
                            holder.qty_input_et.setVisibility(View.GONE);
                            currentItem.setSelected_return_qty(String.valueOf(0));
                            calculate_Total();
                        }
                    }
                }
        );
        holder.qty_input_et.setOnValueChangeListener(
                new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        currentItem.setSelected_return_qty(String.valueOf(newValue));
                        calculate_Total();
                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return sale_return_list.size();
    }


    NumberFormat formatter = new DecimalFormat("##.00");

    public void calculate_Total() {
        try {
            float sum_discount_amount = 0;
            float sum_taxable = 0;
            float tax_total = 0;
            float net_payable = 0;
            int i = 0;
            while (i < sale_return_list.size()) {
                if (Integer.parseInt(sale_return_list.get(i).getSelected_return_qty()) > 0) {
                    float sale_Price = Float.parseFloat(sale_return_list.get(i).getSale_Price());
                    int tax_percentage = Integer.parseInt(sale_return_list.get(i).getSingleTaxRatePer());
                    float basic_price = ((sale_Price) / (100 + tax_percentage)) * 100;
                    float single_tax = (sale_Price - basic_price);
                    float total_basic = basic_price * Integer.parseInt(sale_return_list.get(i).getSelected_return_qty());
                    float discount_amount = ((total_basic * Integer.parseInt(sale_return_list.get(i).getSingleDiscountPer())) / 100);
                    float single_discount_amount = ((basic_price * Integer.parseInt(sale_return_list.get(i).getSingleDiscountPer())) / 100);

                    float total_taxable_amount = total_basic - discount_amount;
                    float total_tax_amount = (total_taxable_amount * Integer.parseInt(sale_return_list.get(i).getSingleTaxRatePer())) / 100;
                    float cgst = total_tax_amount / 2;
                    float sgst = total_tax_amount / 2;

                    sum_discount_amount = sum_taxable + discount_amount;
                    sum_taxable = sum_taxable +total_taxable_amount;
                    tax_total=tax_total+total_tax_amount;
//                    sum_discount_amount = sum_discount_amount + (Float.parseFloat(sale_return_list.get(i).getSingleDiscountAmt()) * (Integer.parseInt(sale_return_list.get(i).getSelected_return_qty())));
//                    sum_taxable = sum_taxable + ((Float.parseFloat(sale_return_list.get(i).getSingle_Basic_Price()) * Integer.parseInt(sale_return_list.get(i).getSelected_return_qty())));
//                    tax_total = tax_total + (Float.parseFloat(sale_return_list.get(i).getSingle_Tax_Amt()) * (Integer.parseInt(sale_return_list.get(i).getSelected_return_qty())));
                }
                i++;
            }
//            sum_taxable = sum_taxable - sum_discount_amount;
            net_payable = (sum_taxable) + tax_total;
            float round_off = Float.parseFloat(String.valueOf(Math.round(net_payable))) - Float.parseFloat(formatter.format(net_payable));
            ((SalesReturnActivity) context).setTotal(sum_discount_amount, tax_total, sum_taxable, round_off, net_payable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
