package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DAO.BienBaoDAO;
import DAO.LoaiBienBaoDAO;
import DTO.LoaiBienBao;

public class SuaBienBaoActivity extends AppCompatActivity {
    private EditText editTextSoBien, editTextTen, editTextMoTa;
    private Spinner spinnerLoaiBienBao;
    private ImageView imageViewBienBao, imgQL;
    private Button buttonCapNhat;
    private String maBienBao;
    private TextView textViewMa;
    private BienBaoDAO bienBaoDAO;
    private LoaiBienBaoDAO loaiBienBaoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_bien_bao);

        // Ánh xạ các view
        textViewMa = findViewById(R.id.textviewMa);
        editTextSoBien = findViewById(R.id.edtsobien);
        editTextTen = findViewById(R.id.edtten);
        editTextMoTa = findViewById(R.id.edtmota);
        spinnerLoaiBienBao = findViewById(R.id.SpinerLoaibienbao);
        imageViewBienBao = findViewById(R.id.img);
        buttonCapNhat = findViewById(R.id.btnCapnhat);
        imgQL = findViewById(R.id.imageViewQL);

        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        loadLoaiBienBao();

        bienBaoDAO = new BienBaoDAO(this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String maBienBao = intent.getStringExtra("MA");
        String maloai = intent.getStringExtra("MALOAI");
        String soBien = intent.getStringExtra("SO_BIEN");
        String tenBien = intent.getStringExtra("TEN");
        String moTa = intent.getStringExtra("MO_TA");
        String anh = intent.getStringExtra("ANH");

        // Hiển thị dữ liệu lên giao diện
        textViewMa.setText(maBienBao);
        editTextSoBien.setText(soBien);
        editTextTen.setText(tenBien);
        editTextMoTa.setText(moTa);

        // Hiển thị hình ảnh
        int resourceId = getResources().getIdentifier(anh, "drawable", getPackageName());
        if (resourceId != 0) {
            Drawable drawable = getResources().getDrawable(resourceId);
            imageViewBienBao.setImageDrawable(drawable);
        }

        // Xử lý sự kiện khi nhấn nút Cập nhật
        buttonCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ các EditText
                // Lấy dữ liệu từ Spinner
                String maloai = spinnerLoaiBienBao.getSelectedItem().toString();
                String sobien = editTextSoBien.getText().toString();
                String ten = editTextTen.getText().toString();
                String moTa = editTextMoTa.getText().toString();
                String ma = textViewMa.getText().toString();

                if (TextUtils.isEmpty(sobien) || TextUtils.isEmpty(ten) || TextUtils.isEmpty(moTa) || TextUtils.isEmpty(maloai)) {
                    Toast.makeText(SuaBienBaoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện cập nhật dữ liệu
                boolean isSuccess = bienBaoDAO.Suabienbao(ma, maloai, sobien, ten, moTa, anh);
                if (isSuccess) {
                    Toast.makeText(SuaBienBaoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    // Gửi kết quả về cho Activity gọi SuaBienBaoActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED", true); // Bạn có thể thêm bất kỳ dữ liệu nào bạn muốn gửi trở lại
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(SuaBienBaoActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Xử lý sự kiện khi nhấn nút Quay lại
        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadLoaiBienBao() {
        List<LoaiBienBao> loaiBienBaoList = loaiBienBaoDAO.getAlltenloai();
        if (loaiBienBaoList != null && !loaiBienBaoList.isEmpty()) {
            for (LoaiBienBao loaiBienBao : loaiBienBaoList) {
                Log.d("ThemBienbaoActivity", "LoaiBienBao: " + loaiBienBao.getTenloaiBB());
            }
            ArrayAdapter<LoaiBienBao> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiBienBaoList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLoaiBienBao.setAdapter(adapter);
        } else {
            // Xử lý trường hợp danh sách rỗng
            Log.e("ThemBienbaoActivity", "Danh sách biển báo rỗng");
        }
    }
}
