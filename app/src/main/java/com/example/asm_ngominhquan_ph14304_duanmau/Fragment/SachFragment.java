package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.SachAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.SachSpinnerAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.LoaiSachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.SachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.LoaiSach;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SachFragment extends Fragment {
    private RecyclerView rcvQLS;
    private FloatingActionButton fabQLS;
    private SachAdapter sachAdapter;
    private MyHelper myHelper;
    private SachDAO sachDAO;
    private List<Sach> sachList;
    private List<LoaiSach> loaiSachList;
    private LoaiSachDAO loaiSachDAO;
    private AlertDialog alertDialog;


    private TextInputLayout tilCreateTenSach;
    private TextInputLayout tilCreateGiaThue;
    private TextInputLayout tilCreateTacGia;
    private MaterialButton btnCreateSach;
    private String tenLoai;
    private Spinner spnCreateMaLoai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        rcvQLS = (RecyclerView) view.findViewById(R.id.rcvQLS);
        fabQLS = (FloatingActionButton) view.findViewById(R.id.fabQLS);
        sachList = new ArrayList<>();
        loaiSachList = new ArrayList<>();


        myHelper = new MyHelper(getContext());
        sachDAO = new SachDAO(myHelper);
        loaiSachDAO = new LoaiSachDAO(myHelper);
        sachList = sachDAO.getAllSach();
        sachAdapter = new SachAdapter(sachList);

        rcvQLS.setAdapter(sachAdapter);
        rcvQLS.setLayoutManager(new LinearLayoutManager(getContext()));

        fabQLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.create_sach,null);


                loaiSachList = loaiSachDAO.getAllLoaiSach();
                Toast.makeText(getContext(), ""+loaiSachList.size(), Toast.LENGTH_SHORT).show();
                tilCreateTenSach = (TextInputLayout) view1.findViewById(R.id.tilCreateTenSach);
                tilCreateGiaThue = (TextInputLayout) view1.findViewById(R.id.tilCreateGiaThue);
                tilCreateTacGia = (TextInputLayout) view1.findViewById(R.id.tilCreateTacGia);
                btnCreateSach = (MaterialButton) view1.findViewById(R.id.btnCreateSach);
                spnCreateMaLoai = (Spinner) view1.findViewById(R.id.spnCreateMaLoai);

                SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(loaiSachList);
                spnCreateMaLoai.setAdapter(sachSpinnerAdapter);
                builder.setView(view1);

                btnCreateSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = spnCreateMaLoai.getSelectedItemPosition();
                        String tenLoai = loaiSachList.get(pos).getTenLoai();
                        Toast.makeText(getContext(), ""+tenLoai, Toast.LENGTH_SHORT).show();
                        int maLoai = loaiSachDAO.getLSFormName(tenLoai).getMaLoai();
                        String tacGia = tilCreateTacGia.getEditText().getText().toString();
                        String tenSach = tilCreateTenSach.getEditText().getText().toString();
                        String giaThue = tilCreateGiaThue.getEditText().getText().toString();
                        int checkInput = checkForm(loaiSachDAO.getAllLoaiSach(),tacGia,tenSach,giaThue);
                        if (checkInput>0){
                            Sach sach = new Sach();
                            sach.setTenSach(tenSach);
                            sach.setTacGia(tacGia);
                            sach.setGiaThue(Integer.parseInt(giaThue));
                            sach.setMaLoai(maLoai);

                            long check = sachDAO.insertSach(sach);
                            if (check>0){
                                Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                sachAdapter.notifyDataSetChanged();
                                sachList.clear();
                                sachList.addAll(sachDAO.getAllSach());
                                alertDialog.dismiss();
                                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                            }
                            else {
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

    private int checkForm(List<LoaiSach> loaiSaches, String tacGia, String tenSach, String giaThue) {
        if (loaiSaches.size()==0){
            Toast.makeText(getContext(), "Vui lòng tạo loại sách!", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (tacGia.trim().isEmpty()&&tenSach.trim().isEmpty()&&giaThue.trim().isEmpty()){
            tilCreateTacGia.setError("Vui lòng nhập tên tác giả");
            tilCreateGiaThue.setError("Vui lòng nhập giá thuê");
            tilCreateTenSach.setError("Vui lòng nhập tên sách");
            return -1;
        }
        if (tenSach.trim().isEmpty()){
            tilCreateTenSach.setError("Vui lòng nhập tên sách");
            return -1;
        }
        tilCreateTenSach.setErrorEnabled(false);
        if (giaThue.trim().isEmpty()){
            tilCreateGiaThue.setError("Vui lòng nhập giá thuê");
            return -1;
        }
        tilCreateGiaThue.setErrorEnabled(false);
        if (tacGia.trim().isEmpty()){
            tilCreateTacGia.setError("Vui lòng nhập tên tác giả");
            return -1;
        }
        tilCreateTacGia.setErrorEnabled(false);

        return 1;
    }

}