package com.example.schedulemanagement.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.schedulemanagement.core.AppConstants;

import org.checkerframework.checker.units.qual.A;

/**
 * Lớp tập trung các "điểm truy cập" đến Firebase.
 * Dễ thay thế/mock nếu cần test sau này.
 */
public class FirebaseSource {
    public FirebaseAuth auth(){
        return FirebaseAuth.getInstance();
    }

    public FirebaseFirestore db(){
        return FirebaseFirestore.getInstance();
    }

    public CollectionReference users(){
        return db().collection(AppConstants.USERS);
    }
}
