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

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThanhVienDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThanhVien;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienHolder> {
    private List<ThanhVien> thanhVienList;
    private MyHelper myHelper;
    private ThanhVienDAO thanhVienDAO;
    private AlertDialog alertDialog;


    private TextInputLayout tilUpdateName;
    private TextInputLayout tilUpdateSDT;
    private TextInputLayout tilUpdateCMND;
    private MaterialButton btnUpdateTV;

    public ThanhVienAdapter(List<ThanhVien> thanhVienList) {
        this.thanhVienList = thanhVienList;
    }

    @NonNull
    @Override
    public ThanhVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_thanhvien, parent, false);
        return new ThanhVienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienHolder holder, int position) {
        final ThanhVien thanhVien = thanhVienList.get(position);
        holder.tvRowMaTV.setText("Mã TV: " + thanhVien.getMaTV());
        holder.tvRowTenTV.setText("Tên TV: " + thanhVien.getTenTV());
        holder.tvRowSDT.setText("SDT: " + thanhVien.getSdt());
        holder.tvRowCMND.setText("CMND: " + thanhVien.getCmnd());

        myHelper = new MyHelper(holder.itemView.getContext());
        thanhVienDAO = new ThanhVienDAO(myHelper);

        holder.imgRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa");
                builder.setMessage("Bạn muốn xóa " + thanhVien.getMaTV() + " ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = thanhVienDAO.deleteTV(thanhVien);
                        if (check > 0) {
                            thanhVienList.remove(thanhVien);
                            Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            notifyItemRemoved(holder.getAdapterPosition());
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(v.getContext(), "Lỗi xóa!", Toast.LENGTH_SHORT).show();
                        }

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
                builder.setMessage("Bạn muốn cập nhật " + thanhVien.getMaTV() + " ?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder updateDialog = new AlertDialog.Builder(v.getContext());
                        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_update_thanhvien, null);
                        updateDialog.setView(view);

                        tilUpdateName = (TextInputLayout) view.findViewById(R.id.tilUpdateName);
                        tilUpdateSDT = (TextInputLayout) view.findViewById(R.id.tilUpdateSDT);
                        tilUpdateCMND = (TextInputLayout) view.findViewById(R.id.tilUpdateCMND);
                        btnUpdateTV = (MaterialButton) view.findViewById(R.id.btnUpdateTV);

                        tilUpdateName.getEditText().setText(thanhVien.getTenTV());
                        tilUpdateSDT.getEditText().setText(thanhVien.getSdt());
                        tilUpdateCMND.getEditText().setText(thanhVien.getCmnd());

                        btnUpdateTV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = tilUpdateName.getEditText().getText().toString();
                                String sdt = tilUpdateSDT.getEditText().getText().toString();
                                String cmnd = tilUpdateCMND.getEditText().getText().toString();
                                if (name.trim().isEmpty() && sdt.trim().isEmpty() && cmnd.trim().isEmpty()) {
                                    tilUpdateName.setError("Vui lòng nhập tên thay thế");
                                    tilUpdateSDT.setError("Vui lòng nhập số điện thoại thay thế");
                                    tilUpdateCMND.setError("Vui lòng nhập CMND/CCCD thay thế");
                                    return;
                                }
                                tilUpdateName.setErrorEnabled(false);
                                tilUpdateSDT.setErrorEnabled(false);
                                tilUpdateCMND.setErrorEnabled(false);

                                if (name.trim().isEmpty()) {
                                    tilUpdateName.setError("Vui lòng nhập tên thay thế");
                                    return;
                                }
                                tilUpdateName.setErrorEnabled(false);
                                if (sdt.trim().isEmpty()) {
                                    tilUpdateSDT.setError("Vui lòng nhập số điện thoại thay thế");
                                    return;
                                }
                                tilUpdateSDT.setErrorEnabled(false);
                                if (cmnd.trim().isEmpty()) {
                                    tilUpdateCMND.setError("Vui lòng nhập CMND/CCCD thay thế");
                                    return;
                                }
                                tilUpdateCMND.setErrorEnabled(false);



                                thanhVien.setCmnd(cmnd);
                                thanhVien.setSdt(sdt);
                                thanhVien.setTenTV(name);
                                int check = thanhVienDAO.updateTV(thanhVien);
                                if (check > 0) {
                                    Toast.makeText(v.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(v.getContext(), "Lỗi cập nhật!", Toast.LENGTH_SHORT).show();
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
        return thanhVienList.size();
    }

    public class ThanhVienHolder extends RecyclerView.ViewHolder {
        private TextView tvRowMaTV;
        private TextView tvRowTenTV;
        private TextView tvRowSDT;
        private TextView tvRowCMND;
        private ImageView imgRowDelete;

        public ThanhVienHolder(@NonNull View itemView) {
            super(itemView);

            tvRowMaTV = (TextView) itemView.findViewById(R.id.tvRowMaTV);
            tvRowTenTV = (TextView) itemView.findViewById(R.id.tvRowTenTV);
            tvRowSDT = (TextView) itemView.findViewById(R.id.tvRowSDT);
            tvRowCMND = (TextView) itemView.findViewById(R.id.tvRowCMND);
            imgRowDelete = (ImageView) itemView.findViewById(R.id.imgRowDelete);

        }
    }
}
