package com.service.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.rwtpos.R;

public class DiscardChangesSheet extends BottomSheetDialogFragment {

    private DiscardChangesListener mListener;
    Button continue_editing_btn, discard_changes_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.discard_chnages_sheet_layout, container, false);

        continue_editing_btn = v.findViewById(R.id.continue_editing_btn);
        discard_changes_btn = v.findViewById(R.id.discard_changes_btn);
//        expense_category_name_et = v.findViewById(R.id.expense_category_name_et);

        continue_editing_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        discard_changes_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onDiscardBill();
                    }
                }
        );
        return v;
    }

    public interface DiscardChangesListener {
        void onDiscardBill();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DiscardChangesListener) context;
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