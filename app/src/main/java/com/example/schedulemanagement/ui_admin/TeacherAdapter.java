package com.example.schedulemanagement.ui_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.TeacherItem;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private List<TeacherItem> teacherList;

    public TeacherAdapter(List<TeacherItem> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        TeacherItem teacher = teacherList.get(position);
        if (teacher == null) return;

        holder.tvTeacherID.setText("Mã GV: " + teacher.getTeacherId());
        holder.tvTeacherName.setText("Tên: " + teacher.getTeacherName());
        holder.tvDepartment.setText("Bộ môn: " + teacher.getDepartment());
        holder.tvNumClasses.setText("Số môn dạy: " + teacher.getSubjectCount());
        holder.tvNumHours.setText("Số giờ dạy: " + teacher.getTeachingHours());
    }

    @Override
    public int getItemCount() {
        return teacherList != null ? teacherList.size() : 0;
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeacherID, tvTeacherName, tvDepartment, tvNumClasses, tvNumHours;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeacherID = itemView.findViewById(R.id.tvTeacherID);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvNumClasses = itemView.findViewById(R.id.tvNumClasses);
            tvNumHours = itemView.findViewById(R.id.tvNumHours);
        }
    }
}
