package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.Top;
import com.example.asm_ngominhquan_ph14304_duanmau.R;

import java.util.List;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Top10Holder> {
    private List<Top> topList;

    public Top10Adapter(List<Top> topList) {
        this.topList = topList;
    }

    @NonNull
    @Override
    public Top10Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_top,parent,false);
        return new Top10Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10Holder holder, int position) {
        final Top top = topList.get(position);
        holder.tvRowNumber.setText(String.valueOf(position+1));
        holder.tvRowTenSach.setText(top.getTenSach());
        holder.tvRowSL.setText("Số lượng: "+top.getSoLuong()+"");
    }

    @Override
    public int getItemCount() {
        return topList.size();
    }

    public class Top10Holder extends RecyclerView.ViewHolder {
        private TextView tvRowNumber;
        private TextView tvRowTenSach;
        private TextView tvRowSL;
        public Top10Holder(@NonNull View itemView) {
            super(itemView);

            tvRowNumber = (TextView) itemView.findViewById(R.id.tvRowNumber);
            tvRowTenSach = (TextView) itemView.findViewById(R.id.tvRowTenSach);
            tvRowSL = (TextView) itemView.findViewById(R.id.tvRowSL);
        }
    }
}
