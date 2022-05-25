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

import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.ThanhVienAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThanhVienDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThanhVien;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienFragment extends Fragment {
    private RecyclerView rcvQLTV;
    private FloatingActionButton fabQLTV;
    private ThanhVienAdapter adapter;
    private MyHelper myHelper;
    private ThanhVienDAO thanhVienDAO;
    private List<ThanhVien> thanhVienList;
    private AlertDialog alertDialog;

    private TextInputLayout tilCreateName;
    private TextInputLayout tilCreateSDT;
    private TextInputLayout tilCreateCMND;
    private MaterialButton btnCreateTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        rcvQLTV = (RecyclerView) view.findViewById(R.id.rcvQLTV);
        fabQLTV = (FloatingActionButton) view.findViewById(R.id.fabQLTV);
        thanhVienList = new ArrayList<>();

        myHelper = new MyHelper(getContext());
        thanhVienDAO = new ThanhVienDAO(myHelper);
        thanhVienList = thanhVienDAO.getAllTV();
        adapter = new ThanhVienAdapter(thanhVienList);

        rcvQLTV.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQLTV.setLayoutManager(linearLayoutManager);

        fabQLTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.create_thanhvien,null);
                builder.setView(view);

                tilCreateName = (TextInputLayout) view.findViewById(R.id.tilCreateName);
                tilCreateSDT = (TextInputLayout) view.findViewById(R.id.tilCreateSDT);
                tilCreateCMND = (TextInputLayout) view.findViewById(R.id.tilCreateCMND);
                btnCreateTV = (MaterialButton) view.findViewById(R.id.btnCreateTV);

                btnCreateTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = tilCreateName.getEditText().getText().toString();
                        String sdt = tilCreateSDT.getEditText().getText().toString();
                        String cmnd = tilCreateCMND.getEditText().getText().toString();
                        int checkLog = checkLogin(name,sdt,cmnd);
                        if (checkLog>0){
                            ThanhVien thanhVien =new ThanhVien();
                            thanhVien.setTenTV(name);
                            thanhVien.setSdt(sdt);
                            thanhVien.setCmnd(cmnd);
                            long insert = thanhVienDAO.insertTV(thanhVien);
                            if (insert>0){
                                Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                                thanhVienList.clear();
                                thanhVienList.addAll(thanhVienDAO.getAllTV());
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
    void capNhapRCV(){
        thanhVienList = thanhVienDAO.getAllTV();
        adapter = new ThanhVienAdapter(thanhVienList);
        rcvQLTV.setAdapter(adapter);
    }
    public int checkLogin(String name, String sdt, String cmnd){
        if (name.trim().isEmpty()&&sdt.trim().isEmpty()&&cmnd.trim().isEmpty()){
            tilCreateSDT.setError("Vui lòng nhập số điện thoại");
            tilCreateCMND.setError("Vui lòng nhập số CMND/CCCD");
            tilCreateName.setError("Vui lòng nhập tên thành viên");
            return -1;
        }
        if (name.trim().isEmpty()){
            tilCreateName.setError("Vui lòng nhập tên thành viên");
            return -1;
        }
        tilCreateName.setErrorEnabled(false);
        if (sdt.trim().isEmpty()){
            tilCreateSDT.setError("Vui lòng nhập số điện thoại");
            return -1;
        }
        tilCreateSDT.setErrorEnabled(false);
        if (cmnd.trim().isEmpty()){
            tilCreateCMND.setError("Vui lòng nhập số CMND/CCCD");
            return -1;
        }
        tilCreateCMND.setErrorEnabled(false);
        return 1;
    }
}