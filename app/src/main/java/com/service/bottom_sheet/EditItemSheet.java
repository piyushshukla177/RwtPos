package com.service.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.model.ProductListModel;
import com.service.rwtpos.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EditItemSheet extends BottomSheetDialogFragment {

    public ArrayList<ProductListModel.Batch> batch_list;
    public String product_name_txt;
    public String qty_txt;
    public String discount_percentage_txt;
    public String price_txt;
    public int index = 0;

    private EditItemSheetListener mListener;
    EditText sale_price_et, qty_et, discont_et;
    Spinner select_batch_spinner;
    Button edit_item_btn;
    HashMap<String, String> batch_map = new HashMap<>();
    TextView max_qty_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_item_sheet, container, false);
        sale_price_et = v.findViewById(R.id.sale_price_et);
        qty_et = v.findViewById(R.id.qty_et);
        discont_et = v.findViewById(R.id.discont_et);
        select_batch_spinner = v.findViewById(R.id.select_batch_spinner);
        max_qty_tv = v.findViewById(R.id.max_qty_tv);
        edit_item_btn = v.findViewById(R.id.edit_item_btn);
        edit_item_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            mListener.onItemEdited(index, sale_price_et.getText().toString(), qty_et.getText().toString(), discont_et.getText().toString(), select_batch_spinner.getSelectedItem().toString());
                            dismiss();
                        }
                    }
                }
        );
        ArrayList x = new ArrayList();
        x.add("-- Select Batch --");
        for (int j = 0; j < batch_list.size(); j++) {
            ProductListModel.Batch b = batch_list.get(j);
            x.add(String.valueOf(b.getPrice()));
            batch_map.put(b.getPrice(), b.getQty());
        }
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter(getActivity(),
                R.layout.spinner_layout, x);
        spinner_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        select_batch_spinner.setAdapter(spinner_adapter);

        sale_price_et.setText(price_txt);
        qty_et.setText(qty_txt);
        discont_et.setText(discount_percentage_txt);

        select_batch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.e("spinnerposition", position + "");
                if (position != 0) {
                    sale_price_et.setText(String.valueOf(x.get(position)));
                    max_qty_tv.setText(" ( Max Quantity " + batch_map.get(x.get(position)) + " )");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
//        int indexOfDecimal = price_txt.indexOf(".");
//        String stringForm= price_txt.substring(0, indexOfDecimal);
       int spinnerPosition = spinner_adapter.getPosition(price_txt);
       select_batch_spinner.setSelection(spinnerPosition);
        return v;
    }

    boolean check() {
        boolean b = true;
        if (sale_price_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(getActivity(), "Enter Sale Price", Toast.LENGTH_SHORT).show();
            return b;
        } else if (qty_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(getActivity(), "Enter Quantity", Toast.LENGTH_SHORT).show();
            return b;
        } else if (discont_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(getActivity(), "Enter Discount Percentage", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_batch_spinner.getSelectedItem().equals("-- Select Batch --")) {
            b = false;
            Toast.makeText(getActivity(), "Select Batch", Toast.LENGTH_SHORT).show();
            return b;
        } else if (!qty_et.getText().toString().isEmpty() && select_batch_spinner.getSelectedItemPosition() > 0) {

            int qty = Integer.parseInt(qty_et.getText().toString());
            int max_qty = Integer.parseInt(batch_map.get(select_batch_spinner.getSelectedItem().toString()));
            if (qty > max_qty) {
                b = false;
                Toast.makeText(getActivity(), "Enter Valid Quantity", Toast.LENGTH_SHORT).show();
                qty_et.requestFocus();
                return b;
            }
        } else if (!discont_et.getText().toString().isEmpty()) {
            float discount = Float.parseFloat(discont_et.getText().toString());
            if (discount > 100) {
                b = false;
                Toast.makeText(getActivity(), "Enter Valid Discount", Toast.LENGTH_SHORT).show();
                discont_et.requestFocus();
                return b;
            }
        }
        return b;
    }

    public interface EditItemSheetListener {
        void onItemEdited(int index, String salePrice, String qty, String discount, String selectedbatch);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EditItemSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

//  ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, batch_list);
//  select_batch_spinner.setAdapter(stateAdapter);
