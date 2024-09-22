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

import DAO.LoaiBienBaoDAO;
import DAO.LoaiCauHoiDAO;

public class SuaLoaiBienBaoActivity extends AppCompatActivity {
    private EditText editTextTenLoai, editTextMoTa;
    private ImageView imageViewLoai,imgQL;
    private Spinner spinnerloaibienbao;
    private Button buttonCapnhat;
    private TextView textviewMaloai;
    private LoaiBienBaoDAO loaiBienBaoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_loai_bien_bao);

        Anhxa();


        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        Intent intent = getIntent();
        String maLoai = intent.getStringExtra("MA_LOAI");
        String tenLoai = intent.getStringExtra("TEN_LOAI");
        String moTa = intent.getStringExtra("MO_TA");
        String anh = intent.getStringExtra("ANH");

        textviewMaloai.setText(maLoai);
        editTextTenLoai.setText(tenLoai);
        editTextMoTa.setText(moTa);

        // Hiển thị hình ảnh
        Drawable drawable = getResources().getDrawable(
                getResources().getIdentifier(anh, "drawable", getPackageName()));
        imageViewLoai.setImageDrawable(drawable);

        buttonCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ các EditText
                String tenLoaiMoi = editTextTenLoai.getText().toString();
                String moTaMoi = editTextMoTa.getText().toString();
                String maLoai = textviewMaloai.getText().toString();


                if (TextUtils.isEmpty(tenLoaiMoi) || TextUtils.isEmpty(moTaMoi)) {
                    Toast.makeText(SuaLoaiBienBaoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Thực hiện cập nhật dữ liệu
                boolean isSuccess = loaiBienBaoDAO.SuaLoaibienbao(maLoai, tenLoaiMoi, moTaMoi, anh);
                if (isSuccess) {
                    Toast.makeText(SuaLoaiBienBaoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    // Gửi kết quả về cho Activity gọi SuaLoaiCauhoiActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED", true); // Bạn có thể thêm bất kỳ dữ liệu nào bạn muốn gửi trở lại
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                } else {
                    Toast.makeText(SuaLoaiBienBaoActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
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
        textviewMaloai = findViewById(R.id.textviewMaloai);
        editTextTenLoai = findViewById(R.id.edtten);
        editTextMoTa = findViewById(R.id.edtmota);
        imageViewLoai = findViewById(R.id.imgLoaibienbao);
        buttonCapnhat = findViewById(R.id.btnCapnhat);
        imgQL = findViewById(R.id.imageViewQL);
        spinnerloaibienbao = findViewById(R.id.SpinerLoaibienbao);
    }
}