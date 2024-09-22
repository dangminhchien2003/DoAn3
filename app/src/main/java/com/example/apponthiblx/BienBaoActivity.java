package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import DAO.BienBaoDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BienBao;
import DTO.LoaiBienBao;
import Database.DbHelper;

public class BienBaoActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private RecyclerView recyclerViewBB;
    private BienBaoAdapter bienBaoAdapter;
    private ArrayList<BienBao> listbb;
    private ImageView imgQL;
    private TextView textViewtenBB;
    private BienBaoDAO bienBaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao2);

        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewBB = findViewById(R.id.recyclerViewBB);
        textViewtenBB = findViewById(R.id.textViewTenloaibien);

        bienBaoDAO = new BienBaoDAO(this);

        dbHelper = new DbHelper(this);
        listbb = new ArrayList<>();

        recyclerViewBB.setLayoutManager(new LinearLayoutManager(this));
        bienBaoAdapter = new BienBaoAdapter(this, R.layout.item_bienbao, listbb);
        recyclerViewBB.setAdapter(bienBaoAdapter);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String tenLoaiBienBao = intent.getStringExtra("ten_loai");
            String idLoaiBienBao = intent.getStringExtra("id_loai");

            loadLoaiBienBaoData(idLoaiBienBao);
            // Gán tên loại biển báo vào TextView
            textViewtenBB.setText(tenLoaiBienBao);
        }

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void loadLoaiBienBaoData(String idLoaiBienBao) {
        listbb.clear();
        listbb.addAll(bienBaoDAO.getBienBaoByLoai(idLoaiBienBao));
        // Thông báo cho adapter biết dữ liệu đã thay đổi
        bienBaoAdapter.notifyDataSetChanged();
    }

}