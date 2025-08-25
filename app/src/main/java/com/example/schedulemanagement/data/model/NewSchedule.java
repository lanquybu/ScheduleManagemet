package com.example.schedulemanagement.data.model;

public class NewSchedule {
    private String classID;
    private String date;
    private String subject;
    private String teachername; // Tên biến được đổi để khớp với Firestore

    public NewSchedule() {
        // Bắt buộc có constructor rỗng cho Firestore
    }

    public NewSchedule(String classID, String date, String subject, String teachername) {
        this.classID = classID;
        this.date = date;
        this.subject = subject;
        this.teachername = teachername;
    }

    // Các phương thức getter (bắt buộc cho Firestore)
    public String getClassID() {
        return classID;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    // Đổi tên phương thức getter để khớp với tên biến
    public String getTeachername() {
        return teachername;
    }
}