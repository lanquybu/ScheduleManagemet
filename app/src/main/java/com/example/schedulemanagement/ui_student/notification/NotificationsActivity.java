package com.example.schedulemanagement.ui_student.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.NotificationItem;
import com.example.schedulemanagement.data.remote.FirebaseSource;
import com.example.schedulemanagement.data.remote.ResultCallback;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NotificationAdapter adapter;
    private final List<NotificationItem> notiList = new ArrayList<>();
    private final FirebaseSource firebaseSource = new FirebaseSource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(notiList);
        recyclerView.setAdapter(adapter);

        loadNotifications();
    }

    private void loadNotifications() {
        progressBar.setVisibility(View.VISIBLE);

        // giả sử classId của sinh viên đang login = "IT01"
        String studentClassId = "IT01";

        firebaseSource.getStudentNotifications(new ResultCallback<List<NotificationItem>>() {
            @Override
            public void onSuccess(List<NotificationItem> data) {
                progressBar.setVisibility(View.GONE);
                notiList.clear();
                notiList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NotificationsActivity.this, "Lỗi: " + message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
