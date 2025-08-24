package com.example.schedulemanagement.ui_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView
import com.example.schedulemanagement.R;

public class VisibilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visibility);

        // Xử lý nút Back đã có sẵn
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Tìm CardView "Danh sách lịch dạy mới" bằng ID của nó
        CardView cardDayMoi = findViewById(R.id.cardDayMoi);

        // Thiết lập OnClickListener cho CardView
        cardDayMoi.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity mới
            Intent intent = new Intent(VisibilityActivity.this, NewTeachingScheduleActivity.class);
            startActivity(intent);
        });
    }
}