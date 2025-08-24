package com.example.schedulemanagement.ui_student.progress;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ProgressItem;
import com.example.schedulemanagement.data.remote.FirebaseSource;
import com.example.schedulemanagement.data.remote.ResultCallback;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView tvPercentCenter, tvCompleted, tvPending;
    private final FirebaseSource firebaseSource = new FirebaseSource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        pieChart = findViewById(R.id.pieChart);
        tvPercentCenter = findViewById(R.id.tvPercentCenter);
        tvCompleted = findViewById(R.id.tvCompleted);
        tvPending = findViewById(R.id.tvPending);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        setupPieChart();
        loadProgress();
    }

    private void setupPieChart() {
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);

        // Donut style
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(70f);              // khoảng trắng giữa
        pieChart.setTransparentCircleRadius(0f);  // bỏ viền mờ ngoài

        // Tắt label & legend mặc định
        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setEnabled(false);
    }

    private void loadProgress() {
        // Ví dụ: 15 buổi học
        int totalSessions = 15;

        firebaseSource.getStudentProgress(totalSessions, new ResultCallback<ProgressItem>() {
            @Override
            public void onSuccess(ProgressItem data) {
                int completed = data.getCompleted();
                int total = data.getTotal();
                int pending = total - completed;
                int percent = data.getPercent();

                // Update UI
                tvPercentCenter.setText(percent + "%");
                tvCompleted.setText(String.valueOf(completed));
                tvPending.setText(String.valueOf(pending));

                // Dữ liệu chart
                List<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(completed, "Đã hoàn thành"));
                entries.add(new PieEntry(pending, "Chưa hoàn thành"));

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(
                        getResources().getColor(R.color.primary_blue),   // xanh: đã hoàn thành
                        getResources().getColor(R.color.blue)       // xám nhạt: chưa hoàn thành
                );
                dataSet.setDrawValues(false);

                PieData pieData = new PieData(dataSet);
                pieChart.setData(pieData);
                pieChart.invalidate(); // refresh chart
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ProgressActivity.this, "Lỗi: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
