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

import Adapter.QLLoaiBienBaoAdapter;
import Adapter.QLLoaiCauHoiAdapter;
import DAO.LoaiBienBaoDAO;
import DAO.LoaiCauHoiDAO;
import DTO.BienBao;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;
import Database.DbHelper;

public class QLLoaiBienBaoActivity extends AppCompatActivity {
    private LoaiBienBaoDAO loaiBienBaoDAO;
    private ArrayList<LoaiBienBao> listLBB;
    private RecyclerView recyclerViewDSLBB;
    private ImageView imageViewThem,imgQL;
    private QLLoaiBienBaoAdapter qlLoaiBienBaoAdapter;
    private EditText editTextSearch;
    private Button buttonSearch,buttonLoad;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlloai_bien_bao);


        imageViewThem = findViewById(R.id.imageViewthem);
        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewDSLBB = findViewById(R.id.RecycalDSLoaibienbao);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonLoad = findViewById(R.id.buttonLoad);
        recyclerViewDSLBB.setNestedScrollingEnabled(true);
        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        listLBB = new ArrayList<>();

        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        recyclerViewDSLBB.setLayoutManager(new LinearLayoutManager(this));
        qlLoaiBienBaoAdapter = new QLLoaiBienBaoAdapter(this, R.layout.item_qlloaicauhoi, listLBB);
        recyclerViewDSLBB.setAdapter(qlLoaiBienBaoAdapter);

        loadLoaiBienbaoData();

        imageViewThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLLoaiBienBaoActivity.this, ThemLoaiBienbaoActivity.class);
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
                List<LoaiBienBao> loaiBienBaoList = loaiBienBaoDAO.search(keyword);
                qlLoaiBienBaoAdapter.setData(loaiBienBaoList);
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoaiBienbaoData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            loadLoaiBienbaoData();
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("UPDATED", false)) {
                loadLoaiBienbaoData();
            }
        }
    }

    private void loadLoaiBienbaoData() {
        listLBB.clear();
        listLBB.addAll(loaiBienBaoDAO.getAll());
        qlLoaiBienBaoAdapter.notifyDataSetChanged();
    }
}