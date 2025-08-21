package com.example.schedulemanagement.ui_teacher.totaltime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ScheduleItem;
import com.example.schedulemanagement.ui_teacher.teacher_home.TeacherMenuActivity;
import com.example.schedulemanagement.ui_teacher.teachingclass.ClassListActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TotalHoursActivity extends AppCompatActivity {

    private TextView tvTotalHours;
    private RecyclerView rvConfirmed;
    private ConfirmedAdapter adapter;
    private List<ScheduleItem> confirmedList = new ArrayList<>();

    private FirebaseFirestore db;
    private ImageView btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_hours);

        tvTotalHours = findViewById(R.id.tvTotalHours);
        rvConfirmed = findViewById(R.id.rvConfirmed);

        db = FirebaseFirestore.getInstance();

        adapter = new ConfirmedAdapter(confirmedList);
        rvConfirmed.setLayoutManager(new LinearLayoutManager(this));
        rvConfirmed.setAdapter(adapter);

        loadConfirmedSchedules();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalHoursActivity.this, TeacherMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadConfirmedSchedules() {
        db.collection("confirmedSchedules")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    confirmedList.clear();
                    int totalHours = 0;

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String module = doc.getString("module");
                        String subject = doc.getString("subject");
                        String room = doc.getString("room");
                        String section = doc.getString("section");
                        String date = doc.getString("date");



                        // Tính số giờ từ section
                        int hours = calcHours(section);
                        totalHours += hours;

                        ScheduleItem item = new ScheduleItem(module, subject, date, room, section);
                        confirmedList.add(item);
                    }

                    tvTotalHours.setText("Tổng số giờ: " + totalHours);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show()
                );
    }

    private int calcHours(String section) {
        if (section == null || !section.contains("-")) return 0;
        try {
            String[] parts = section.split("-");
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            return (end - start + 1);
        } catch (Exception e) {
            return 0;
        }
    }
}
