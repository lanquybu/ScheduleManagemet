package com.example.schedulemanagement.ui_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.ClassScheduleItem;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<ClassScheduleItem> reportList;

    public ReportAdapter(List<ClassScheduleItem> reportList) {
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_class, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ClassScheduleItem item = reportList.get(position);

        holder.tvClassCode.setText(item.getClassCode());
        holder.tvSubject.setText(item.getSubject());
        holder.tvDate.setText(item.getDate());
        holder.tvSection.setText(item.getSection());
        holder.tvRoom.setText(item.getRoom());
        holder.tvTeacherName.setText(item.getTeacherName());
        holder.tvStatus.setText(item.getStatus());
    }

    @Override
    public int getItemCount() {
        return reportList != null ? reportList.size() : 0;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassCode, tvSubject, tvDate, tvSection, tvRoom, tvTeacherName, tvStatus;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassCode = itemView.findViewById(R.id.tvClassCode);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSection = itemView.findViewById(R.id.tvSection);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    public void updateData(List<ClassScheduleItem> newList) {
        this.reportList = newList;
        notifyDataSetChanged();
    }
}
