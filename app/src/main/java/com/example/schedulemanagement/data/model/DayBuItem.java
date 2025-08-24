package com.example.schedulemanagement.data.model;

import com.google.firebase.Timestamp;

public class DayBuItem {
    private String subject;     // Môn học
    private String name;        // Giảng viên
    private String room;        // Phòng học
    private Timestamp absentDay; // Ngày học gốc
    private Timestamp makeUpDay; // Ngày dạy bù

    private String classId;

    public DayBuItem() {
        // Bắt buộc cho Firestore
    }

    public DayBuItem(String subject, String name, String room, Timestamp absentDay, Timestamp makeUpDay, String classId) {
        this.subject = subject;
        this.name = name;
        this.room = room;
        this.absentDay = absentDay;
        this.makeUpDay = makeUpDay;
        this.classId = classId;
    }

    public String getSubject() { return subject; }
    public String getName() { return name; }
    public String getRoom() { return room; }
    public Timestamp getAbsentDay() { return absentDay; }
    public Timestamp getMakeUpDay() { return makeUpDay; }

    public String getClassId() { return classId; }
}
