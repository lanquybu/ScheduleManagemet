package com.example.schedulemanagement.data.model;

public class ProgressItem {
    private int completed;
    private int total;

    public ProgressItem() {}

    public ProgressItem(int completed, int total) {
        this.completed = completed;
        this.total = total;
    }

    public int getCompleted() { return completed; }
    public int getTotal() { return total; }
    public int getPercent() {
        return total > 0 ? (int) ((completed * 100.0) / total) : 0;
    }
}
