package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThanhVien;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.R;

import java.util.List;

public class ThuThuSpinnerAdapter extends BaseAdapter {
    private List<ThuThu> thuThuList;

    public ThuThuSpinnerAdapter(List<ThuThu> thuThuList) {
        this.thuThuList = thuThuList;
    }

    @Override
    public int getCount() {
        return thuThuList.size();
    }

    @Override
    public ThuThu getItem(int position) {
        return thuThuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThuThuSpinnerHolder viewHolder = null;
        ThuThu thuThu = getItem(position);
        if (convertView==null){
            viewHolder =new ThuThuSpinnerHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_thuthu,parent,false);
            viewHolder.tvSpnThuThu = (TextView) convertView.findViewById(R.id.tvSpnThuThu);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ThuThuSpinnerHolder) convertView.getTag();
        }
        viewHolder.tvSpnThuThu.setText(thuThu.getMaTT());
        return convertView;
    }
    public class ThuThuSpinnerHolder {
        private TextView tvSpnThuThu;
    }
    public int getPos(String maTT){
        for (int i = 0; i < thuThuList.size(); i++) {
            if (maTT.equals(thuThuList.get(i).getMaTT())){
                return i;
            }
        }
        return -1;
    }
}
