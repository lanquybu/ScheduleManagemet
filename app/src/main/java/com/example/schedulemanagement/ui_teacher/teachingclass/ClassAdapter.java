package com.example.schedulemanagement.ui_teacher.teachingclass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.TeachingClassItem;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private List<TeachingClassItem> classList;

    public ClassAdapter(List<TeachingClassItem> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class_row, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        TeachingClassItem item = classList.get(position);
        holder.tvSubject.setText(item.getSubject());
        holder.tvClassCode.setText(item.getClassCode());
        holder.tvModule.setText(item.getModule());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvClassCode, tvModule;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvClassCode = itemView.findViewById(R.id.tvClassCode);
            tvModule = itemView.findViewById(R.id.tvModule);
        }
    }
}