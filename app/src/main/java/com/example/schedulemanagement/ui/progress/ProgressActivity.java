package com.example.schedulemanagement.ui.progress;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        // TODO: sau sẽ render % động
    }
}