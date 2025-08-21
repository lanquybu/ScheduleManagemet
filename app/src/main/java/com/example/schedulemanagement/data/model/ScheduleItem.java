package com.example.schedulemanagement.data.model;

public class ScheduleItem {
    private String id;
    private String module;
    private String subject;
    private String date;
    private String room;
    private String section;


    public ScheduleItem() {
        // Firestore cần constructor rỗng
    }

    public ScheduleItem(String module, String subject, String date, String room, String section) {
        this.module = module;
        this.subject = subject;
        this.date = date;
        this.room = room;
        this.section = section;


    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getModule() {
        return module;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public String getSection() {
        return section;
    }
}
