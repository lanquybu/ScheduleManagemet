package com.example.schedulemanagement.ui_admin;

import android.os.Bundle;
import android.widget.ImageButton;
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
    private NewScheduleAdapter adapter;
    private List<NewSchedule> scheduleList;
    private FirebaseFirestore db;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teaching_schedule);

        // Ánh xạ các View từ layout
        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Thiết lập RecyclerView và Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleList = new ArrayList<>();
        adapter = new NewScheduleAdapter(scheduleList);
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện click cho nút Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Tải dữ liệu từ Firestore
        loadNewSchedules();
    }

    private void loadNewSchedules() {
        db.collection("NewSchedules")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                        scheduleList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Chuyển đổi Firestore document thành đối tượng NewSchedule
                            NewSchedule schedule = document.toObject(NewSchedule.class);
                            scheduleList.add(schedule);
                        }
                        // Cập nhật RecyclerView để hiển thị dữ liệu
                        adapter.notifyDataSetChanged();
                    } else {
                        // Hiển thị thông báo lỗi nếu không tải được dữ liệu
                        Toast.makeText(this, "Lỗi khi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}