package com.example.schedulemanagement.ui_teacher.verifytime;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;

/** Xác nhận giờ dạy (UI-only). */
public class VerifyTeachingTimeActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_time);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
