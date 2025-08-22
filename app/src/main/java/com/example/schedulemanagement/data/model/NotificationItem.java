package com.example.schedulemanagement.data.model;

import com.google.firebase.Timestamp;

public class NotificationItem {
    private String id;
    private String title;
    private String message;
    private String classId;     // để lọc theo lớp
    private Timestamp timestamp;

    public NotificationItem() {}

    public NotificationItem(String id, String title, String message, String classId, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.classId = classId;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
