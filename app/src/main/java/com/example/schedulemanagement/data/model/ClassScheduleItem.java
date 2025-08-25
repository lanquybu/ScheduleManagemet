package com.example.schedulemanagement.data.model;

public class ClassScheduleItem {
    private String classCode;
    private String subject;
    private String date;
    private String section;
    private String room;
    private String teacherName;
    private String status; // Trạng thái báo cáo (ví dụ: Đã dạy, Nghỉ, Bù...)

    public ClassScheduleItem() {
        // Bắt buộc cho Firestore
    }

    public ClassScheduleItem(String classCode, String subject, String date, String section, String room, String teacherName, String status) {
        this.classCode = classCode;
        this.subject = subject;
        this.date = date;
        this.section = section;
        this.room = room;
        this.teacherName = teacherName;
        this.status = status;
    }

    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
