package com.example.schedulemanagement.ui_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ScheduleItem2;

import java.util.List;

public class ScheduleAdapter2 extends RecyclerView.Adapter<ScheduleAdapter2.ScheduleViewHolder> {

    private List<ScheduleItem2> scheduleList;

    public ScheduleAdapter2(List<ScheduleItem2> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule2, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem2 item = scheduleList.get(position);
        holder.tvClassId.setText(item.getClassId());     // lớp học phần
        holder.tvSubject.setText(item.getSubject());     // môn học
        holder.tvTeacher.setText(item.getTeacherName()); // giảng viên
        holder.tvDate.setText(item.getDate().toDate().toString());            // ngày
        holder.tvRoom.setText(item.getRoom());            // phòng
        holder.tvSection.setText(item.getSection());      // tiết
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassId, tvSubject, tvTeacher, tvDate, tvRoom, tvSection;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassId = itemView.findViewById(R.id.tvClassId);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTeacher = itemView.findViewById(R.id.tvTeacher);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvSection = itemView.findViewById(R.id.tvSection);
        }
    }
}
