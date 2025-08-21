package com.example.schedulemanagement.ui_teacher.verifytime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ScheduleItem;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ScheduleItem item, int position);
    }

    private List<ScheduleItem> scheduleList;
    private OnItemClickListener listener;

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
        holder.tvModule.setText("Lớp học phần: " + item.getModule());
        holder.tvSubject.setText("Môn học: " + item.getSubject());
        holder.tvDate.setText("Ngày: " + item.getDate());
        holder.tvSection.setText("Tiết: " + item.getSection());
        holder.tvRoom.setText("Phòng: " + item.getRoom());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item, position));

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvModule, tvSubject, tvDate, tvSection, tvRoom;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModule = itemView.findViewById(R.id.tvClassCode);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSection = itemView.findViewById(R.id.tvSection);
            tvRoom = itemView.findViewById(R.id.tvRoom);
        }
    }
}