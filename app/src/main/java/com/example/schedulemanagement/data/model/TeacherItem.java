package com.example.schedulemanagement.data.model;

public class TeacherItem {
    private String teacherId;     // Mã giảng viên
    private String teacherName;   // Tên giảng viên
    private String department;    // Bộ môn
    private int subjectCount;     // Số môn phụ trách
    private int teachingHours;    // Số giờ giảng dạy

    // Bắt buộc: constructor rỗng cho Firestore
    public TeacherItem() {}

    public TeacherItem(String teacherId, String teacherName, String department,
                       int subjectCount, int teachingHours) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.department = department;
        this.subjectCount = subjectCount;
        this.teachingHours = teachingHours;
    }

    // Getter & Setter
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
    }

    public int getTeachingHours() {
        return teachingHours;
    }

    public void setTeachingHours(int teachingHours) {
        this.teachingHours = teachingHours;
    }
}
