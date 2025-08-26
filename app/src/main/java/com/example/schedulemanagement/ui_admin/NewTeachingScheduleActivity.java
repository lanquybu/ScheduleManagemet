package com.example.schedulemanagement.ui_admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.NewSchedule;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewTeachingScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NewScheduleAdapter adapter;
    private List<NewSchedule> newScheduleList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teaching_schedule);

        // Ánh xạ các view từ layout
        ImageView btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerViewNotifications);
        progressBar = findViewById(R.id.progressBar);

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newScheduleList = new ArrayList<>();
        adapter = new NewScheduleAdapter(newScheduleList);
        recyclerView.setAdapter(adapter);

        // Thiết lập Firestore
        db = FirebaseFirestore.getInstance();

        // Xử lý nút Back
        btnBack.setOnClickListener(v -> finish());

        // Tải dữ liệu từ Firestore
        fetchNewSchedules();
    }

    private void fetchNewSchedules() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("NewSchedules")
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        newScheduleList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NewSchedule newSchedule = document.toObject(NewSchedule.class);
                            newScheduleList.add(newSchedule);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi khi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}