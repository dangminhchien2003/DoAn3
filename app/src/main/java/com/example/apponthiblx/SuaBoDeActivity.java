package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import DAO.BoDeDAO;
import DAO.LoaiBienBaoDAO;

public class SuaBoDeActivity extends AppCompatActivity {
    private EditText editTextTen;
    private ImageView imgQL;
    private Button buttonCapnhat,btnHuy;
    private TextView textviewMa;
    private BoDeDAO boDeDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_bo_de);

        Anhxa();


        boDeDAO = new BoDeDAO(this);

        Intent intent = getIntent();
        String ma = intent.getStringExtra("MA");
        String ten = intent.getStringExtra("TEN");


        textviewMa.setText(ma);
        editTextTen.setText(ten);


        buttonCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ các EditText
                String tenLoaiMoi = editTextTen.getText().toString();
                String maLoai = textviewMa.getText().toString();


                if (TextUtils.isEmpty(tenLoaiMoi)) {
                    Toast.makeText(SuaBoDeActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Thực hiện cập nhật dữ liệu
                boolean isSuccess = boDeDAO.SuaBode(maLoai, tenLoaiMoi);
                if (isSuccess) {
                    Toast.makeText(SuaBoDeActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    // Gửi kết quả về cho Activity gọi SuaLoaiCauhoiActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED", true); // Bạn có thể thêm bất kỳ dữ liệu nào bạn muốn gửi trở lại
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                } else {
                    Toast.makeText(SuaBoDeActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        textviewMa = findViewById(R.id.textviewma);
        editTextTen = findViewById(R.id.edtten);
        buttonCapnhat = findViewById(R.id.buttonCapnhat);
        btnHuy = findViewById(R.id.btnHuy);
        imgQL = findViewById(R.id.imageViewQL);
    }
}