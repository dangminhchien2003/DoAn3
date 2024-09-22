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

import java.util.ArrayList;
import java.util.List;

import DAO.LoaiBienBaoDAO;
import DAO.LoaiCauHoiDAO;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;

public class ThemLoaiBienbaoActivity extends AppCompatActivity {
    private EditText edtId, edtTen, edtMota;
    private Spinner spinnerAnh;
    private Button btnThem, btnHuy;
    private LoaiBienBaoDAO loaiBienBaoDAO;
    private ImageView imgLoaibienbao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_bienbao);

        Anhxa();

        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        List<String> imageList = getImageList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imageList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnh.setAdapter(adapter);

        // Thiết lập sự kiện cho Spinner
        spinnerAnh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy tên hình ảnh được chọn
                String selectedImage = parent.getItemAtPosition(position).toString();
                // Lấy id của hình ảnh từ tên
                int imageResourceId = getResources().getIdentifier(selectedImage, "drawable", getPackageName());
                // Cập nhật ImageView
                imgLoaibienbao.setImageResource(imageResourceId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Thiết lập sự kiện cho nút "Thêm"
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                String tenLoai = edtTen.getText().toString().trim();
                String moTa = edtMota.getText().toString().trim();
                String anh = spinnerAnh.getSelectedItem().toString();

                if (id.isEmpty() || tenLoai.isEmpty() || moTa.isEmpty()) {
                    Toast.makeText(ThemLoaiBienbaoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (loaiBienBaoDAO.Kiemtramaloaibienbao(id)) {
                    Toast.makeText(ThemLoaiBienbaoActivity.this, "Mã loại biển báo đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoaiBienBao loaiBienBao = new LoaiBienBao(id, tenLoai, moTa, anh);
                loaiBienBaoDAO.ThemLoaiBienBao(loaiBienBao);

                Toast.makeText(ThemLoaiBienbaoActivity.this, "Thêm loại biển báo thành công", Toast.LENGTH_SHORT).show();

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

        ImageView imgQL = findViewById(R.id.imageViewQL);
        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<String> getImageList() {
        List<String> imageList = new ArrayList<>();
        imageList.add("bien301a"); // Tên các hình ảnh trong thư mục drawable
        imageList.add("bien301b");
        imageList.add("bien301c");
        imageList.add("bien301d");
        imageList.add("bien301e");

        return imageList;
    }

    private void Anhxa() {
        edtId = findViewById(R.id.edtid);
        edtTen = findViewById(R.id.edtten);
        edtMota = findViewById(R.id.edtmota);
        spinnerAnh = findViewById(R.id.spinnerAnh);
        imgLoaibienbao = findViewById(R.id.imgLoaibienbao);
        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);
    }
}