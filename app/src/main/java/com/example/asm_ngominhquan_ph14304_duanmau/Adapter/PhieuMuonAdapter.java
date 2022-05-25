package com.example.asm_ngominhquan_ph14304_duanmau.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.PhieuMuonDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.SachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThanhVienDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThuThuDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.PhieuMuon;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThanhVien;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonHolder> {
    private List<PhieuMuon> phieuMuonList;
    private PhieuMuonDAO phieuMuonDAO;
    private MyHelper myHelper;
    private ThanhVienDAO thanhVienDAO;
    private ThuThuDAO thuThuDAO;
    private SachDAO sachDAO;

    private List<Sach> sachList;
    private List<ThuThu> thuThuList;
    private List<ThanhVien> thanhVienList;

    private SachSpinner sachSpinner;
    private ThuThuSpinnerAdapter thuThuSpinner;
    private ThanhVienSpinnerAdapter thanhVienSpinner;
    private AlertDialog alertDialog;


    private Spinner spnUpdateTenTT;
    private Spinner spnUpdateTenTV;
    private Spinner spnUpdateTenSach;
    private MaterialButton spnUpdatePM;
    private MaterialButton btnGetTime;
    private RadioButton rdoCT;
    private RadioButton rdoDT;
    private int check;

    private TextInputLayout tilShowMaPM;
    private TextInputLayout tilShowMaTT;
    private TextInputLayout tilShowMaTV;
    private TextInputLayout tilShowMaSach;
    private TextInputLayout tilShowNgayMuon;
    private TextInputLayout tilShowTrangThai;
    private MaterialButton btnShowXN;
    public PhieuMuonAdapter(List<PhieuMuon> phieuMuonList) {
        this.phieuMuonList = phieuMuonList;
    }

    @NonNull
    @Override
    public PhieuMuonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_phieumuon, parent, false);
        return new PhieuMuonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonHolder holder, int position) {
        final PhieuMuon phieuMuon = phieuMuonList.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        myHelper = new MyHelper(holder.itemView.getContext());
        thanhVienDAO = new ThanhVienDAO(myHelper);
        thuThuDAO = new ThuThuDAO(myHelper);
        sachDAO = new SachDAO(myHelper);

        holder.tvRowMaPhieu.setText("Mã PM: " + String.valueOf(phieuMuon.getMaPm()));
        holder.tvRowMaTT.setText("Tên Sách: " + sachDAO.getSachFromID(String.valueOf(phieuMuon.getMaSach())).getTenSach());
        holder.tvRowMaTV.setText("Tên TV: " + thanhVienDAO.getTVFromID((phieuMuon.getMaTV())).getTenTV());
        holder.tvRowTienThue.setText("Tiền thuê: " + String.valueOf(phieuMuon.getTienThue()) + " VNĐ");
        holder.tvRowNgayMuon.setText("Ngày mượn: " + String.valueOf(phieuMuon.getNgayMuon()));
        if (phieuMuon.getTraSach() == 0) {
            holder.tvRowTrangThai.setTextColor(Color.RED);
            holder.tvRowTrangThai.setText("Trạng thái: Chưa trả");
        }
        if (phieuMuon.getTraSach() == 1) {
            holder.tvRowTrangThai.setTextColor(Color.parseColor("#00B32C"));
            holder.tvRowTrangThai.setText("Trạng thái: Đã trả");
        }


        holder.imgRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa");
                builder.setMessage("Bạn muốn xóa " + phieuMuon.getMaTT() + " ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper = new MyHelper(v.getContext());
                        phieuMuonDAO = new PhieuMuonDAO(myHelper);

                        int check = phieuMuonDAO.deletePM(phieuMuon);
                        if (check > 0) {
                            phieuMuonList.remove(phieuMuon);
                            Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyDataSetChanged();
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
                builder.setMessage("Bạn muốn cập nhật PM " + phieuMuon.getMaPm() + " ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_update_phieumuon, null);
                        getData(v.getContext());

                        spnUpdateTenTT = (Spinner) view.findViewById(R.id.spnUpdateTenTT);
                        spnUpdateTenTV = (Spinner) view.findViewById(R.id.spnUpdateTenTV);
                        spnUpdateTenSach = (Spinner) view.findViewById(R.id.spnUpdateTenSach);
                        spnUpdatePM = (MaterialButton) view.findViewById(R.id.spnUpdatePM);
                        btnGetTime = (MaterialButton) view.findViewById(R.id.btnGetTime);
                        rdoCT = (RadioButton) view.findViewById(R.id.rdoCT);
                        rdoDT = (RadioButton) view.findViewById(R.id.rdoDT);

                        spnUpdateTenSach.setAdapter(sachSpinner);
                        spnUpdateTenTV.setAdapter(thanhVienSpinner);
                        spnUpdateTenTT.setAdapter(thuThuSpinner);

                        if (phieuMuon.getTraSach() == 0) {
                            rdoCT.setChecked(true);
                        } else {
                            rdoDT.setChecked(true);
                        }
                        int posTT = thuThuSpinner.getPos(phieuMuon.getMaTT());
                        spnUpdateTenTT.setSelection(posTT);

                        int posTV = thanhVienSpinner.getPos((phieuMuon.getMaTV()));
                        spnUpdateTenTV.setSelection(posTV);

                        int posLS = sachSpinner.getPos((phieuMuon.getMaSach()));
                        spnUpdateTenSach.setSelection(posLS);

                        btnGetTime.setText(phieuMuon.getNgayMuon());
                        builder.setView(view);


                        btnGetTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int month = calendar.get(Calendar.MONTH);
                                int year = calendar.get(Calendar.YEAR);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,dayOfMonth);
                                        btnGetTime.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
                                    }
                                }, year, month, day);
                                datePickerDialog.show();
                            }
                        });

                        spnUpdatePM.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int idTV = thanhVienList.get(spnUpdateTenTV.getSelectedItemPosition()).getMaTV();
                                int idSach = sachList.get(spnUpdateTenSach.getSelectedItemPosition()).getMaSach();
                                int tienThue = sachList.get(spnUpdateTenSach.getSelectedItemPosition()).getGiaThue();
                                String maTT = thuThuList.get(spnUpdateTenTT.getSelectedItemPosition()).getMaTT();
                                String date = btnGetTime.getText().toString();
                                if (maTT.trim().isEmpty()&&idTV==0&&idSach==0){
                                    Toast.makeText(v.getContext(), "Vui lòng tạo các trường liên quan", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (maTT.trim().isEmpty()){
                                    Toast.makeText(v.getContext(), "Danh sách thủ thư rỗng", Toast.LENGTH_SHORT).show();
                                    return ;
                                }
                                if (idTV==0){
                                    Toast.makeText(v.getContext(), "Danh sách thành viên rỗng", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                try {
                                    Date dateCheck = new Date();
                                    dateCheck = simpleDateFormat.parse(date);
                                } catch (ParseException e) {
                                    Toast.makeText(v.getContext(), "Vui lòng chọn ngày mượn!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                    return;
                                }

                                int traSach = -1;
                                if (rdoCT.isChecked()) {
                                    traSach = 0;
                                } else {
                                    traSach = 1;
                                }

                                phieuMuon.setMaTV(idTV);
                                phieuMuon.setMaSach(idSach);
                                phieuMuon.setMaTT(maTT);
                                phieuMuon.setTienThue((tienThue));
                                phieuMuon.setTraSach(traSach);
                                phieuMuon.setNgayMuon(date);

                                int check = phieuMuonDAO.updatePM(phieuMuon);
                                if (check > 0) {
                                    phieuMuonList.clear();
                                    phieuMuonList.addAll(phieuMuonDAO.getALlPM());
                                    notifyDataSetChanged();
                                    Toast.makeText(v.getContext(), "Update thành công!", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(v.getContext(), "Lỗi update!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.show_all_phieumuon,null);
                tilShowMaPM = (TextInputLayout) view.findViewById(R.id.tilShowMaPM);
                tilShowMaTT = (TextInputLayout) view.findViewById(R.id.tilShowMaTT);
                tilShowMaTV = (TextInputLayout) view.findViewById(R.id.tilShowMaTV);
                tilShowMaSach = (TextInputLayout) view.findViewById(R.id.tilShowMaSach);
                tilShowNgayMuon = (TextInputLayout) view.findViewById(R.id.tilShowNgayMuon);
                tilShowTrangThai = (TextInputLayout) view.findViewById(R.id.tilShowTrangThai);
                btnShowXN = (MaterialButton) view.findViewById(R.id.btnShowXN);

                tilShowMaPM.getEditText().setText(phieuMuon.getMaPm()+"");
                tilShowMaTT.getEditText().setText(thuThuDAO.getTTFromID(phieuMuon.getMaTT()).getHoTen());
                tilShowMaTV.getEditText().setText(thanhVienDAO.getTVFromID(phieuMuon.getMaTV()).getTenTV());
                tilShowMaSach.getEditText().setText(sachDAO.getSachFromID(phieuMuon.getMaSach()+"").getTenSach());
                tilShowNgayMuon.getEditText().setText(phieuMuon.getNgayMuon());
                String traSach ="";
                if (phieuMuon.getTraSach()==1){
                    traSach = "Đã trả sách";
                }
                else {
                    traSach = "Chưa trả sách";
                }
                tilShowTrangThai.getEditText().setText(traSach);

                tilShowMaPM.setEnabled(false);
                tilShowMaTT.setEnabled(false);
                tilShowMaTV.setEnabled(false);
                tilShowMaSach.setEnabled(false);
                tilShowNgayMuon.setEnabled(false);
                tilShowTrangThai.setEnabled(false);

                btnShowXN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    public void getData(Context context) {

        myHelper = new MyHelper(context);

        sachDAO = new SachDAO(myHelper);
        thuThuDAO = new ThuThuDAO(myHelper);
        thanhVienDAO = new ThanhVienDAO(myHelper);

        sachList = sachDAO.getAllSach();
        thuThuList = thuThuDAO.getAllThuThu();
        thanhVienList = thanhVienDAO.getAllTV();

        sachSpinner = new SachSpinner(sachList);
        thuThuSpinner = new ThuThuSpinnerAdapter(thuThuList);
        thanhVienSpinner = new ThanhVienSpinnerAdapter(thanhVienList);

        phieuMuonDAO = new PhieuMuonDAO(myHelper);
        phieuMuonList = phieuMuonDAO.getALlPM();
    }

    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public class PhieuMuonHolder extends RecyclerView.ViewHolder {

        private TextView tvRowMaPhieu;
        private TextView tvRowMaTV;
        private TextView tvRowTienThue;
        private TextView tvRowNgayMuon;
        private TextView tvRowTrangThai;
        private TextView tvRowMaTT;
        private ImageView imgRowDelete;

        public PhieuMuonHolder(@NonNull View itemView) {
            super(itemView);

            tvRowMaPhieu = (TextView) itemView.findViewById(R.id.tvRowMaPhieu);
            tvRowMaTT = (TextView) itemView.findViewById(R.id.tvRowMaTT);
            tvRowMaTV = (TextView) itemView.findViewById(R.id.tvRowMaTV);
            tvRowTienThue = (TextView) itemView.findViewById(R.id.tvRowTienThue);
            tvRowNgayMuon = (TextView) itemView.findViewById(R.id.tvRowNgayMuon);
            tvRowTrangThai = (TextView) itemView.findViewById(R.id.tvRowTrangThai);
            imgRowDelete = (ImageView) itemView.findViewById(R.id.imgRowDelete);

        }
    }
}
