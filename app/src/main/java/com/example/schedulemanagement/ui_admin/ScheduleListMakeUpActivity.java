package com.example.schedulemanagement.ui_admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.DayBuItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListMakeUpActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DayBuAdapter adapter;
    private List<DayBuItem> dayBuList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list_make_up);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerViewDayBu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DayBuAdapter(dayBuList, item -> {
            // xử lý khi click nút Xác nhận (nếu cần)
            Toast.makeText(this, "Xác nhận: " + item.getClassId(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadDayBuFromFirebase();
    }

    private void loadDayBuFromFirebase() {
        db.collection("MakeUpClasses")  // ⚠️ ĐÚNG collection của bạn
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    dayBuList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Log.d("FIREBASE_DOC", doc.getId() + " => " + doc.getData());

                        String classId = doc.getString("classId");
                        String subject = doc.getString("subject");
                        String name = doc.getString("name");
                        String room = doc.getString("room");
                        com.google.firebase.Timestamp absentDay = doc.getTimestamp("absentDay");
                        com.google.firebase.Timestamp makeUpDay = doc.getTimestamp("makeUpDay");

                        DayBuItem item = new DayBuItem(subject, name, room, absentDay, makeUpDay, classId);
                        dayBuList.add(item);
                    }
                    Log.d("FIREBASE_LIST", "Loaded " + dayBuList.size() + " items");
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("FIREBASE_ERROR", "Error: " + e.getMessage());
                    Toast.makeText(this, "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
