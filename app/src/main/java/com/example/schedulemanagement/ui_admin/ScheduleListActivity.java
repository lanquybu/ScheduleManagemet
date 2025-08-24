package com.example.schedulemanagement.ui_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.ui_admin.ScheduleAdapter2;
import com.example.schedulemanagement.data.model.ScheduleItem2;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ScheduleListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScheduleAdapter2 adapter;
    private List<ScheduleItem2> scheduleList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewSchedules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        scheduleList = new ArrayList<>();
        adapter = new ScheduleAdapter2(scheduleList);
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("ClassSchedule")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            // Xử lý lỗi nếu có
                            return;
                        }
                        if (snapshots != null) {
                            scheduleList.clear();
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                ScheduleItem2 item = doc.toObject(ScheduleItem2.class);
                                if (item != null) {
                                    scheduleList.add(item);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
