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
import Adapter.QLLoaiBienBaoAdapter;
import DAO.BienBaoDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BienBao;
import DTO.CauHoi;
import DTO.LoaiBienBao;

public class QLBienBaoActivity extends AppCompatActivity {
    private BienBaoDAO bienBaoDAO;
    private ArrayList<BienBao> listBB;
    private RecyclerView recyclerViewDSBB;
    private ImageView imageViewThem,imgQL;
    private QLBienBaoAdapter qlBienBaoAdapter;
    private EditText editTextSearch;
    private Button buttonSearch,buttonLoad;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlbien_bao);

        Anhxa();


        recyclerViewDSBB.setNestedScrollingEnabled(true);
        bienBaoDAO = new BienBaoDAO(this);

        listBB = new ArrayList<>();

        bienBaoDAO = new BienBaoDAO(this);

        recyclerViewDSBB.setLayoutManager(new LinearLayoutManager(this));
        qlBienBaoAdapter = new QLBienBaoAdapter(this, R.layout.item_qlbienbao, listBB);
        recyclerViewDSBB.setAdapter(qlBienBaoAdapter);

        loadBienbaoData();

        imageViewThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLBienBaoActivity.this, ThemBienbaoActivity.class);
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
                List<BienBao> bienBaoList = bienBaoDAO.search(keyword);
                qlBienBaoAdapter.setData(bienBaoList);
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBienbaoData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            loadBienbaoData();
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("UPDATED", false)) {
                loadBienbaoData();
            }
        }
    }

    private void loadBienbaoData() {
        listBB.clear();
        listBB.addAll(bienBaoDAO.getAll());
        qlBienBaoAdapter.notifyDataSetChanged();
    }
    private void Anhxa() {
        imageViewThem = findViewById(R.id.imageViewthem);
        imgQL = findViewById(R.id.imageViewQL);
        recyclerViewDSBB = findViewById(R.id.RecycalDSBienbao);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonLoad = findViewById(R.id.buttonLoad);
    }
}