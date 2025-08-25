package com.example.schedulemanagement.ui_admin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.NewSchedule;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class NewScheduleAdapter extends RecyclerView.Adapter<NewScheduleAdapter.ViewHolder> {

    private List<NewSchedule> scheduleList;

    public NewScheduleAdapter(List<NewSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewSchedule schedule = scheduleList.get(position);

        holder.txtClassID.setText(schedule.getClassID());
        holder.txtSubject.setText(schedule.getSubject());
        holder.txtTeacher.setText(schedule.getTeachername());
        holder.txtDate.setText(schedule.getDate());

        // Update the button's logic to show a confirmation dialog
        holder.btnXacNhan.setOnClickListener(v -> {
            showConfirmationDialog(v.getContext(), schedule, position);
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    private void showConfirmationDialog(Context context, NewSchedule schedule, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_confirm_schedule, null);
        builder.setView(dialogView);

        // Find views in the dialog layout
        TextView tvClassName = dialogView.findViewById(R.id.tvClassName);
        TextView tvSubject = dialogView.findViewById(R.id.tvSubject);
        TextView tvDate = dialogView.findViewById(R.id.tvDate);
        TextView tvRoom = dialogView.findViewById(R.id.tvRoom);
        TextView tvPeriod = dialogView.findViewById(R.id.tvPeriod);
        TextView tvTeacher = dialogView.findViewById(R.id.tvTeacher);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        // Fill dialog views with data from the NewSchedule object
        tvClassName.setText("Lớp: " + schedule.getClassID());
        tvSubject.setText("Môn học: " + schedule.getSubject());
        tvDate.setText("Ngày: " + schedule.getDate());
        // Note: I'm assuming your NewSchedule model and Firestore data have these fields.
        // If not, you'll need to update them to prevent a crash.
        // For now, I'll use placeholders.
        tvRoom.setText("Phòng: ");
        tvPeriod.setText("Tiết: ");
        tvTeacher.setText("Giảng viên: " + schedule.getTeachername());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Handle the "Cancel" button click
        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        // Handle the "Confirm" button click
        btnConfirm.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("confirmedSchedules")
                    .add(schedule)
                    .addOnSuccessListener(ref -> {
                        Toast.makeText(context, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                        scheduleList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, scheduleList.size());
                        alertDialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    });
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtClassID, txtSubject, txtTeacher, txtDate;
        Button btnXacNhan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClassID = itemView.findViewById(R.id.txtLopHocPhan);
            txtSubject = itemView.findViewById(R.id.txtMonHoc);
            txtTeacher = itemView.findViewById(R.id.txtGiangVien);
            txtDate = itemView.findViewById(R.id.txtNgay);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
        }
    }
}