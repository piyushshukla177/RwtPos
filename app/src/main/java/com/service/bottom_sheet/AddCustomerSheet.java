package com.service.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.rwtpos.R;

public class AddCustomerSheet extends BottomSheetDialogFragment {
    private AddCustomerSheetListener mListener;

    EditText customer_name_et, customer_mobile_et, email_et, address_et;
    Button add_customer_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_customer_sheet_layout, container, false);

        customer_name_et = v.findViewById(R.id.customer_name_et);
        customer_mobile_et = v.findViewById(R.id.customer_mobile_et);
        email_et = v.findViewById(R.id.email_et);
        address_et = v.findViewById(R.id.address_et);
        add_customer_btn = v.findViewById(R.id.add_customer_btn);
        add_customer_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            mListener.onCustomerAdded(customer_name_et.getText().toString(), email_et.getText().toString(), customer_mobile_et.getText().toString(), address_et.getText().toString());
//                            dismiss();
                        }
                    }
                }
        );

        return v;
    }

    boolean check() {
        boolean b = true;
        if (customer_name_et.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Enter Customer Name", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (customer_mobile_et.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Enter Customer Mobile", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (email_et.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Enter Emial Id", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        }else if (address_et.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        }
        return b;
    }

    public interface AddCustomerSheetListener {
        void onCustomerAdded(String name, String email, String mobile, String address);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (AddCustomerSheetListener) context;
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