package com.example.schedulemanagement.data.model;

public class TeachingClassItem {
    private String subject;
    private String classCode;
    private String module;

    public TeachingClassItem() {} // cần constructor rỗng cho Firestore

    public TeachingClassItem(String subject, String classCode, String module) {
        this.subject = subject;
        this.classCode = classCode;
        this.module = module;
    }

    public String getSubject() { return subject; }
    public String getClassCode() { return classCode; }
    public String getModule() { return module; }
}
