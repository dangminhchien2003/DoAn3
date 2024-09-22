package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.LoaiBienBaoAdapter;
import DAO.LoaiBienBaoDAO;
import DAO.LoaiCauHoiDAO;
import DTO.LoaiBienBao;
import Database.DbHelper;

public class LoaiBienBaoActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private RecyclerView recyclerViewLoaiBB;
    private LoaiBienBaoAdapter loaiBienBaoAdapter;
    private ArrayList<LoaiBienBao> list;
    private ImageView imgQL;
    private LoaiBienBaoDAO loaiBienBaoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao);

        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewLoaiBB = findViewById(R.id.recyclerViewLoaiBB);

        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbHelper = new DbHelper(this);
        list = new ArrayList<>();

        recyclerViewLoaiBB.setLayoutManager(new LinearLayoutManager(this));
        loaiBienBaoAdapter = new LoaiBienBaoAdapter(this, R.layout.item_loaibienbao, list);
        recyclerViewLoaiBB.setAdapter(loaiBienBaoAdapter);

        loaiBienBaoAdapter.setOnItemClickListener(new LoaiBienBaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiBienBao loaiBienBao) {
                Intent intent = new Intent(LoaiBienBaoActivity.this, BienBaoActivity.class);
                intent.putExtra("id_loai", loaiBienBao.getId());
                intent.putExtra("ten_loai", loaiBienBao.getTenloaiBB());
                startActivity(intent);
            }
        });

        loadLoaiBienBaoData();
    }

    private void loadLoaiBienBaoData() {
        list.clear();
        list.addAll(loaiBienBaoDAO.getAll());

        // Thông báo cho adapter biết dữ liệu đã thay đổi
        loaiBienBaoAdapter.notifyDataSetChanged();
    }
}
