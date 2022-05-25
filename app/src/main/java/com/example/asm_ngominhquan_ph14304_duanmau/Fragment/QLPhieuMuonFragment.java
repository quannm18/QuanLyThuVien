package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.PhieuMuonAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.SachSpinner;
import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.ThanhVienSpinnerAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.ThuThuSpinnerAdapter;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class QLPhieuMuonFragment extends Fragment {
    private RecyclerView rcvQLS;
    private FloatingActionButton fabQLPM;

    private List<PhieuMuon> phieuMuonList;
    private List<Sach> sachList;
    private List<ThuThu> thuThuList;
    private List<ThanhVien> thanhVienList;

    private PhieuMuonDAO phieuMuonDAO;
    private SachDAO sachDAO;
    private ThuThuDAO thuThuDAO;
    private ThanhVienDAO thanhVienDAO;

    private MyHelper myHelper;

    private SachSpinner sachSpinner;
    private ThuThuSpinnerAdapter thuThuSpinner;
    private ThanhVienSpinnerAdapter thanhVienSpinner;

    private PhieuMuonAdapter adapter;

    private AlertDialog alertDialog;
    private Spinner spnCreateTenTT;
    private Spinner spnCreateTenTV;
    private Spinner spnCreateTenSach;
    private MaterialButton btnCreateTV;
    private MaterialButton btnGetTime;
    private RadioButton rdoCT;
    private RadioButton rdoDT;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_l_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        rcvQLS = (RecyclerView) view.findViewById(R.id.rcvQLS);
        fabQLPM = (FloatingActionButton) view.findViewById(R.id.fabQLPM);

        phieuMuonList = new ArrayList<>();
        sachList = new ArrayList<>();
        thuThuList = new ArrayList<>();
        thanhVienList = new ArrayList<>();

        myHelper = new MyHelper(getContext());

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
        adapter = new PhieuMuonAdapter(phieuMuonList);
        rcvQLS.setAdapter(adapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQLS.setLayoutManager(linearLayoutManager);

        fabQLPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.create_phieumuon, null);


                spnCreateTenTT = (Spinner) view.findViewById(R.id.spnCreateTenTT);
                spnCreateTenTV = (Spinner) view.findViewById(R.id.spnCreateTenTV);
                spnCreateTenSach = (Spinner) view.findViewById(R.id.spnCreateTenSach);
                btnCreateTV = (MaterialButton) view.findViewById(R.id.btnCreateTV);
                btnGetTime = (MaterialButton) view.findViewById(R.id.btnGetTime);
                rdoCT = (RadioButton) view.findViewById(R.id.rdoCT);
                rdoDT = (RadioButton) view.findViewById(R.id.rdoDT);
                rdoCT.setChecked(true);
                spnCreateTenSach.setAdapter(sachSpinner);
                spnCreateTenTV.setAdapter(thanhVienSpinner);
                spnCreateTenTT.setAdapter(thuThuSpinner);

                builder.setView(view);

                btnGetTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, dayOfMonth);
                                btnGetTime.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                btnCreateTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sachList.size()==0||thuThuList.size()==0||thanhVienList.size()==0){
                            Toast.makeText(getContext(), "Vui lòng tạo các trường liên quan!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int idTV = thanhVienList.get(spnCreateTenTV.getSelectedItemPosition()).getMaTV();
                        int idSach = sachList.get(spnCreateTenSach.getSelectedItemPosition()).getMaSach();
                        int tienThue = sachList.get(spnCreateTenSach.getSelectedItemPosition()).getGiaThue();
                        String maTT = thuThuList.get(spnCreateTenTT.getSelectedItemPosition()).getMaTT();
                        String date = btnGetTime.getText().toString();
                        int checkInput = checkForm(maTT,idTV,idSach,date);
                        if (checkInput>0){
                            int traSach = -1;
                            if (rdoCT.isChecked()) {
                                traSach = 0;
                            } else {
                                traSach = 1;
                            }

                            PhieuMuon phieuMuon = new PhieuMuon();
                            phieuMuon.setMaTV(idTV);
                            phieuMuon.setMaSach(idSach);
                            phieuMuon.setMaTT(maTT);
                            phieuMuon.setTienThue((tienThue));
                            phieuMuon.setTraSach(traSach);
                            phieuMuon.setNgayMuon(date);

                            long check = phieuMuonDAO.insertPM(phieuMuon);
                            if (check > 0) {
                                phieuMuonList.clear();
                                phieuMuonList.addAll(phieuMuonDAO.getALlPM());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                            } else {
                                Toast.makeText(getContext(), "Lỗi thêm!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private int checkForm(String maTT, int idTV, int idSach, String date) {
        if (maTT.trim().isEmpty()&&idTV==0&&idSach==0){
            Toast.makeText(getContext(), "Vui lòng tạo các trường liên quan", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (maTT.trim().isEmpty()){
            Toast.makeText(getContext(), "Danh sách thủ thư rỗng", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (idTV==0){
            Toast.makeText(getContext(), "Danh sách thành viên rỗng", Toast.LENGTH_SHORT).show();
            return -1;
        }

        try {
            Date dateCheck = new Date();
            dateCheck = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Vui lòng chọn ngày mượn!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return -1;
        }

        return 1;
    }
}