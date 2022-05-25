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

import com.example.asm_ngominhquan_ph14304_duanmau.Adapter.Top10Adapter;
import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThongKeDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.Top;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Top10Fragment extends Fragment {
    private RecyclerView rvTop10;
    private FloatingActionButton fabTop10;
    private ThongKeDAO thongKeDAO;
    private MyHelper myHelper;
    private Top10Adapter top10Adapter;
    private List<Top> topList;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        rvTop10 = (RecyclerView) view.findViewById(R.id.rvTop10);
        fabTop10 = (FloatingActionButton) view.findViewById(R.id.fabTop10);
        topList = new ArrayList<>();
        myHelper = new MyHelper(getContext());
        thongKeDAO = new ThongKeDAO(myHelper);
        topList = thongKeDAO.getTop();
        top10Adapter = new Top10Adapter(topList);
        rvTop10.setAdapter(top10Adapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvTop10.setLayoutManager(linearLayoutManager);

        fabTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sortRCV = linearLayoutManager.getReverseLayout();
                if (sortRCV == false){
                    sortRCV = true;
                    linearLayoutManager.setReverseLayout(sortRCV);
                    linearLayoutManager.setStackFromEnd(true);
                    rvTop10.setLayoutManager(linearLayoutManager);
                }
                else {
                    sortRCV = false;
                    linearLayoutManager.setReverseLayout(sortRCV);
                    linearLayoutManager.setStackFromEnd(false);
                    rvTop10.setLayoutManager(linearLayoutManager);
                }
            }
        });
    }
}