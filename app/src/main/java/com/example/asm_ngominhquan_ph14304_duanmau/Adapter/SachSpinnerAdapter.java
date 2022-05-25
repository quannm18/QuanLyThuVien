package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.LoaiSach;
import com.example.asm_ngominhquan_ph14304_duanmau.R;

import java.util.List;

public class SachSpinnerAdapter extends BaseAdapter {
    private List<LoaiSach> loaiSachList;

    public SachSpinnerAdapter(List<LoaiSach> loaiSachList) {
        this.loaiSachList = loaiSachList;
    }

    @Override
    public int getCount() {
        return loaiSachList.size();
    }

    @Override
    public LoaiSach getItem(int position) {
        return loaiSachList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SachSpinnerHolder viewHolder = null;
        LoaiSach loaiSach = getItem(position);
        if (convertView==null){
            viewHolder =new SachSpinnerHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_loaisach,parent,false);
            viewHolder.tvSpnLoaiSach = (TextView) convertView.findViewById(R.id.tvSpnLoaiSach);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (SachSpinnerHolder) convertView.getTag();
        }
        viewHolder.tvSpnLoaiSach.setText(loaiSach.getTenLoai());
        return convertView;
    }
    public class SachSpinnerHolder{
        private TextView tvSpnLoaiSach;
    }
    public int getPos(int maLS){
        for (int i = 0; i < loaiSachList.size(); i++) {
            if (maLS==(loaiSachList.get(i).getMaLoai())){
                return i;
            }
        }
        return -1;
    }
}
