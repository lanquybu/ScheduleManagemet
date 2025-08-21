package com.example.schedulemanagement.ui_teacher.teachingclass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.data.model.TeachingClassItem;
import com.example.schedulemanagement.ui_teacher.teacher_home.TeacherMenuActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ClassListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private List<TeachingClassItem> classList;
    private FirebaseFirestore db;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        recyclerView = findViewById(R.id.recyclerViewClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        classList = new ArrayList<>();
        adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadDataFromFirestore();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassListActivity.this, TeacherMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadDataFromFirestore() {
        db.collection("classes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                classList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Log.d("Firestore", "Doc ID: " + doc.getId() + " => " + doc.getData());
                    String subject = doc.getString("subject");
                    String classCode = doc.getString("classCode");
                    String module = doc.getString("module");
                    TeachingClassItem item = new TeachingClassItem(classCode, module, subject);
                    classList.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}