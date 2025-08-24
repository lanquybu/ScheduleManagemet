package com.example.schedulemanagement.ui_teacher.teacher_schedule;

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
import com.example.schedulemanagement.ui_teacher.teacher_schedule.ScheduleAdapter;
import com.example.schedulemanagement.ui_teacher.verifytime.VerifyTeachingTimeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeacherScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<ScheduleItem> scheduleList = new ArrayList<>();
    private FirebaseFirestore db;
    private CollectionReference scheduleRef;
    private ImageView btnBack;
    private ImageView btnAdd;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_schedule);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);

        adapter = new ScheduleAdapter(scheduleList, (item, pos) -> {
            Toast.makeText(this, "Chọn: " + item.getSubject(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherScheduleActivity.this, TeacherMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherScheduleActivity.this, AddScheduleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherScheduleActivity.this, MakeUpScheduleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });



        db = FirebaseFirestore.getInstance();
        scheduleRef = db.collection("schedule");

        // Load danh sách từ Firestore
        loadScheduleFromFirestore();


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
                        Timestamp date = doc.getTimestamp("date");




                        ScheduleItem item = new ScheduleItem(  doc.getId(),  // id
                                subject,
                                module,
                                room,
                                section,
                                date);

                        scheduleList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                });
    }



}

