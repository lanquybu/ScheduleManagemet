package com.example.schedulemanagement.ui_student.notification;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        // TODO: sẽ thay static include bằng RecyclerView + data thật

    }
}