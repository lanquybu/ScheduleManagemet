package com.example.schedulemanagement.ui.schedule;

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

    private final List<ScheduleItem> scheduleList;

    public ScheduleAdapter(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem item = scheduleList.get(position);
        holder.tvCourse.setText(item.getSubject());
        holder.tvRoom.setText(item.getRoom());
        holder.tvPeriods.setText("Tiết: " + item.getSection());

        if (item.getDate() != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
            holder.tvTime.setText(sdf.format(item.getDate().toDate()));
        } else {
            holder.tvTime.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList != null ? scheduleList.size() : 0;
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourse, tvRoom, tvTime, tvPeriods;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPeriods = itemView.findViewById(R.id.tvPeriods);
        }
    }
}
