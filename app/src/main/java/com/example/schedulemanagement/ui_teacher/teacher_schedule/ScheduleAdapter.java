package com.example.schedulemanagement.ui_teacher.teacher_schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ScheduleItem;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleItem> scheduleList;
    private OnItemClickListener listener;

    // Interface để bắt sự kiện click
    public interface OnItemClickListener {
        void onItemClick(ScheduleItem item, int position);
    }

    public ScheduleAdapter(List<ScheduleItem> scheduleList, OnItemClickListener listener) {
        this.scheduleList = scheduleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem item = scheduleList.get(position);

        holder.tvClassCode.setText("Lớp học phần: " + item.getModule());
        holder.tvSubject.setText("Môn học: " + item.getSubject());
        holder.tvRoom.setText("Phòng: " + item.getRoom());
        holder.tvSection.setText("Tiết: " + item.getSection());

        // Convert Timestamp -> Date string
        Timestamp timestamp = item.getDate();
        if (timestamp != null) {
            Date date = timestamp.toDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.tvDate.setText("Ngày: " + sdf.format(date));
        } else {
            holder.tvDate.setText("Ngày: --/--/----");
        }

        // Xử lý click item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassCode, tvSubject, tvDate, tvSection, tvRoom;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassCode = itemView.findViewById(R.id.tvClassCode);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSection = itemView.findViewById(R.id.tvSection);
            tvRoom = itemView.findViewById(R.id.tvRoom);
        }
    }
}
