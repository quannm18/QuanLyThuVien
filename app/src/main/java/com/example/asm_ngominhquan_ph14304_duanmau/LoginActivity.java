package com.example.asm_ngominhquan_ph14304_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThuThuDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUserLogin;
    private TextInputLayout tilPassLogin;
    private CheckBox chkLogin;
    private MaterialButton btnLogin;
    private MyHelper myHelper;
    private ThuThuDAO thuThuDAO;
    private SharedPreferences preferences;
    private ThuThu ttSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilUserLogin = (TextInputLayout) findViewById(R.id.tilUserLogin);
        tilPassLogin = (TextInputLayout) findViewById(R.id.tilPassLogin);
        chkLogin = (CheckBox) findViewById(R.id.chkLogin);
        btnLogin = (MaterialButton) findViewById(R.id.btnLogin);

        myHelper = new MyHelper(this);
        thuThuDAO = new ThuThuDAO(myHelper);
        ttSend = new ThuThu();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        tilUserLogin.getEditText().setText(preferences.getString("USERNAME",""));
        tilPassLogin.getEditText().setText(preferences.getString("PASSWORD",""));
        chkLogin.setChecked(preferences.getBoolean("REMEMBER",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();

            }
        });

    }
    void checkLogin(){
        String username = tilUserLogin.getEditText().getText().toString();
        String password = tilPassLogin.getEditText().getText().toString();
        if (username.trim().isEmpty() && password.trim().isEmpty()){
            tilUserLogin.setError("Tên đăng nhập không được để trống");
            tilPassLogin.setError("Mật khẩu không được để trống");
            return ;
        }
        tilPassLogin.setErrorEnabled(false);
        tilUserLogin.setErrorEnabled(false);
        if (username.trim().isEmpty()){
            tilUserLogin.setError("Tên đăng nhập không được để trống");
            return ;
        }
        tilUserLogin.setErrorEnabled(false);

        if (password.trim().isEmpty()){
            tilPassLogin.setError("Mật khẩu không được để trống");
            return ;
        }
        tilPassLogin.setErrorEnabled(false);
        tilUserLogin.setErrorEnabled(false);


        int check = thuThuDAO.checkLogin(username,password);
        if (check==0){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            if (chkLogin.isChecked()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("USERNAME", username);
                editor.putString("PASSWORD", password);
                editor.putBoolean("REMEMBER", true);
                editor.commit();
            } else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("USERNAME");
                editor.remove("PASSWORD");
                editor.remove("REMEMBER");
                editor.commit();
            }
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user",getUser(username).getHoTen());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
        }
    }
    public ThuThu getUser(String username){
        for (int i = 0; i < thuThuDAO.getAllThuThu().size(); i++) {
            if (username.trim().equals(thuThuDAO.getAllThuThu().get(i).getMaTT())){
                ttSend = thuThuDAO.getAllThuThu().get(i);
            }
        }
        return ttSend;
    }
}