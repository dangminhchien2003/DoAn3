package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import DAO.BoDeDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BoDe;
import DTO.LoaiBienBao;

public class ThemBoDeActivity extends AppCompatActivity {
    private EditText edtId, edtTen;
    private Button btnThem, btnHuy;
    private BoDeDAO boDeDAO;
    private  ImageView imgQL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_bo_de);

        Anhxa();

        boDeDAO = new BoDeDAO(this);


        // Thiết lập sự kiện cho nút "Thêm"
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                String ten = edtTen.getText().toString().trim();

                if (id.isEmpty() || ten.isEmpty()) {
                    Toast.makeText(ThemBoDeActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (boDeDAO.Kiemtramabode(id)) {
                    Toast.makeText(ThemBoDeActivity.this, "Mã bộ đề đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                BoDe boDe = new BoDe(id, ten);
                boDeDAO.ThemBode(boDe);

                Toast.makeText(ThemBoDeActivity.this, "Thêm bộ đề thành công", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });

        // Thiết lập sự kiện cho nút "Hủy"
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Anhxa() {
        edtId = findViewById(R.id.edtid);
        edtTen = findViewById(R.id.edtten);
        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);
        imgQL = findViewById(R.id.imageViewQL);
    }
}