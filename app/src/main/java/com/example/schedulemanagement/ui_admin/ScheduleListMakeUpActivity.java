package com.example.schedulemanagement.ui_admin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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

        adapter = new DayBuAdapter(dayBuList, item -> showConfirmDialog(item));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadDayBuFromFirebase();
    }

    private void loadDayBuFromFirebase() {
        db.collection("MakeUpClasses")
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

    private void showConfirmDialog(DayBuItem item) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_makeup);
        dialog.setCancelable(false);

        // Set kích thước 1/3 màn hình và nằm giữa
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(window.getAttributes());
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = getResources().getDisplayMetrics().heightPixels / 3;
            window.setAttributes(params);
            window.setGravity(Gravity.CENTER);
        }

        // Set thông tin lớp học
        TextView txtClassInfo = dialog.findViewById(R.id.txtClassInfo);
        txtClassInfo.setText(
                "Lớp: " + item.getClassId() + "\n" +
                        "Môn: " + item.getSubject() + "\n" +
                        "Giảng viên: " + item.getName() + "\n" +
                        "Ngày dạy bù: " + item.getMakeUpDay().toDate().toString()
        );

        // Nút Hủy
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Nút Xác nhận
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            db.collection("MakeUpClasses")
                    .document(item.getClassId()) // Giả sử documentId = classId
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Đã xóa lớp " + item.getClassId(), Toast.LENGTH_SHORT).show();
                        dayBuList.remove(item);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Xóa thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        dialog.show();
    }

}
