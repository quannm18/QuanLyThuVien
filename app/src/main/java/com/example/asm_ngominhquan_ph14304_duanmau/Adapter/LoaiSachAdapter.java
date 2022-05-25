package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.LoaiSachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.LoaiSach;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachHolder> {
    private List<LoaiSach> loaiSachList;
    private MyHelper myHelper;
    private LoaiSachDAO loaiSachDAO;
    private AlertDialog alertDialog;


    private TextInputLayout tilUpdateTS;
    private MaterialButton btnUpdateLS;
    public LoaiSachAdapter(List<LoaiSach> loaiSachList) {
        this.loaiSachList = loaiSachList;
    }

    @NonNull
    @Override
    public LoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_loaisach,parent,false);
        return new LoaiSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachHolder holder, int position) {
        final LoaiSach loaiSach = loaiSachList.get(position);
        holder.tvRowMaLoai.setText("Mã loại: "+(loaiSach.getMaLoai()));
        holder.tvRowTenLoai.setText("Tên loại: "+loaiSach.getTenLoai());

        holder.imgRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa ");
                builder.setMessage("Bạn muốn xóa "+loaiSach.getTenLoai()+" ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myHelper = new MyHelper(v.getContext());
                        loaiSachDAO = new LoaiSachDAO(myHelper);

                        int check = loaiSachDAO.deleteLS(loaiSach);
                        if (check>0){
                            loaiSachList.remove(loaiSach);
                            Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                        else {
                            Toast.makeText(v.getContext(), "Lỗi xóa!", Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Cập nhật");
                builder.setMessage("Bạn muốn cập nhật " + loaiSach.getTenLoai() + " ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();

                        AlertDialog.Builder updateDialog = new AlertDialog.Builder(v.getContext());
                        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_update_loaisach,null);
                        updateDialog.setView(view);


                        tilUpdateTS = (TextInputLayout) view.findViewById(R.id.tilUpdateTS);
                        btnUpdateLS = (MaterialButton) view.findViewById(R.id.btnUpdateLS);

                        btnUpdateLS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myHelper = new MyHelper(v.getContext());
                                loaiSachDAO = new LoaiSachDAO(myHelper);
                                String tenLoai = tilUpdateTS.getEditText().getText().toString();
                                if (tenLoai.trim().isEmpty()){
                                    tilUpdateTS.setError("Vui lòng nhập tên loại sách");
                                    return;
                                }

                                if (checkName(tenLoai)>0){
                                    tilUpdateTS.setError("Loại sách đã tồn tại");
                                    return;
                                }
                                tilUpdateTS.setErrorEnabled(false);
                                if (checkName(tenLoai)==0){
                                    loaiSach.setTenLoai(tenLoai);
                                    int check = loaiSachDAO.updateLS(loaiSach);
                                    if (check>0){
                                        loaiSachList.clear();
                                        loaiSachList.addAll(loaiSachDAO.getAllLoaiSach());
                                        Toast.makeText(v.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        alertDialog.dismiss();
                                    }
                                    else {
                                        Toast.makeText(v.getContext(), "Lỗi cập nhật!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        alertDialog = updateDialog.create();
                        alertDialog.show();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return loaiSachList.size();
    }

    protected class LoaiSachHolder extends RecyclerView.ViewHolder {
        private TextView tvRowMaLoai;
        private TextView tvRowTenLoai;
        private ImageView imgRowDelete;


        public LoaiSachHolder(@NonNull View itemView) {
            super(itemView);

            tvRowMaLoai = (TextView) itemView.findViewById(R.id.tvRowMaLoai);
            tvRowTenLoai = (TextView) itemView.findViewById(R.id.tvRowTenLoai);
            imgRowDelete = (ImageView) itemView.findViewById(R.id.imgRowDelete);
        }
    }
    int checkName(String name){
        for (int i = 0; i < loaiSachList.size(); i++) {
            if (name.equals(loaiSachList.get(i).getTenLoai())){
                return 1;
            }
        }
        return 0;
    }
}
