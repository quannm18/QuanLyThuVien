package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

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

import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.ThuThuAdapter;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThuThuDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddUserFragment extends Fragment {
    private TextInputLayout tilNewUsername;
    private TextInputLayout tilNewPassword;
    private TextInputLayout tilNewRetypePass;
    private TextInputLayout tilNewName;
    private MaterialButton btnCreate;
    private RecyclerView rcvAddNewUser;
    private ThuThuDAO thuThuDAO;
    private MyHelper myHelper;
    private ThuThuAdapter thuThuAdapter;
    private List<ThuThu> thuThuList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        tilNewUsername = (TextInputLayout) view.findViewById(R.id.tilNewUsername);
        tilNewPassword = (TextInputLayout) view.findViewById(R.id.tilNewPassword);
        tilNewRetypePass = (TextInputLayout) view.findViewById(R.id.tilNewRetypePass);
        btnCreate = (MaterialButton) view.findViewById(R.id.btnCreate);
        rcvAddNewUser = (RecyclerView) view.findViewById(R.id.rcvAddNewUser);
        tilNewName = (TextInputLayout) view.findViewById(R.id.tilNewName);

        myHelper = new MyHelper(getContext());
        thuThuDAO = new ThuThuDAO(myHelper);
        thuThuList = thuThuDAO.getAllThuThu();
        thuThuAdapter = new ThuThuAdapter(thuThuList);

        rcvAddNewUser.setAdapter(thuThuAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvAddNewUser.setLayoutManager(linearLayoutManager);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = checkSpace();
                if (check>0){
                    createNewUser();
                    return;
                }
            }
        });


    }

    private void createNewUser() {
        String username = tilNewUsername.getEditText().getText().toString();
        String newName = tilNewName.getEditText().getText().toString();
        String password = tilNewPassword.getEditText().getText().toString();
        String retype = tilNewRetypePass.getEditText().getText().toString();
        long check = thuThuDAO.insertTT(new ThuThu(username,newName,password));
        if (check>0){
            Toast.makeText(getContext(), "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
            thuThuList.add(new ThuThu(username,newName,password));
            thuThuAdapter.notifyDataSetChanged();

            tilNewName.getEditText().setText("");
            tilNewUsername.getEditText().setText("");
            tilNewPassword.getEditText().setText("");
            tilNewRetypePass.getEditText().setText("");
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            return;
        }
        else {
            Toast.makeText(getContext(), "Lỗi tạo tài khoản!", Toast.LENGTH_SHORT).show();

        }
    }

    public int checkSpace(){
        String username = tilNewUsername.getEditText().getText().toString();
        String newName = tilNewName.getEditText().getText().toString();
        String password = tilNewPassword.getEditText().getText().toString();
        String retype = tilNewRetypePass.getEditText().getText().toString();


        if (newName.trim().isEmpty() &&username.trim().isEmpty() && password.trim().isEmpty() && retype.trim().isEmpty()){
            tilNewUsername.setError("Vui lòng nhập tên tài khoản muốn tạo!");
            tilNewName.setError("Vui lòng nhập tên người dùng!");
            tilNewPassword.setError("Vui lòng nhập mật khẩu!");
            tilNewRetypePass.setError("Vui lòng nhập lại mật khẩu!");
            return 0;
        }
        if (newName.trim().isEmpty()){
            tilNewName.setError("Vui lòng nhập tên người dùng!");
            return 0;
        }
        tilNewName.setErrorEnabled(false);
        tilNewUsername.setErrorEnabled(false);
        if (username.trim().isEmpty()){
            tilNewUsername.setError("Vui lòng nhập tên tài khoản muốn tạo!");
            return 0;
        }
        tilNewUsername.setErrorEnabled(false);
        if (password.trim().isEmpty()){
            tilNewPassword.setError("Vui lòng nhập mật khẩu!");
            return 0;
        }
        tilNewPassword.setErrorEnabled(false);
        if (retype.trim().isEmpty()){
            tilNewRetypePass.setError("Vui lòng nhập lại mật khẩu!");
            return 0;
        }
        if (!retype.trim().equals(password)){
            tilNewRetypePass.setError("Mật khẩu không trùng khớp!");
            return 0;
        }
        tilNewRetypePass.setErrorEnabled(false);
        for (int i = 0; i < thuThuDAO.getAllThuThu().size(); i++) {
            if (username.trim().equals(thuThuDAO.getAllThuThu().get(i).getMaTT())){
                Toast.makeText(getContext(), "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
                tilNewUsername.setError("Tài khoản này đã tồn tại!");
                return 0;
            }
        }
       return 1;
    }
}