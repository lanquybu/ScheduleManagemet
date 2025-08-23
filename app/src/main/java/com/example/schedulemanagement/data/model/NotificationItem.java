package com.example.schedulemanagement.data.model;

import com.google.firebase.Timestamp;

public class NotificationItem {
    private String id;        // id của document (có thể set thủ công sau)
    private String title;     // tiêu đề thông báo
    private String message;   // nội dung thông báo
    private String classId;   // lớp liên quan (có thể null)
    private Timestamp timestamp; // thời gian tạo thông báo

    // Bắt buộc cần constructor rỗng để Firestore map dữ liệu
    public NotificationItem() {}

    public NotificationItem(String id, String title, String message, String classId, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.classId = classId;
        this.timestamp = timestamp;
    }

    // Constructor rút gọn nếu không cần classId
    public NotificationItem(String title, String message, Timestamp timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getter & Setter
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
