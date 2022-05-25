package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm_ngominhquan_ph14304_duanmau.MainActivity;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {
    private CardView cvHomeTop10;
    private CardView cvHomeDoanhThu;
    private CardView cvHomeGioiThieu;
    private FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        cvHomeTop10 = (CardView) view.findViewById(R.id.cvHomeTop10);
        cvHomeDoanhThu = (CardView) view.findViewById(R.id.cvHomeDoanhThu);
        cvHomeGioiThieu = (CardView) view.findViewById(R.id.cvHomeGioiThieu);


        cvHomeTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentFrame,new Top10Fragment()).commit();
                NavigationView nav = getActivity().findViewById(R.id.nav_view);
//                nav.getMenu().findItem(R.id.top10).setChecked(true);
            }
        });
        cvHomeDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentFrame,new DoanhThuFragment()).commit();
                NavigationView nav = getActivity().findViewById(R.id.nav_view);
//                nav.getMenu().findItem(R.id.doanhthu).setChecked(true);
            }
        });
        cvHomeGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentFrame,new LogOutFragment()).commit();
                NavigationView nav = getActivity().findViewById(R.id.nav_view);
            }
        });
    }
}