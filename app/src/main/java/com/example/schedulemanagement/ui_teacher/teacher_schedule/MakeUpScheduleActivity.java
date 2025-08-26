package com.example.schedulemanagement.ui_teacher.teacher_schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schedulemanagement.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MakeUpScheduleActivity extends AppCompatActivity {


    private EditText edtAbsentDay, edtMakeUpDay, edtSubject, edtRoom, edtReason, edtName;

    private Button btnRegister;
    private ImageView btnBack;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup_class);

        // Ánh xạ view
        edtAbsentDay = findViewById(R.id.edtAbsentDay);
        edtMakeUpDay = findViewById(R.id.edtMakeUpDay);
        edtSubject = findViewById(R.id.edtSubject);
        edtRoom = findViewById(R.id.edtRoom);
        edtReason = findViewById(R.id.edtReason);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

        edtName = findViewById(R.id.edtName);

        // Firestore instance
        db = FirebaseFirestore.getInstance();

        // Nút back
        btnBack.setOnClickListener(v -> finish());

        // Nút Đăng ký
        btnRegister.setOnClickListener(v -> {
            String absentStr = edtAbsentDay.getText().toString().trim();
            String makeUpStr = edtMakeUpDay.getText().toString().trim();
            String subject = edtSubject.getText().toString().trim();
            String room = edtRoom.getText().toString().trim();
            String reason = edtReason.getText().toString().trim();

            String name = edtName.getText().toString().trim();


            if (absentStr.isEmpty() || makeUpStr.isEmpty() || subject.isEmpty() || room.isEmpty() || reason.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert String -> Timestamp
            Timestamp absentDay = parseDateToTimestamp(absentStr);
            Timestamp makeUpDay = parseDateToTimestamp(makeUpStr);

            if (absentDay == null || makeUpDay == null) {
                Toast.makeText(this, "Ngày phải đúng định dạng dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Object lưu Firestore
            Map<String, Object> data = new HashMap<>();
            data.put("absentDay", absentDay);
            data.put("makeUpDay", makeUpDay);
            data.put("subject", subject);
            data.put("room", room);
            data.put("reason", reason);

            data.put("name", name);


            // Lưu vào collection "MakeUpClasses"
            db.collection("MakeUpClasses")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    // Chuyển String dd/MM/yyyy -> Timestamp
    private Timestamp parseDateToTimestamp(String dateStr) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateStr);
            if (date != null) {
                return new Timestamp(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}