package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import Adapter.BoDeAdapter;
import DAO.BoDeDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BoDe;
import Database.DbHelper;

public class BoDeActivity extends AppCompatActivity {
    private BoDeAdapter adapter;
    private ArrayList<BoDe> boDeList;
    private ImageView imgQL;
    private DbHelper dbHelper;
    private BoDeDAO boDeDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bo_de);

        imgQL = findViewById(R.id.imageViewQL);
        GridView gridView = findViewById(R.id.gridLayout);

        dbHelper = new DbHelper(this);
        boDeDAO = new BoDeDAO(this);

        // Lấy danh sách các đề từ cơ sở dữ liệu SQLite
        boDeList = boDeDAO.getAll();

        adapter = new BoDeAdapter(this, boDeList);
        gridView.setAdapter(adapter);

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BoDeActivity.this, DethiActivity.class);
                intent.putExtra("ten_de", boDeList.get(position).getTen_de());
                intent.putExtra("id_de", boDeList.get(position).getId_de());
                startActivity(intent);
            }
        });
    }
}
