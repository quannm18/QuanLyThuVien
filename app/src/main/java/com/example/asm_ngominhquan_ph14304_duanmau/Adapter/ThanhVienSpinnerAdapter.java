package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThanhVien;
import com.example.asm_ngominhquan_ph14304_duanmau.R;

import java.util.List;

public class ThanhVienSpinnerAdapter extends BaseAdapter {
    private List<ThanhVien> thanhVienList;

    public ThanhVienSpinnerAdapter(List<ThanhVien> thanhVienList) {
        this.thanhVienList = thanhVienList;
    }

    @Override
    public int getCount() {
        return thanhVienList.size();
    }

    @Override
    public ThanhVien getItem(int position) {
        return thanhVienList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThanhVienSpinnerHolder viewHolder = null;
        ThanhVien thanhVien = getItem(position);
        if (convertView==null){
            viewHolder =new ThanhVienSpinnerHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_thanhvien,parent,false);
            viewHolder.tvSpnThanhVien = (TextView) convertView.findViewById(R.id.tvSpnThanhVien);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ThanhVienSpinnerHolder) convertView.getTag();
        }
        viewHolder.tvSpnThanhVien.setText(thanhVien.getTenTV());
        return convertView;
    }
    public class ThanhVienSpinnerHolder {
        private TextView tvSpnThanhVien;
    }
    public int getPos(int maTV){
        for (int i = 0; i < thanhVienList.size(); i++) {
            if (maTV==(thanhVienList.get(i).getMaTV())){
                return i;
            }
        }
        return -1;
    }
}
