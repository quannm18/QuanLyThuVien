package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.LoaiSachAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.LoaiSachDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.LoaiSach;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachFragment extends Fragment {
    private RecyclerView rvQLLS;
    private FloatingActionButton fabQLLS;
    private List<LoaiSach> loaiSachList;
    private MyHelper myHelper;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSachAdapter adapter;
    private AlertDialog alertDialog;

    private TextInputLayout tilCreateTS;
    private MaterialButton btnCreateLS;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        rvQLLS = (RecyclerView) view.findViewById(R.id.rvQLLS);
        fabQLLS = (FloatingActionButton) view.findViewById(R.id.fabQLLS);

        loaiSachList = new ArrayList<>();
        myHelper= new MyHelper(getContext());
        loaiSachDAO = new LoaiSachDAO(myHelper);
        loaiSachList = loaiSachDAO.getAllLoaiSach();
        adapter = new LoaiSachAdapter(loaiSachList);

        rvQLLS.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvQLLS.setLayoutManager(linearLayoutManager);

        fabQLLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.create_loaisach,null);
                builder.setView(view);

                tilCreateTS = (TextInputLayout) view.findViewById(R.id.tilCreateTS);
                btnCreateLS = (MaterialButton) view.findViewById(R.id.btnCreateLS);

                alertDialog = builder.create();
                btnCreateLS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = tilCreateTS.getEditText().getText().toString();
                        int checkInput = checkForm(name);
                        if (checkInput>0){
                            int checkN = checkName(name);
                            if (checkN==0){
                                tilCreateTS.setErrorEnabled(false);
                                LoaiSach loaiSach= new LoaiSach();
                                loaiSach.setTenLoai(name);
                                long check  = loaiSachDAO.insertLS(loaiSach);
                                if (check>0){
                                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                    loaiSachList.clear();
                                    loaiSachList.addAll(loaiSachDAO.getAllLoaiSach());
                                    adapter.notifyDataSetChanged();
                                    alertDialog.dismiss();
                                    getActivity().getWindow().getDecorView().setSystemUiVisibility(
                                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                                }
                                else {
                                    Toast.makeText(getContext(), "Lỗi thêm!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                tilCreateTS.setError("Loại sách đã tồn tại");
                            }
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    private int checkForm(String name) {
        if (name.trim().isEmpty()){
            tilCreateTS.setError("Vui lòng nhập tên loại sách");
            return -1;
        }
        tilCreateTS.setErrorEnabled(false);
        return 1;
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