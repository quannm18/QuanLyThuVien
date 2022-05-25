package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.R;

import java.util.List;

public class SachSpinner extends BaseAdapter {
    private List<Sach> sachList;

    public SachSpinner(List<Sach> sachList) {
        this.sachList = sachList;
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Sach getItem(int position) {
        return sachList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SachSpinnerHolder viewHolder = null;
        Sach sach = getItem(position);
        if (convertView==null){
            viewHolder =new SachSpinnerHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_sach,parent,false);
            viewHolder.tvSpnSach = (TextView) convertView.findViewById(R.id.tvSpnSach);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (SachSpinnerHolder) convertView.getTag();
        }
        viewHolder.tvSpnSach.setText(sach.getTenSach());
        return convertView;
    }
    public class SachSpinnerHolder{
        private TextView tvSpnSach;
    }
    public int getPos(int maSach){
        for (int i = 0; i < sachList.size(); i++) {
            if (maSach==(sachList.get(i).getMaSach())){
                return i;
            }
        }
        return -1;
    }
}
