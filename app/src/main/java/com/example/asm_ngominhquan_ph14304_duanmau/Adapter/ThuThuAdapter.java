package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThuThuDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.util.List;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ThuThuHolder> {
    private List<ThuThu> thuThuList;
    private ThuThuDAO thuThuDAO;
    private MyHelper myHelper;
    private AlertDialog alertDialog;
    public ThuThuAdapter(List<ThuThu> thuThuList) {
        this.thuThuList = thuThuList;
    }

    @NonNull
    @Override
    public ThuThuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_thuthu,parent,false);
        return new ThuThuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuThuHolder holder, int position) {
        final ThuThu thuThu = thuThuList.get(position);
        holder.tvRowMaTT.setText("Mã TT: "+thuThu.getMaTT());
        holder.tvRowTenTT.setText("Tên TT: "+thuThu.getHoTen());
        holder.tvRowMK.setText("Mật khẩu: "+thuThu.getMatKhau());


        this.myHelper = new MyHelper(holder.itemView.getContext());
        this.thuThuDAO = new ThuThuDAO(myHelper);

        holder.imgRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa");
                builder.setMessage("Bạn muốn xóa "+thuThu.getMaTT()+" ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = thuThuDAO.deleteTT(thuThu);
                        if (check>0) {
                            Toast.makeText(holder.itemView.getContext(), "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            thuThuList.remove(thuThu);
                            notifyItemRangeChanged(holder.getAdapterPosition(), thuThuList.size());
                            notifyItemRemoved(holder.getAdapterPosition());
                            alertDialog.dismiss();
                        }
                        else {
                            Toast.makeText(holder.itemView.getContext(), "Lỗi xóa tài khoản!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return thuThuList.size();
    }

    public class ThuThuHolder extends RecyclerView.ViewHolder {
        private TextView tvRowMaTT;
        private TextView tvRowTenTT;
        private TextView tvRowMK;
        private ImageView imgRowDelete;
        public ThuThuHolder(@NonNull View itemView) {
            super(itemView);

            tvRowMaTT = (TextView) itemView.findViewById(R.id.tvRowMaTT);
            tvRowTenTT = (TextView) itemView.findViewById(R.id.tvRowTenTT);
            tvRowMK = (TextView) itemView.findViewById(R.id.tvRowMK);
            imgRowDelete = (ImageView) itemView.findViewById(R.id.imgRowDelete);

        }
    }
}
