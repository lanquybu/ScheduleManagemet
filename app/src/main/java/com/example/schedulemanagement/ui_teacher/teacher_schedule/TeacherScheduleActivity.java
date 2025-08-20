package com.example.schedulemanagement.ui_teacher.teacher_schedule;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;

/** Quản lý lịch dạy của giảng viên (UI-only). */
public class TeacherScheduleActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_schedule);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
