package com.service.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.rwtpos.R;

public class PreviewInvoiceSheet extends BottomSheetDialogFragment {

    private PreviewInvoiceSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preview_invoicebottom_sheet, container, false);

        LinearLayout button1 = v.findViewById(R.id.share_btn);
        LinearLayout button2 = v.findViewById(R.id.preview_invoice_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onInvoicePreview("whatsapp");
                dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onInvoicePreview("open");
                dismiss();
            }
        });

        return v;
    }

    public interface PreviewInvoiceSheetListener {
        void onInvoicePreview(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (PreviewInvoiceSheetListener) context;
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