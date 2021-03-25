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

public class EditDemandSheet extends BottomSheetDialogFragment {

    public String qty_txt;
    private EditDemandSheet.EditDemandSheetListener mListener;
    EditText qty_et;
    Button edit_item_btn;
    public int index = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_demand_sheet_layout, container, false);

        qty_et = v.findViewById(R.id.qty_et);

        edit_item_btn = v.findViewById(R.id.edit_item_btn);
        edit_item_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!qty_et.getText().toString().isEmpty()) {
                            mListener.onEditDemand(index, qty_et.getText().toString());
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Enter Quantity", Toast.LENGTH_SHORT).show();
                            qty_et.requestFocus();
                        }
                    }
                }
        );
        return v;
    }

    public interface EditDemandSheetListener {
        void onEditDemand(int index, String quantity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EditDemandSheet.EditDemandSheetListener) context;
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
