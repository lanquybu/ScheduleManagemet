package com.example.schedulemanagement.ui_teacher.teacher_schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schedulemanagement.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText edtDate, edtPeriod, edtSubject, edtRoom;
    private Button btnSubmit;
    private ImageView btnBack;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_add_schedule);

        // Ánh xạ view
        edtDate = findViewById(R.id.edtDate);
        edtPeriod = findViewById(R.id.edtPeriod);
        edtSubject = findViewById(R.id.edtSubject);
        edtRoom = findViewById(R.id.edtRoom);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        // Firestore instance
        db = FirebaseFirestore.getInstance();

        // Xử lý nút back
        btnBack.setOnClickListener(v -> finish());

        // Xử lý nút Đăng ký
        btnSubmit.setOnClickListener(v -> {
            String dateStr = edtDate.getText().toString().trim();
            String period = edtPeriod.getText().toString().trim();
            String subject = edtSubject.getText().toString().trim();
            String room = edtRoom.getText().toString().trim();

            if (dateStr.isEmpty() || period.isEmpty() || subject.isEmpty() || room.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert String -> Timestamp
            Timestamp timestamp = null;
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateStr);
                if (date != null) {
                    timestamp = new Timestamp(date);
                }
            } catch (ParseException e) {
                Toast.makeText(this, "Ngày không đúng định dạng dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo object lưu vào Firestore
            Map<String, Object> schedule = new HashMap<>();
            schedule.put("date", timestamp);
            schedule.put("section", period);
            schedule.put("subject", subject);
            schedule.put("room", room);
            schedule.put("module", "001"); // bạn có thể thay bằng module thực tế

            // Lưu lên Firestore
            db.collection("TeachingSchedules")
                    .add(schedule)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // quay lại màn hình trước
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
