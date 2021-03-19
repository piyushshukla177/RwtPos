package com.service.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.rwtpos.R;

public class AddProductByBarcodeSheet extends BottomSheetDialogFragment {
    private AddProductByBarcodeSheetListener mListener;
    ImageView close_imageview;
    Button add_barcode_btn;
    EditText barcode_et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_barcode_sheet, container, false);

        close_imageview = v.findViewById(R.id.close_imageview);
        Button add_barcode_btn;
        add_barcode_btn = v.findViewById(R.id.add_barcode_btn);
        barcode_et = v.findViewById(R.id.barcode_et);

        close_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        add_barcode_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!barcode_et.getText().toString().isEmpty()) {
                            mListener.onBarcodeAdded(barcode_et.getText().toString());
                            dismiss();
                        }else
                        {
                            Toast.makeText(getActivity(),"Enter Barcode",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        return v;
    }

    public interface AddProductByBarcodeSheetListener {
        void onBarcodeAdded(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (AddProductByBarcodeSheetListener) context;
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