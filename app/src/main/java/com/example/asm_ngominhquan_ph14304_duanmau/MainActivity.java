package com.example.asm_ngominhquan_ph14304_duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.AddUserFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.ChangePassFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.DoanhThuFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.HomeFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.LoaiSachFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.LogOutFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.QLPhieuMuonFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.SachFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.ThanhVienFragment;
import com.example.asm_ngominhquan_ph14304_duanmau.Fragment.Top10Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FrameLayout contentFrame;
    private NavigationView navView;
    private View mHeaderView;
    private TextView tvHeaderShow;

    boolean doubleBackToExitPressedOnce = false;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_QLPM = 1;
    private static final int FRAGMENT_QLLS = 2;
    private static final int FRAGMENT_QLSACH = 3;
    private static final int FRAGMENT_QLTV = 4;
    private static final int FRAGMENT_TOP10 = 5;
    private static final int FRAGMENT_DOANHTHU = 6;
    private static final int FRAGMENT_ADDUSER = 7;
    private static final int FRAGMENT_CHANGE_PASS = 8;
    private static final int FRAGMENT_LOGOUT = 10;
    private int currentFra = FRAGMENT_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        contentFrame = (FrameLayout) findViewById(R.id.contentFrame);
        navView = (NavigationView) findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.phuongnam);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,
                        drawerLayout,
                        toolbar,
                        R.string.nav_open,
                        R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        mHeaderView = navView.getHeaderView(0);
        tvHeaderShow = mHeaderView.findViewById(R.id.tvHeaderShow);
        Intent intent = getIntent();
        String showName = intent.getStringExtra("user");
        tvHeaderShow.setText("Welcome "+showName);

        if (showName.equals("Administration")){
            navView.getMenu().findItem(R.id.addUser).setVisible(true);
        }
        else {
            navView.getMenu().findItem(R.id.addUser).setVisible(false);

        }

        replaceFrag(new HomeFragment());
        navView.getMenu().findItem(R.id.home).setChecked(true);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home){
            if (currentFra!=FRAGMENT_HOME){
                replaceFrag(new HomeFragment());
                currentFra = FRAGMENT_HOME;
            }
        }
        if (id == R.id.qlpm){
            if (currentFra!=FRAGMENT_QLPM){
                replaceFrag(new QLPhieuMuonFragment());
                currentFra = FRAGMENT_QLPM;
            }
        }
        if (id == R.id.qlls){
            if (currentFra!=FRAGMENT_QLLS){
                replaceFrag(new LoaiSachFragment());
                currentFra = FRAGMENT_QLLS;
            }
        }
        if (id == R.id.qls){
            if (currentFra!=FRAGMENT_QLSACH){
                replaceFrag(new SachFragment());
                currentFra = FRAGMENT_QLSACH;
            }
        }
        if (id == R.id.qltv){
            if (currentFra!=FRAGMENT_QLTV){
                replaceFrag(new ThanhVienFragment());
                currentFra = FRAGMENT_QLTV;
            }
        }
        if (id == R.id.top10){
            if (currentFra!=FRAGMENT_TOP10){
                replaceFrag(new Top10Fragment());
                currentFra = FRAGMENT_TOP10;
            }
        }
        if (id == R.id.doanhthu){
            if (currentFra!=FRAGMENT_DOANHTHU){
                replaceFrag(new DoanhThuFragment());
                currentFra = FRAGMENT_DOANHTHU;
            }
        }
        if (id == R.id.addUser){
            if (currentFra!=FRAGMENT_ADDUSER){
                replaceFrag(new AddUserFragment());
                currentFra = FRAGMENT_ADDUSER;
            }
        }
        if (id == R.id.changePass){
            if (currentFra!=FRAGMENT_CHANGE_PASS){
                replaceFrag(new ChangePassFragment());
                currentFra = FRAGMENT_CHANGE_PASS;
            }
        }
        if (id == R.id.dangXuat){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
    public void replaceFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame,fragment);
        transaction.commit();
    }
}