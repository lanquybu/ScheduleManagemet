package com.example.schedulemanagement.ui_admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schedulemanagement.R;
import com.example.schedulemanagement.ui_admin.ScheduleItem;

public class NewTeachingScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teaching_schedule);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Lấy các View từ 3 thẻ include đã được đặt ID trong XML
        View schedule1View = findViewById(R.id.schedule1);
        View schedule2View = findViewById(R.id.schedule2);
        View schedule3View = findViewById(R.id.schedule3);

        // Lấy các nút "Xác nhận" từ mỗi View đã include
        Button btnConfirm1 = schedule1View.findViewById(R.id.btnConfirm);
        Button btnConfirm2 = schedule2View.findViewById(R.id.btnConfirm);
        Button btnConfirm3 = schedule3View.findViewById(R.id.btnConfirm);

        // Gán sự kiện click cho từng nút.
        btnConfirm1.setOnClickListener(v -> showConfirmDialog());
        btnConfirm2.setOnClickListener(v -> showConfirmDialog());
        btnConfirm3.setOnClickListener(v -> showConfirmDialog());
    }

    private void showConfirmDialog() {
        // Tạo và hiển thị AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm_schedule, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Gán dữ liệu mặc định hoặc trống vào các TextView
        TextView tvClassName = dialogView.findViewById(R.id.tvClassName);
        tvClassName.setText("Lớp: ...");

        TextView tvSubject = dialogView.findViewById(R.id.tvSubject);
        tvSubject.setText("Môn học: ...");

        TextView tvDate = dialogView.findViewById(R.id.tvDate);
        tvDate.setText("Ngày: ...");

        TextView tvRoom = dialogView.findViewById(R.id.tvRoom);
        tvRoom.setText("Phòng: ...");

        TextView tvPeriod = dialogView.findViewById(R.id.tvPeriod);
        tvPeriod.setText("Tiết: ...");

        TextView tvTeacher = dialogView.findViewById(R.id.tvTeacher);
        tvTeacher.setText("Giảng viên: ...");

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            Toast.makeText(this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}