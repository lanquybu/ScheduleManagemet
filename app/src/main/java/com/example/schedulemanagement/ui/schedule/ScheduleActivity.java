package com.example.schedulemanagement.ui.schedule;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        // TODO: sẽ thay static UI bằng RecyclerView + data thật
    }
}