package com.example.schedulemanagement.ui_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.DayBuItem;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DayBuAdapter extends RecyclerView.Adapter<DayBuAdapter.DayBuViewHolder> {

    public interface OnConfirmClickListener {
        void onConfirmClick(DayBuItem item); // Interface callback cho Activity
    }

    private final List<DayBuItem> dayBuList;
    private final OnConfirmClickListener listener;

    public DayBuAdapter(List<DayBuItem> dayBuList, OnConfirmClickListener listener) {
        this.dayBuList = dayBuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayBuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_bu, parent, false);
        return new DayBuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayBuViewHolder holder, int position) {
        DayBuItem item = dayBuList.get(position);

        holder.txtLopHocPhan.setText(item.getClassId());
        holder.txtMonHoc.setText(item.getSubject());
        holder.txtGiangVien.setText(item.getName());
        holder.txtNgay.setText(formatDate(item.getMakeUpDay()));

        // Khi bấm nút Xác nhận, gọi callback sang Activity để mở dialog
        holder.btnXacNhan.setOnClickListener(v -> listener.onConfirmClick(item));
    }

    @Override
    public int getItemCount() {
        return dayBuList != null ? dayBuList.size() : 0;
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(timestamp.toDate());
    }

    static class DayBuViewHolder extends RecyclerView.ViewHolder {
        TextView txtLopHocPhan, txtMonHoc, txtGiangVien, txtNgay;
        Button btnXacNhan;

        public DayBuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLopHocPhan = itemView.findViewById(R.id.txtLopHocPhan);
            txtMonHoc = itemView.findViewById(R.id.txtMonHoc);
            txtGiangVien = itemView.findViewById(R.id.txtGiangVien);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
        }
    }
}
