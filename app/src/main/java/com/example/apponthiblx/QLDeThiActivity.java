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

import Adapter.QLBoDeAdapter;
import Adapter.QLLoaiBienBaoAdapter;
import DAO.BoDeDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BienBao;
import DTO.BoDe;
import DTO.LoaiBienBao;

public class QLDeThiActivity extends AppCompatActivity {
    private BoDeDAO boDeDAO;
    private ArrayList<BoDe> listBD;
    private RecyclerView recyclerViewDSLBD;
    private ImageView imageViewThem,imgQL;
    private QLBoDeAdapter qlBoDeAdapter;
    private EditText editTextSearch;
    private Button buttonSearch,buttonLoad;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlde_thi);

        imageViewThem = findViewById(R.id.imageViewthem);
        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewDSLBD = findViewById(R.id.RecycalDSDethi);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonLoad = findViewById(R.id.buttonLoad);
        recyclerViewDSLBD.setNestedScrollingEnabled(true);

        boDeDAO = new BoDeDAO(this);

        listBD = new ArrayList<>();


        recyclerViewDSLBD.setLayoutManager(new LinearLayoutManager(this));
        qlBoDeAdapter = new QLBoDeAdapter(this, R.layout.item_qlbode, listBD);
        recyclerViewDSLBD.setAdapter(qlBoDeAdapter);

        loadBoDeData();

        imageViewThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLDeThiActivity.this, ThemBoDeActivity.class);
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
                List<BoDe> boDeList = boDeDAO.search(keyword);
                qlBoDeAdapter.setData(boDeList);
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBoDeData();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            loadBoDeData();
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("UPDATED", false)) {
                loadBoDeData();
            }
        }
    }

    private void loadBoDeData() {
        listBD.clear();
        listBD.addAll(boDeDAO.getAll());
        qlBoDeAdapter.notifyDataSetChanged();
    }
}