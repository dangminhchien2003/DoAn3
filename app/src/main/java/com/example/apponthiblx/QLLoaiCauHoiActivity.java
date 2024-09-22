package com.example.apponthiblx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.QLLoaiCauHoiAdapter;
import DAO.LoaiCauHoiDAO;
import DTO.BienBao;
import DTO.LoaiCauHoi;
import Database.DbHelper;

public class QLLoaiCauHoiActivity extends AppCompatActivity {
    private LoaiCauHoiDAO loaiCauHoiDAO;
    private ArrayList<LoaiCauHoi> listLCH;
    private DbHelper dbHelper;
    private RecyclerView recyclerViewDSLCH;
    private ImageView imageViewThem,imgQL;
    private QLLoaiCauHoiAdapter qlLoaiCauHoiAdapter;
    private EditText editTextSearch;
    private Button buttonSearch,buttonLoad;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlloai_cau_hoi);

        imageViewThem = findViewById(R.id.imageViewthem);
        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewDSLCH = findViewById(R.id.RecycalDSLoaicauhoi);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonLoad = findViewById(R.id.buttonLoad);
        recyclerViewDSLCH.setNestedScrollingEnabled(true);
        loaiCauHoiDAO = new LoaiCauHoiDAO(this);

        dbHelper = new DbHelper(this);
        listLCH = new ArrayList<>();

        loaiCauHoiDAO = new LoaiCauHoiDAO(this);

        recyclerViewDSLCH.setLayoutManager(new LinearLayoutManager(this));
        qlLoaiCauHoiAdapter = new QLLoaiCauHoiAdapter(this, R.layout.item_qlloaicauhoi, listLCH);
        recyclerViewDSLCH.setAdapter(qlLoaiCauHoiAdapter);

        loadLoaiCauHoiData();

        imageViewThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLLoaiCauHoiActivity.this, ThemLoaiCauHoiActivity.class);
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
                List<LoaiCauHoi> loaiCauHoiList = loaiCauHoiDAO.search(keyword);
                qlLoaiCauHoiAdapter.setData(loaiCauHoiList);
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoaiCauHoiData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            loadLoaiCauHoiData();
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("UPDATED", false)) {
                loadLoaiCauHoiData();
            }
        }
    }

    private void loadLoaiCauHoiData() {
        listLCH.clear();
        listLCH.addAll(loaiCauHoiDAO.getAll());
        qlLoaiCauHoiAdapter.notifyDataSetChanged();
    }
}
