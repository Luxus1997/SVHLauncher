package com.haoyue.svhlauncher.adapter;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.bean.PhysicalsBean;

import java.util.List;

import static com.haoyue.svhlauncher.bean.PhysicalsBean.PTYPE.Pressure;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PTYPE.Stature;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PTYPE.Temp;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.HIGH;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.LOW;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.NORMAL;

public class PhysicalsAdapter extends RecyclerView.Adapter<PhysicalsAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<PhysicalsBean> mlist;

    public PhysicalsAdapter(List<PhysicalsBean> mlist) {
        this.mlist = mlist;
    }

    public int getItemCount() {
        return this.mlist.size();
    }

    public void onBindViewHolder(MyViewHolder holder, int n) {

        PhysicalsBean p = mlist.get(n);
        //背景
        boolean isNormal = true;
        for (PhysicalsBean.PhysicalsData data : p.getPhysicalsData()) {
            if (!data.getType().equals(NORMAL)) {
                isNormal = false;
            }
        }
        if (isNormal) {
            holder.item_cons.setBackgroundResource(R.drawable.item_blue_r10);
        } else {
            holder.item_cons.setBackgroundResource(R.drawable.item_red_r10);
        }
        //标题
        holder.item_describe.setText(p.getDescribe());
        holder.item_name1.setText(p.getPhysicalsData().get(0).getName());
        if (LOW.equals(p.getPhysicalsData().get(0).getType())) {
            holder.item_type1.setText("[偏低]");
            holder.item_type1.setTextColor(Color.parseColor("#E65A5A"));
        } else if (NORMAL.equals(p.getPhysicalsData().get(0).getType())) {
            holder.item_type1.setText("[正常]");
            holder.item_type1.setTextColor(Color.parseColor("#1CA6EB"));
        } else if (HIGH.equals(p.getPhysicalsData().get(0).getType())) {
            holder.item_type1.setText("[偏高]");
            holder.item_type1.setTextColor(Color.parseColor("#E65A5A"));
        }

        if (Stature.equals(p.getProject_type())) {
            holder.item_name2.setText(p.getPhysicalsData().get(1).getName());
            holder.item_name3.setText(p.getPhysicalsData().get(2).getName());

            holder.item_value1.setText(p.getPhysicalsData().get(0).getValue() + "cm");
            holder.item_value2.setText(p.getPhysicalsData().get(1).getValue() + "kg");
            holder.item_value3.setText(p.getPhysicalsData().get(2).getValue() + "");

            if (LOW.equals(p.getPhysicalsData().get(1).getType())) {
                holder.item_type2.setText("[偏低]");
                holder.item_type2.setTextColor(Color.parseColor("#E65A5A"));
            } else if (NORMAL.equals(p.getPhysicalsData().get(1).getType())) {
                holder.item_type2.setText("[正常]");
                holder.item_type2.setTextColor(Color.parseColor("#1CA6EB"));
            } else if (HIGH.equals(p.getPhysicalsData().get(1).getType())) {
                holder.item_type2.setText("[偏高]");
                holder.item_type2.setTextColor(Color.parseColor("#E65A5A"));
            }
            if (LOW.equals(p.getPhysicalsData().get(2).getType())) {
                holder.item_type3.setText("[偏低]");
                holder.item_type3.setTextColor(Color.parseColor("#E65A5A"));
            } else if (NORMAL.equals(p.getPhysicalsData().get(2).getType())) {
                holder.item_type3.setText("[正常]");
                holder.item_type3.setTextColor(Color.parseColor("#1CA6EB"));
            } else if (HIGH.equals(p.getPhysicalsData().get(2).getType())) {
                holder.item_type3.setText("[偏高]");
                holder.item_type3.setTextColor(Color.parseColor("#E65A5A"));
            }
        } else if (Pressure.equals(p.getProject_type())) {
            holder.item_name2.setText(p.getPhysicalsData().get(1).getName());
            holder.item_name3.setText(p.getPhysicalsData().get(2).getName());

            holder.item_value1.setText(p.getPhysicalsData().get(0).getValue() + "mmHg");
            holder.item_value2.setText(p.getPhysicalsData().get(1).getValue() + "mmHg");
            holder.item_value3.setText(p.getPhysicalsData().get(2).getValue() + "次/分钟");

            if (LOW.equals(p.getPhysicalsData().get(1).getType())) {
                holder.item_type2.setText("[偏低]");
                holder.item_type2.setTextColor(Color.parseColor("#E65A5A"));
            } else if (NORMAL.equals(p.getPhysicalsData().get(1).getType())) {
                holder.item_type2.setText("[正常]");
                holder.item_type2.setTextColor(Color.parseColor("#1CA6EB"));
            } else if (HIGH.equals(p.getPhysicalsData().get(1).getType())) {
                holder.item_type2.setText("[偏高]");
                holder.item_type2.setTextColor(Color.parseColor("#E65A5A"));
            }
            if (LOW.equals(p.getPhysicalsData().get(2).getType())) {
                holder.item_type3.setText("[偏低]");
                holder.item_type3.setTextColor(Color.parseColor("#E65A5A"));
            } else if (NORMAL.equals(p.getPhysicalsData().get(2).getType())) {
                holder.item_type3.setText("[正常]");
                holder.item_type3.setTextColor(Color.parseColor("#1CA6EB"));
            } else if (HIGH.equals(p.getPhysicalsData().get(2).getType())) {
                holder.item_type3.setText("[偏高]");
                holder.item_type3.setTextColor(Color.parseColor("#E65A5A"));
            }
        } else if (Temp.equals(p.getProject_type())) {
            holder.item_value1.setText(p.getPhysicalsData().get(0).getValue() + "℃");
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new MyViewHolder(LayoutInflater.from(SVHApplication.getInstance()).inflate(R.layout.item_physicals, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout item_cons;
        TextView item_describe;

        TextView item_name1;
        TextView item_value1;
        TextView item_type1;

        TextView item_name2;
        TextView item_value2;
        TextView item_type2;

        TextView item_name3;
        TextView item_value3;
        TextView item_type3;

        public MyViewHolder(View view) {
            super(view);
            item_cons = view.findViewById(R.id.item_cons);
            item_describe = view.findViewById(R.id.item_describe);

            item_name1 = view.findViewById(R.id.item_name1);
            item_value1 = view.findViewById(R.id.item_value1);
            item_type1 = view.findViewById(R.id.item_type1);

            item_name2 = view.findViewById(R.id.item_name2);
            item_value2 = view.findViewById(R.id.item_value2);
            item_type2 = view.findViewById(R.id.item_type2);

            item_name3 = view.findViewById(R.id.item_name3);
            item_value3 = view.findViewById(R.id.item_value3);
            item_type3 = view.findViewById(R.id.item_type3);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}