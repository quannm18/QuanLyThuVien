package com.example.asm_ngominhquan_ph14304_duanmau.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.DAO.ThongKeDAO;
import com.example.asm_ngominhquan_ph14304_duanmau.R;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DoanhThuFragment extends Fragment {
    private TextView tvDoanhThu;
    private MaterialButton btnEndDate;
    private MaterialButton btnStartDate;
    private MaterialButton btnCalculator;
    private MyHelper myHelper;
    private ThongKeDAO thongKeDAO;
    private PieChartView chart;

    private int daTra;
    private int chuaTra;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        btnEndDate = (MaterialButton) view.findViewById(R.id.btnEndDate);
        btnStartDate = (MaterialButton) view.findViewById(R.id.btnStartDate);
        btnCalculator = (MaterialButton) view.findViewById(R.id.btnCalculator);
        chart = (PieChartView) view.findViewById(R.id.chart);
        tvDoanhThu = (TextView) view.findViewById(R.id.tvDoanhThu);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,dayOfMonth);
                        btnStartDate.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,dayOfMonth);
                        btnEndDate.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = null;
                try {
                    date = (simpleDateFormat.parse(btnStartDate.getText().toString()));
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Vui lòng chọn ngày bắt đầu tính!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    date = (simpleDateFormat.parse(btnEndDate.getText().toString()));
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Vui lòng chọn ngày kết thúc tính!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Date date1 = simpleDateFormat.parse(btnStartDate.getText().toString());
                    Date date2 = simpleDateFormat.parse(btnEndDate.getText().toString());
                    long diff = date2.getTime() - date1.getTime();
                    Log.e("Test","" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)<0){
                        Toast.makeText(getContext(), "Vui lòng chọn ngày bắt đầu lớn hơn ngày kết thúc!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                myHelper = new MyHelper(getContext());
                thongKeDAO = new ThongKeDAO(myHelper);
                String tuNgay = btnStartDate.getText().toString();
                String denNgay = btnEndDate.getText().toString();



                tvDoanhThu.setText("Tổng: "+thongKeDAO.getDoanhThu(tuNgay,denNgay)+" VND");
                tvDoanhThu.setTextColor(Color.BLACK);

                daTra = thongKeDAO.getCT(tuNgay,denNgay);

                chuaTra = thongKeDAO.getDT(tuNgay,denNgay);

                List<SliceValue> pieData = new ArrayList<>();
                pieData.add(new SliceValue(daTra, Color.parseColor("#AAC8FA")));
                pieData.add(new SliceValue(chuaTra, Color.parseColor("#FFB5B5")));
                PieChartData pieChartData = new PieChartData(pieData);
                pieChartData.setHasLabels(true).setValueLabelTextSize(14);
                pieChartData.setHasCenterCircle(true).setCenterText1("DOANH THU").setCenterText1FontSize(24).setCenterText1Color(Color.parseColor("#B34242"));
                chart.setPieChartData(pieChartData);
                Toast.makeText(getContext(), "Tính thành công!", Toast.LENGTH_SHORT).show();
            }
        });


        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(50, Color.parseColor("#AAC8FA")));
        pieData.add(new SliceValue(50, Color.parseColor("#FFB5B5")));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("DOANH THU").setCenterText1FontSize(24).setCenterText1Color(Color.parseColor("#B34242"));
        chart.setPieChartData(pieChartData);
    }
}