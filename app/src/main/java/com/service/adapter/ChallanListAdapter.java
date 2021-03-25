package com.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.model.DemandList;
import com.service.rwtpos.R;

import java.util.ArrayList;

public class ChallanListAdapter extends RecyclerView.Adapter<ChallanListAdapter.ChallanListViewHolder>{
    private ArrayList<DemandList> demand_list;
    private Context context;

    public class ChallanListViewHolder extends RecyclerView.ViewHolder {
        public TextView outlet_name_tv, owner_name_tv, on_date_tv, delivery_status_tv;

        public ChallanListViewHolder(View itemView) {
            super(itemView);
            outlet_name_tv = itemView.findViewById(R.id.outlet_name_tv);
            owner_name_tv = itemView.findViewById(R.id.owner_name_tv);
            on_date_tv = itemView.findViewById(R.id.on_date_tv);
            delivery_status_tv = itemView.findViewById(R.id.delivery_status_tv);
        }
    }

    public ChallanListAdapter(Context context, ArrayList<DemandList> list) {
        demand_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ChallanListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.demand_list_layout, parent, false);
        ChallanListViewHolder evh = new ChallanListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChallanListViewHolder holder, int i) {
        final DemandList currentItem = demand_list.get(i);
        holder.outlet_name_tv.setText(currentItem.getOutlet_name());
        holder.owner_name_tv.setText(currentItem.getOwner_name());
        holder.on_date_tv.setText(currentItem.getOn_date());
        if (currentItem.getIntStatus().equals("0")) {
            holder.delivery_status_tv.setText("Pending");
        } else if (currentItem.getIntStatus().equals("1")) {
            holder.delivery_status_tv.setText("Delivered");
        } else if (currentItem.getIntStatus().equals("2")) {
            holder.delivery_status_tv.setText("Cancelled");
        }
    }

    @Override
    public int getItemCount() {
        return demand_list.size();
    }
}
