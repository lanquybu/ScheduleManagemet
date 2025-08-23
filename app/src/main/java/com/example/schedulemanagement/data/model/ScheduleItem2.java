package com.example.schedulemanagement.data.model;
import com.google.firebase.Timestamp;

public class ScheduleItem2 {
    private String classId;
    private String subject;       // thêm trường môn học
    private String teacherName;
    private String section;
    private String room;
    private int capacity;        // có hoặc không cũng được, không hiển thị vẫn giữ
    private Timestamp date;

    public ScheduleItem2() {
    }

    public ScheduleItem2(String classId, String subject, String teacherName, String section, String room, int capacity, Timestamp date) {
        this.classId = classId;
        this.subject = subject;
        this.teacherName = teacherName;
        this.section = section;
        this.room = room;
        this.capacity = capacity;
        this.date = date;
    }

    public String getClassId() {
        return classId;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSection() {
        return section;
    }

    public String getRoom() {
        return room;
    }

    public int getCapacity() {
        return capacity;
    }

    public Timestamp getDate() { return date; }
}
