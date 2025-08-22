package com.example.schedulemanagement.data.remote;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onError(String error);
}
