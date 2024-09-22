package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import Adapter.QLBienBaoAdapter;
import Adapter.QLCauHoiAdapter;
import DAO.BienBaoDAO;
import DAO.CauHoiDAO;
import DTO.BienBao;
import DTO.CauHoi;

public class QLCauHoiActivity extends AppCompatActivity {
    private CauHoiDAO cauHoiDAO;
    private ArrayList<CauHoi> listCH;
    private RecyclerView recyclerViewDSCH;
    private ImageView imageViewThem,imgQL;
    private QLCauHoiAdapter qlCauHoiAdapter;
    private EditText editTextSearch;
    private Button buttonSearch,buttonLoad;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlcau_hoi);

        Anhxa();

        recyclerViewDSCH.setNestedScrollingEnabled(true);
        cauHoiDAO = new CauHoiDAO(this);

        listCH = new ArrayList<>();

        recyclerViewDSCH.setLayoutManager(new LinearLayoutManager(this));
        qlCauHoiAdapter = new QLCauHoiAdapter(this, R.layout.item_qlcauhoi, listCH);
        recyclerViewDSCH.setAdapter(qlCauHoiAdapter);

        loadCauhoiData();

        imageViewThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLCauHoiActivity.this, ThemCauHoiActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

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
                qlCauHoiAdapter.setData(cauHoiList);
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCauhoiData();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            loadCauhoiData();
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("UPDATED", false)) {
                loadCauhoiData();
            }
        }
    }
    private void loadCauhoiData() {
        listCH.clear();
        listCH.addAll(cauHoiDAO.getAllCauHoi());
        qlCauHoiAdapter.notifyDataSetChanged();
    }

    private void Anhxa() {
        imageViewThem = findViewById(R.id.imageViewthem);
        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewDSCH = findViewById(R.id.RecycalDScauhoi);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonLoad = findViewById(R.id.buttonLoad);
    }
}