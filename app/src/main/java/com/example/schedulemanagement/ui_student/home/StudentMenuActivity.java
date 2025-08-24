package com.example.schedulemanagement.ui_student.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;
import com.example.schedulemanagement.ui_student.notification.NotificationsActivity;
import com.example.schedulemanagement.ui_student.progress.ProgressActivity;
import com.example.schedulemanagement.ui_student.schedule.ScheduleActivity;

public class StudentMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.rowSchedule).setOnClickListener( v ->
                startActivity(new Intent(this, ScheduleActivity.class)));

        findViewById(R.id.rowContent).setOnClickListener( v ->{
            // TODO: sau này mở nội dung bài giảng
                });

        findViewById(R.id.rowProgress).setOnClickListener( v ->
                startActivity(new Intent(this, ProgressActivity.class)));

        findViewById(R.id.rowNotifications).setOnClickListener( v ->
                startActivity(new Intent(this, NotificationsActivity.class)));

    }
}