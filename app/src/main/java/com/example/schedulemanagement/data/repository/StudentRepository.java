package com.example.schedulemanagement.data.repository;

import com.example.schedulemanagement.data.model.ScheduleItem;
import com.example.schedulemanagement.data.remote.FirebaseSource;
import com.example.schedulemanagement.data.remote.ResultCallback;

import java.util.List;

public class StudentRepository {
    private final FirebaseSource firebaseSource = new FirebaseSource();

    public void getSchedule(ResultCallback<List<ScheduleItem>> callback) {
        firebaseSource.getStudentSchedule(callback);
    }
}