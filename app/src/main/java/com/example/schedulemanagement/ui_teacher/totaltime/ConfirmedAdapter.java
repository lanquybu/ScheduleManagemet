package com.example.schedulemanagement.ui_teacher.totaltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ScheduleItem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ConfirmedAdapter extends RecyclerView.Adapter<ConfirmedAdapter.ViewHolder> {

    private List<ScheduleItem> list;

    public ConfirmedAdapter(List<ScheduleItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleItem item = list.get(position);
        holder.tvModule.setText("Lớp học phần: " + item.getModule());
        holder.tvSubject.setText("Môn học: " + item.getSubject());
        String dateStr = "-";
        if (item.getDate() != null) {
            dateStr = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(item.getDate().toDate());
        }
        holder.tvDate.setText("Ngày: " + dateStr);
        holder.tvSection.setText("Tiết: " + item.getSection());
        holder.tvRoom.setText("Phòng: " + item.getRoom());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvModule, tvSubject, tvDate, tvSection, tvRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModule = itemView.findViewById(R.id.tvClassCode);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSection = itemView.findViewById(R.id.tvSection);
            tvRoom = itemView.findViewById(R.id.tvRoom);
        }
    }
}
