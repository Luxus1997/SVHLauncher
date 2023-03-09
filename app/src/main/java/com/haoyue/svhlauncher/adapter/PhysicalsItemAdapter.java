package com.haoyue.svhlauncher.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.bean.PhysicalsBean;

import java.util.List;

import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.HIGH;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.LOW;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.NORMAL;

public class PhysicalsItemAdapter extends RecyclerView.Adapter<PhysicalsItemAdapter.MyViewHolder> {

    private List<PhysicalsBean.PhysicalsData> mlist;

    public PhysicalsItemAdapter(List<PhysicalsBean.PhysicalsData> mlist) {
        this.mlist = mlist;
    }

    public int getItemCount() {
        return this.mlist.size();
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int n) {
        PhysicalsBean.PhysicalsData physicals = mlist.get(n);

        myViewHolder.details_name.setText(physicals.getName());
        myViewHolder.details_value.setText(physicals.getValue() + "");
        myViewHolder.details_range.setText(physicals.getRange());
        if (LOW.equals(physicals.getType())) {
            myViewHolder.details_type.setText("[偏低]");
            myViewHolder.details_type.setTextColor(Color.parseColor("#E65A5A"));
        } else if (NORMAL.equals(physicals.getType())) {
            myViewHolder.details_type.setText("[正常]");
            myViewHolder.details_type.setTextColor(Color.parseColor("#1CA6EB"));
        } else if (HIGH.equals(physicals.getType())) {
            myViewHolder.details_type.setText("[偏高]");
            myViewHolder.details_type.setTextColor(Color.parseColor("#E65A5A"));
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new MyViewHolder(LayoutInflater.from(SVHApplication.getInstance()).inflate(R.layout.item_details, viewGroup, false));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView details_name;
        TextView details_value;
        TextView details_range;
        TextView details_type;

        public MyViewHolder(View view) {
            super(view);
            details_name = view.findViewById(R.id.details_name);
            details_value = view.findViewById(R.id.details_value);
            details_range = view.findViewById(R.id.details_range);
            details_type = view.findViewById(R.id.details_type);
        }
    }

}