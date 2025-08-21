package com.example.schedulemanagement.ui.schedule;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ScheduleItem;
import com.example.schedulemanagement.data.repository.StudentRepository;
import com.example.schedulemanagement.data.remote.ResultCallback;

import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSchedule;
    private ProgressBar progressBar;
    private final StudentRepository studentRepo = new StudentRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule);
        progressBar = findViewById(R.id.progressBar);

        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(this));

        loadSchedule();
    }

    private void loadSchedule() {
        progressBar.setVisibility(View.VISIBLE);
        studentRepo.getSchedule(new ResultCallback<List<ScheduleItem>>() {
            @Override
            public void onSuccess(List<ScheduleItem> result) {
                progressBar.setVisibility(View.GONE);
                recyclerViewSchedule.setAdapter(new ScheduleAdapter(result));
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ScheduleActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
