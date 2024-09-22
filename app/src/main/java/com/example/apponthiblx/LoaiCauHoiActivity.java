package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.BienBaoAdapter;
import Adapter.LoaiBienBaoAdapter;
import Adapter.LoaiCauHoiAdapter;
import DAO.LoaiCauHoiDAO;
import DTO.BienBao;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;
import Database.DbHelper;

public class LoaiCauHoiActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private RecyclerView recyclerViewLCH;
    private LoaiCauHoiAdapter loaiCauHoiAdapter;
    private ArrayList<LoaiCauHoi> listLCH;
    private ImageView imgQL,imgTimkiem;
    private LoaiCauHoiDAO loaiCauHoiDAO;
    private TextView textView200CH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_cau_hoi);

        imgQL = findViewById(R.id.imageViewQL);
        imgTimkiem = findViewById(R.id.imageViewsearch);
        recyclerViewLCH = findViewById(R.id.recyclerViewLCH);
        textView200CH = findViewById(R.id.textView200CH);

        loaiCauHoiDAO = new LoaiCauHoiDAO(this);

        dbHelper = new DbHelper(this);
        listLCH = new ArrayList<>();

        recyclerViewLCH.setLayoutManager(new LinearLayoutManager(this));
        loaiCauHoiAdapter = new LoaiCauHoiAdapter(this, R.layout.item_loaicauhoi, listLCH);
        recyclerViewLCH.setAdapter(loaiCauHoiAdapter);

        loaiCauHoiAdapter.setOnItemClickListener(new LoaiCauHoiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiCauHoi loaiCauHoi) {
                Intent intent = new Intent(LoaiCauHoiActivity.this, CauHoiActivity.class);
                intent.putExtra("id_loai", loaiCauHoi.getId());
                intent.putExtra("ten_loai", loaiCauHoi.getTenloai());
                startActivity(intent);
            }
        });

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoaiCauHoiActivity.this, TimKiemCauHoiActivity.class);
                startActivity(intent);
            }
        });

        loadLoaiCauHoiData();
    }
    private void loadLoaiCauHoiData() {
        listLCH.clear();
        listLCH.addAll(loaiCauHoiDAO.getAll());

        // Thông báo cho adapter biết dữ liệu đã thay đổi
        loaiCauHoiAdapter.notifyDataSetChanged();
    }
}