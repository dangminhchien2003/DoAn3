package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import Adapter.TKCauHoiAdapter;
import DAO.CauHoiDAO;
import DTO.CauHoi;

public class TimKiemCauHoiActivity extends AppCompatActivity {
    private ImageView imgQL;
    private RecyclerView recyclerViewTK;
    private TKCauHoiAdapter tKCauhoiAdapter;
    private CauHoiDAO cauHoiDAO;
    private EditText editTextSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_cau_hoi);

        imgQL = findViewById(R.id.imageViewQL);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerViewTK = findViewById(R.id.RecyclerViewTimKiem);

        recyclerViewTK.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo CauHoiDAO
        cauHoiDAO = new CauHoiDAO(this);

        // Lấy danh sách tất cả các câu hỏi từ CauHoiDAO
        List<CauHoi> allCauHoiList = cauHoiDAO.getAllCauHoi();

        // Khởi tạo và thiết lập Adapter với danh sách tất cả các câu hỏi
        tKCauhoiAdapter = new TKCauHoiAdapter(allCauHoiList);
        recyclerViewTK.setAdapter(tKCauhoiAdapter);

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editTextSearch.getText().toString();
                List<CauHoi> cauHoiList = cauHoiDAO.searchQuestions(keyword);
                tKCauhoiAdapter.setData(cauHoiList);
            }
        });
    }
}
