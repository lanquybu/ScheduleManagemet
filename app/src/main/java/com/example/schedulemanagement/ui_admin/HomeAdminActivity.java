package com.example.schedulemanagement.ui_admin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;
import com.google.android.material.card.MaterialCardView;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        MaterialCardView cardTrackChanges = findViewById(R.id.cardChangeTracking);

        cardTrackChanges.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdminActivity.this, VisibilityActivity.class);
            startActivity(intent);
        });

        MaterialCardView cardScheduleList = findViewById(R.id.cardScheduleList);

        cardScheduleList.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdminActivity.this, ScheduleListActivity.class);
            startActivity(intent);
        });

    }
}
