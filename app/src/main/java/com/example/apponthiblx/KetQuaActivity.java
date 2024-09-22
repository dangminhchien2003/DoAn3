package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.CauHoiSaiAdapter;
import DTO.CauHoi;

public class KetQuaActivity extends AppCompatActivity {
    TextView textViewSOCau, textViewDat;
    private ImageView imgQL, imageViewLoad;
    private ArrayList<CauHoi> wrongQuestions;
    private RecyclerView recyclerViewWrongQuestions;
    private CauHoiSaiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);

        textViewSOCau = findViewById(R.id.textViewSoCau);
        textViewDat = findViewById(R.id.textViewDat);
        imgQL = findViewById(R.id.imageViewQL);
        imageViewLoad = findViewById(R.id.imageViewLoad);
        recyclerViewWrongQuestions = findViewById(R.id.Danhsachcausai);

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Nhận dữ liệu từ Intent
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        //int wrongAnswers = getIntent().getIntExtra("wrongAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        wrongQuestions = getIntent().getParcelableArrayListExtra("wrongQuestions");


        textViewSOCau.setText(correctAnswers + "/" + totalQuestions);


        // Cập nhật textViewDat dựa trên số câu trả lời đúng
        if (correctAnswers < 5) {
            textViewDat.setText("KHÔNG ĐẠT");
            textViewDat.setBackground(getDrawable(R.drawable.backgroud_textviewkhongdat));
        } else {
            textViewDat.setText("ĐẠT");
            textViewDat.setBackground(getDrawable(R.drawable.backgroud_texviewdat));
        }

        // Cấu hình RecyclerView
        recyclerViewWrongQuestions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CauHoiSaiAdapter(this, wrongQuestions);
        recyclerViewWrongQuestions.setAdapter(adapter);
    }
}
