package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.LoaiSachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.SachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.LoaiSach;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachHolder> {
    private List<Sach> sachList;
    private MyHelper myHelper;
    private SachDAO sachDAO;
    private LoaiSachDAO loaiSachDAO;
    private AlertDialog alertDialog;
    private List<LoaiSach> loaiSachList;
    private SachSpinnerAdapter spinnerAdapter;

    private TextInputLayout tilUpdateTenSach;
    private TextInputLayout tilUpdateGiaThue;
    private TextInputLayout tilUpdateTacGia;
    private MaterialButton btnUpdateSach;
    private Spinner spnCreateMaLoai;

    public SachAdapter(List<Sach> sachList) {
        this.sachList = sachList;
    }
    private Context context;
    @NonNull
    @Override
    public SachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sach,parent,false);
        this.context = parent.getContext();
        return new SachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachHolder holder, int position) {
        final Sach sach = sachList.get(position);
        myHelper = new MyHelper(holder.itemView.getContext());
        loaiSachDAO = new LoaiSachDAO(myHelper);
        holder.tvRowMaLoai.setText("M?? lo???i: "+(sach.getMaLoai())+" "+loaiSachDAO.getLSFormID(sach.getMaLoai()).getTenLoai());
        holder.tvRowMaSach.setText("M?? s??ch: "+(sach.getMaSach()));
        holder.tvRowTenSach.setText("T??n s??ch: "+(sach.getTenSach()));
        holder.tvRowGiaThue.setText("Gi?? thu??: "+(sach.getGiaThue())+" VN??");
        holder.tvRowTacGia.setText("T??c gi???: "+(sach.getTacGia()));

        holder.imgRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("X??a");
                builder.setMessage("B???n mu???n x??a "+sach.getMaSach()+" ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper = new MyHelper(v.getContext());
                        sachDAO = new SachDAO(myHelper);

                        int check = sachDAO.deleteSach(sach);
                        if (check>0){
                            Toast.makeText(v.getContext(), "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show();
                            sachList.remove(sach);
                            notifyDataSetChanged();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                        else {
                            Toast.makeText(v.getContext(), "L???i x??a!", Toast.LENGTH_SHORT).show();
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
                builder.setTitle("C???p nh???t");
                builder.setMessage("B???n mu???n c???p nh???t "+sach.getMaSach()+" ?");
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
                        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_update_sach,null);

                        tilUpdateTenSach = (TextInputLayout) view.findViewById(R.id.tilUpdateTenSach);
                        tilUpdateGiaThue = (TextInputLayout) view.findViewById(R.id.tilUpdateGiaThue);
                        tilUpdateTacGia = (TextInputLayout) view.findViewById(R.id.tilUpdateTacGia);
                        btnUpdateSach = (MaterialButton) view.findViewById(R.id.btnUpdateSach);
                        spnCreateMaLoai = (Spinner) view.findViewById(R.id.spnCreateMaLoai);

                        myHelper = new MyHelper(v.getContext());
                        loaiSachList = new ArrayList<>();
                        loaiSachDAO = new LoaiSachDAO(myHelper);
                        loaiSachList = loaiSachDAO.getAllLoaiSach();
                        spinnerAdapter = new SachSpinnerAdapter(loaiSachList);
                        spnCreateMaLoai.setAdapter(spinnerAdapter);

                        spnCreateMaLoai.setSelection(spinnerAdapter.getPos(sach.getMaLoai()));
                        tilUpdateTenSach.getEditText().setText(sach.getTenSach());
                        tilUpdateGiaThue.getEditText().setText(sach.getGiaThue()+"");
                        tilUpdateTacGia.getEditText().setText(sach.getTacGia());
                        updateDialog.setView(view);
                        btnUpdateSach.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posLS = spnCreateMaLoai.getSelectedItemPosition();
                                int maLoai = loaiSachList.get(posLS).getMaLoai();
                                String tenSach = tilUpdateTenSach.getEditText().getText().toString();
                                String giaThue = tilUpdateGiaThue.getEditText().getText().toString();
                                String tacGia = tilUpdateTacGia.getEditText().getText().toString();
                                myHelper = new MyHelper(v.getContext());
                                sachDAO = new SachDAO(myHelper);
                                if (maLoai==0){
                                    Toast.makeText(context, "Vui l??ng t???o lo???i s??ch!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (tacGia.trim().isEmpty()&&tenSach.trim().isEmpty()&&giaThue.trim().isEmpty()){
                                    tilUpdateTacGia.setError("Vui l??ng nh???p t??n t??c gi???");
                                    tilUpdateGiaThue.setError("Vui l??ng nh???p gi?? thu??");
                                    tilUpdateTenSach.setError("Vui l??ng nh???p t??n s??ch");
                                    return;
                                }
                                if (tenSach.trim().isEmpty()){
                                    tilUpdateTenSach.setError("Vui l??ng nh???p t??n s??ch");
                                    return;
                                }
                                tilUpdateTenSach.setErrorEnabled(false);
                                if (giaThue.trim().isEmpty()){
                                    tilUpdateGiaThue.setError("Vui l??ng nh???p gi?? thu??");
                                    return;
                                }
                                tilUpdateGiaThue.setErrorEnabled(false);
                                if (tacGia.trim().isEmpty()){
                                    tilUpdateTacGia.setError("Vui l??ng nh???p t??n t??c gi???");
                                    return;
                                }
                                tilUpdateTacGia.setErrorEnabled(false);
                                sach.setMaLoai(maLoai);
                                sach.setTenSach(tenSach);
                                sach.setGiaThue(Integer.parseInt(giaThue));
                                sach.setTacGia(tacGia);
                                int check = sachDAO.updateSach(sach);
                                if (check>0){
                                    Toast.makeText(v.getContext(), "C???p nh???t th??nh c??ng!", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    alertDialog.dismiss();
                                }
                                else {
                                    Toast.makeText(v.getContext(), "L???i c???p nh???t!", Toast.LENGTH_SHORT).show();
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
        return sachList.size();
    }

    public class SachHolder extends RecyclerView.ViewHolder {
        private TextView tvRowMaSach;
        private TextView tvRowMaLoai;
        private TextView tvRowTenSach;
        private TextView tvRowGiaThue;
        private TextView tvRowTacGia;
        private ImageView imgRowDelete;


        public SachHolder(@NonNull View itemView) {
            super(itemView);
            tvRowMaSach = (TextView) itemView.findViewById(R.id.tvRowMaSach);
            tvRowMaLoai = (TextView) itemView.findViewById(R.id.tvRowMaLoai);
            tvRowTenSach = (TextView) itemView.findViewById(R.id.tvRowTenSach);
            tvRowGiaThue = (TextView) itemView.findViewById(R.id.tvRowGiaThue);
            tvRowTacGia = (TextView) itemView.findViewById(R.id.tvRowTacGia);
            imgRowDelete = (ImageView) itemView.findViewById(R.id.imgRowDelete);

        }
    }
}
