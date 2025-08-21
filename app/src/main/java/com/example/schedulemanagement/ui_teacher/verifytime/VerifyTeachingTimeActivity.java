package com.example.schedulemanagement.ui_teacher.verifytime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Xác nhận giờ dạy (UI-only). */
public class VerifyTeachingTimeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<ScheduleItem> scheduleList = new ArrayList<>();
    private Button btnConfirm;
    private ScheduleItem selectedItem; // item được chọn
    private int selectedPosition = -1;
    private ImageView btnBack;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_time);

        recyclerView = findViewById(R.id.rvSchedules);
        btnConfirm = findViewById(R.id.btnConfirm);

        db = FirebaseFirestore.getInstance();

        adapter = new ScheduleAdapter(scheduleList, (item, position) -> {
            selectedItem = item;
            selectedPosition = position;
            Toast.makeText(this, "Đã chọn: " + item.getSubject(), Toast.LENGTH_SHORT).show();
        });
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyTeachingTimeActivity.this, TeacherMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadScheduleFromFirestore();

        btnConfirm.setOnClickListener(v -> {
            if (selectedItem != null) {
                confirmSchedule(selectedItem, selectedPosition);
            } else {
                Toast.makeText(this, "Vui lòng chọn một giờ dạy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadScheduleFromFirestore() {
        db.collection("schedule")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    scheduleList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String module = doc.getString("module");
                        String subject = doc.getString("subject");
                        String room = doc.getString("room");
                        String section = doc.getString("section");
                        String date = "";

                        if (doc.getDate("date") != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            date = sdf.format(doc.getDate("date"));
                        }

                        ScheduleItem item = new ScheduleItem(module, subject, date, room, section);
                        item.setId(doc.getId()); // cần thêm field id trong model
                        scheduleList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void confirmSchedule(ScheduleItem item, int position) {
        // 1. Xoá khỏi "schedule"
        db.collection("schedule").document(item.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // 2. Lưu sang "confirmedSchedules"
                    Map<String, Object> confirmedData = new HashMap<>();
                    confirmedData.put("room", item.getRoom());
                    confirmedData.put("subject", item.getSubject());
                    confirmedData.put("module", item.getModule());
                    confirmedData.put("date", item.getDate());   // Nếu là String thì giữ String, nếu Timestamp thì set Date
                    confirmedData.put("section", item.getSection());

                    db.collection("confirmedSchedules")
                            .add(confirmedData)
                            .addOnSuccessListener(docRef -> {
                                // 3. Xoá khỏi list + cập nhật UI
                                scheduleList.remove(position);
                                adapter.notifyItemRemoved(position);

                                Toast.makeText(this, "Đã xác nhận giờ dạy!", Toast.LENGTH_SHORT).show();

                                // Reset chọn
                                selectedItem = null;
                                selectedPosition = -1;
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Lỗi khi lưu confirmed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi xoá schedule: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
