package com.example.schedulemanagement.ui_teacher.teacher_home;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.ui_teacher.teacher_schedule.TeacherScheduleActivity;
import com.example.schedulemanagement.ui_teacher.teachingclass.ClassListActivity;
import com.example.schedulemanagement.ui_teacher.totaltime.TotalHoursActivity;
import com.example.schedulemanagement.ui_teacher.verifytime.VerifyTeachingTimeActivity;

public class TeacherMenuActivity extends AppCompatActivity{
    LinearLayout rowClass, rowVerifyTime, rowScheduleTeacher, rowTeachingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        // Ánh xạ view
        rowClass = findViewById(R.id.rowClass);
        rowVerifyTime = findViewById(R.id.rowVerifyTime);
        rowScheduleTeacher = findViewById(R.id.rowScheduleTeacher);
        rowTeachingTime = findViewById(R.id.rowTeachingTime);
        // Sự kiện click "Lớp đang giảng dạy"
        rowClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMenuActivity.this, ClassListActivity.class);
                startActivity(intent);
            }
        });

        // Sự kiện click "Xác nhận giờ dạy"
        rowVerifyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMenuActivity.this, VerifyTeachingTimeActivity.class);
                startActivity(intent);
            }
        });

        // Sự kiện click "Quản lý lịch dạy"
        rowScheduleTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMenuActivity.this, TeacherScheduleActivity.class);
                startActivity(intent);
            }
        });

        rowTeachingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMenuActivity.this, TotalHoursActivity.class);
                startActivity(intent);
            }
        });
    }
}

