package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThuThuDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePassFragment extends Fragment {

    private TextInputLayout tilUserLogin;
    private TextInputLayout tilPassOld;
    private TextInputLayout tilPassNew;
    private TextInputLayout tilPassRetypeLogin;
    private MaterialButton btnLogin;
    private String user,oldPass,newPass,retype;
    private MyHelper myHelper;
    private ThuThuDAO thuThuDAO;
    private ThuThu thuThu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        tilUserLogin = (TextInputLayout) view.findViewById(R.id.tilUserLogin);
        tilPassOld = (TextInputLayout) view.findViewById(R.id.tilPassOld);
        tilPassNew = (TextInputLayout) view.findViewById(R.id.tilPassNew);
        tilPassRetypeLogin = (TextInputLayout) view.findViewById(R.id.tilPassRetypeLogin);
        btnLogin = (MaterialButton) view.findViewById(R.id.btnLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChangePass();
            }
        });
    }

    private void checkChangePass() {
        myHelper = new MyHelper(getContext());
        thuThuDAO = new ThuThuDAO(myHelper);

        user = tilUserLogin.getEditText().getText().toString();
        oldPass = tilPassOld.getEditText().getText().toString();
        newPass = tilPassNew.getEditText().getText().toString();
        retype = tilPassRetypeLogin.getEditText().getText().toString();


        if (user.trim().isEmpty()
                && oldPass.trim().isEmpty()
                && newPass.trim().isEmpty()
                && retype.trim().isEmpty() ){
            tilUserLogin.setError("Tr?????ng n??y kh??ng ???????c ????? tr???ng!");
            tilPassOld.setError("Tr?????ng n??y kh??ng ???????c ????? tr???ng!");
            tilPassNew.setError("Tr?????ng n??y kh??ng ???????c ????? tr???ng!");
            tilPassRetypeLogin.setError("Tr?????ng n??y kh??ng ???????c ????? tr???ng!");
            return;
        }
        if (user.trim().isEmpty()){
            tilUserLogin.setError("Vui l??ng nh???p t??i kho???n!");
            return;
        }
        tilUserLogin.setErrorEnabled(false);
        if (oldPass.trim().isEmpty()){
            tilPassOld.setError("Vui l??ng nh???p m???t kh???u g???n ????y nh???t!");
            return;
        }
        tilPassOld.setErrorEnabled(false);
        if (newPass.trim().isEmpty()){
            tilPassNew.setError("Vui l??ng nh???p m???t kh???u m???i!");
            return;
        }
        tilPassNew.setErrorEnabled(false);
        if (retype.trim().isEmpty()){
            tilPassRetypeLogin.setError("Vui l??ng nh???p l???i m???t kh???u!");
            return;
        }
        tilPassRetypeLogin.setErrorEnabled(false);
        if (!newPass.equals(retype)){
            tilPassRetypeLogin.setError("M???t kh???u m???i kh??ng kh???p!");
            return;
        }
        tilPassRetypeLogin.setErrorEnabled(false);
        String show = "T??i kho???n kh??ng t???n t???i!";
        for (int i = 0; i < thuThuDAO.getAllThuThu().size(); i++) {
            if (user.trim().equals(thuThuDAO.getAllThuThu().get(i).getMaTT())){
                show="???? t??m th???y t??i kho???n";
                thuThu = thuThuDAO.getTTFromID(user);

                if (!thuThu.getMaTT().equals(user)){
                    tilUserLogin.setError("T??i kho???n kh??ng ????ng!");
                    return;
                }
                tilUserLogin.setErrorEnabled(false);
                if ( !thuThu.getMatKhau().equals(oldPass)){
                    tilPassOld.setError("M???t kh???u kh??ng ????ng!");
                    return;
                }
                tilPassOld.setErrorEnabled(false);
                tilPassOld.setErrorEnabled(false);
                if (newPass.equals(retype)){
                    tilPassOld.setErrorEnabled(false);
                    tilUserLogin.setErrorEnabled(false);
                    tilPassRetypeLogin.setErrorEnabled(false);
                    tilPassNew.setErrorEnabled(false);
                }

                thuThu.setMatKhau(retype);
                int check = thuThuDAO.updateTT(thuThu);
                if (check>0){
                    Toast.makeText(getContext(), "?????i m???t kh???u th??nh c??ng!", Toast.LENGTH_SHORT).show();
                    tilUserLogin.getEditText().setText("");
                    tilPassNew.getEditText().setText("");
                    tilPassOld.getEditText().setText("");
                    tilPassRetypeLogin.getEditText().setText("");
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                else {
                    Toast.makeText(getContext(), "L???i ?????i m???t kh???u!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        Toast.makeText(getContext(), ""+show, Toast.LENGTH_SHORT).show();

    }

}