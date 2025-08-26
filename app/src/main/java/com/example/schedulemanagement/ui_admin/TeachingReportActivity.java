package com.example.schedulemanagement.ui_admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.schedulemanagement.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeachingReportActivity extends AppCompatActivity {

    private TableLayout tableClasses, tableTeachers;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_report);

        // Ánh xạ
        tableClasses = findViewById(R.id.tableClasses);
        tableTeachers = findViewById(R.id.tableTeachers);
        db = FirebaseFirestore.getInstance();

        //Back
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Load dữ liệu
        setupClassHeader();
        setupTeacherHeader();
        loadReportClasses();
        loadTeachers();
    }

    /** ------------------ LOAD BÁO CÁO LỚP ------------------ **/
    private void loadReportClasses() {
        db.collection("ReportClasses").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String classID = doc.getString("classID");
                        String subject = doc.getString("subject");
                        long totalStudents = getLongValue(doc, "totalStudents");
                        long currentStudents = getLongValue(doc, "currentStudents");
                        String status = doc.getString("status");
                        long progress = getLongValue(doc, "progress");

                        TableRow row = new TableRow(this);
                        row.addView(createTextView(classID));
                        row.addView(createTextView(subject));
                        row.addView(createTextView(currentStudents + "/" + totalStudents));
                        row.addView(createTextView(status));
                        row.addView(createTextView(progress + "%"));

                        tableClasses.addView(row);
                    }
                });
    }

    /** ------------------ LOAD BÁO CÁO GIẢNG VIÊN ------------------ **/
    private void loadTeachers() {
        db.collection("Teachers").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String teacherID = doc.getString("teacherID");
                        String teacherName = doc.getString("teacherName");
                        String department = doc.getString("department");
                        long numClasses = getLongValue(doc, "numClasses");
                        long numHours = getLongValue(doc, "numHours");

                        TableRow row = new TableRow(this);
                        row.addView(createTextView(teacherID));
                        row.addView(createTextView(teacherName));
                        row.addView(createTextView(department));
                        row.addView(createTextView(String.valueOf(numClasses)));
                        row.addView(createTextView(String.valueOf(numHours)));

                        tableTeachers.addView(row);
                    }
                });
    }

    /** ------------------ HÀM TẠO HEADER ------------------ **/
    private void setupClassHeader() {
        TableRow header = new TableRow(this);
        header.addView(createHeaderTextView("Mã lớp"));
        header.addView(createHeaderTextView("Môn học"));
        header.addView(createHeaderTextView("Sĩ số"));
        header.addView(createHeaderTextView("Tình trạng"));
        header.addView(createHeaderTextView("Tiến độ"));
        tableClasses.addView(header);
    }

    private void setupTeacherHeader() {
        TableRow header = new TableRow(this);
        header.addView(createHeaderTextView("Mã GV"));
        header.addView(createHeaderTextView("Tên GV"));
        header.addView(createHeaderTextView("Khoa"));
        header.addView(createHeaderTextView("Số lớp"));
        header.addView(createHeaderTextView("Số giờ"));
        tableTeachers.addView(header);
    }

    /** ------------------ HÀM TIỆN ÍCH ------------------ **/
    private TextView createTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text != null ? text : "-");
        tv.setPadding(16, 8, 16, 8);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    private TextView createHeaderTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(16, 12, 16, 12);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(16);
        tv.setTextColor(getResources().getColor(android.R.color.white));
        tv.setBackgroundColor(ContextCompat.getColor(this, R.color.card_bg));

        return tv;
    }

    private long getLongValue(DocumentSnapshot doc, String field) {
        return doc.getLong(field) != null ? doc.getLong(field) : 0;
    }

}
