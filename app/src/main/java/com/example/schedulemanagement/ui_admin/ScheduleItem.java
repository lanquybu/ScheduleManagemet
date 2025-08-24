package com.example.schedulemanagement.ui_admin;

public class ScheduleItem {
    private String classId;
    private String subject;
    private String teacher;
    private String date;
    private String room;
    private String period;

    public ScheduleItem(String classId, String subject, String teacher, String date, String room, String period) {
        this.classId = classId;
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
        this.room = room;
        this.period = period;
    }

    public String getClassId() { return classId; }
    public String getSubject() { return subject; }
    public String getTeacher() { return teacher; }
    public String getDate() { return date; }
    public String getRoom() { return room; }
    public String getPeriod() { return period; }
}