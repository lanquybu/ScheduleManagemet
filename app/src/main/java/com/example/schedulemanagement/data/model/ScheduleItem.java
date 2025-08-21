package com.example.schedulemanagement.data.model;

import com.google.firebase.Timestamp;

public class ScheduleItem {
    private String id;
    private String subject;   // Môn học
    private String module;    // Mã học phần
    private String room;      // Phòng học
    private String section;   // Tiết học (VD: 1-3)
    private Timestamp date;   // Ngày giờ học (Firestore Timestamp)

    public ScheduleItem() {}

    public ScheduleItem(String id, String subject, String module, String room,
                        String section, Timestamp date) {
        this.id = id;
        this.subject = subject;
        this.module = module;
        this.room = room;
        this.section = section;
        this.date = date;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
}