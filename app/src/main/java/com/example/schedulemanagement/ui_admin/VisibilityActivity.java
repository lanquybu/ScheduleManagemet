package com.example.schedulemanagement.ui_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.schedulemanagement.R;

public class VisibilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visibility);

        // Nút back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Card danh sách lịch dạy bù
        CardView cardDayBu = findViewById(R.id.cardDayBu);
        cardDayBu.setOnClickListener(v -> {
            Intent intent = new Intent(VisibilityActivity.this, ScheduleListMakeUpActivity.class);
            startActivity(intent);
        });

        /* Card danh sách lịch dạy mới (sau này bạn mở Activity khác)
        CardView cardDayMoi = findViewById(R.id.cardDayMoi);
        cardDayMoi.setOnClickListener(v -> {
            Intent intent = new Intent(VisibilityActivity.this, ScheduleListActivity.class);
            startActivity(intent);
        });*/
    }
}
