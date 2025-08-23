package com.example.schedulemanagement.data.remote;

import com.example.schedulemanagement.data.model.NotificationItem;
import com.example.schedulemanagement.data.model.ScheduleItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSource {
    // Thêm vào để AuthRepository dùng được
    private final FirebaseAuth auth;
    private final CollectionReference users;
    private final FirebaseFirestore db;

    public FirebaseSource() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        users = db.collection("users");
    }

    // Hàm lấy lịch học cho sinh viên
    public void getStudentSchedule(ResultCallback<List<ScheduleItem>> callback) {
        db.collection("confirmedSchedules")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ScheduleItem> schedules = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        ScheduleItem item = doc.toObject(ScheduleItem.class);
                        if (item != null) schedules.add(item);
                    }
                    callback.onSuccess(schedules);
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void getStudentNotifications(String classId, ResultCallback<List<NotificationItem>> callback) {
        db.collection("studentNotifications")
                .whereEqualTo("classId", classId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<NotificationItem> notis = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        NotificationItem item = doc.toObject(NotificationItem.class);
                        if (item != null) notis.add(item);
                    }
                    callback.onSuccess(notis);
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }


    public FirebaseAuth auth() {
        return auth;
    }

    public CollectionReference users() {
        return users;
    }
}