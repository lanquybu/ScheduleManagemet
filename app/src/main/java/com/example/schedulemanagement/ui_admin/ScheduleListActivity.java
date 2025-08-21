package com.example.schedulemanagement.ui_admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ScheduleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        // Toolbar với nút back
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            // Khi bấm back, kết thúc màn hình này để quay về HomeAdminActivity
            finish();
        });
    }
}
