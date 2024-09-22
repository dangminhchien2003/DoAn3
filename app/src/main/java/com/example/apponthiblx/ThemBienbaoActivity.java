package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

import DAO.BienBaoDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BienBao;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;

public class ThemBienbaoActivity extends AppCompatActivity {
    private Spinner spinnerLoaibienbao, spinnerAnh;
    private LoaiBienBaoDAO loaiBienBaoDAO;
    private EditText edtId,edtsobien, edtTen, edtMota;
    private Button btnThem, btnHuy;
    private BienBaoDAO bienBaoDAO;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_bienbao);

        Anhxa();

        loaiBienBaoDAO = new LoaiBienBaoDAO(this);

        loadLoaiBienBao();

        bienBaoDAO = new BienBaoDAO(this);

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
                img.setImageResource(imageResourceId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Thiết lập sự kiện cho nút "Thêm"
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy idloai từ Spinner
                LoaiBienBao selectedLoaiBienbao = (LoaiBienBao) spinnerLoaibienbao.getSelectedItem();
                String idloai = selectedLoaiBienbao.getId();
                String id = edtId.getText().toString().trim();
                String sobien = edtsobien.getText().toString().trim();
                String tenLoai = edtTen.getText().toString().trim();
                String moTa = edtMota.getText().toString().trim();
                String anh = spinnerAnh.getSelectedItem().toString();

                if (id.isEmpty() || idloai.isEmpty() || sobien.isEmpty() || tenLoai.isEmpty() || moTa.isEmpty()) {
                    Toast.makeText(ThemBienbaoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (bienBaoDAO.Kiemtramabienbao(id)) {
                    Toast.makeText(ThemBienbaoActivity.this, "Mã biển báo đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                BienBao bienBao = new BienBao(id,idloai,sobien, tenLoai, moTa, anh);
                bienBaoDAO.ThemBienBao(bienBao);

                Toast.makeText(ThemBienbaoActivity.this, "Thêm biển báo thành công", Toast.LENGTH_SHORT).show();

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

    private void Anhxa() {
        edtId = findViewById(R.id.edtid);
        edtsobien = findViewById(R.id.edtsobien);
        edtTen = findViewById(R.id.edtten);
        edtMota = findViewById(R.id.edtmota);
        spinnerAnh = findViewById(R.id.spinnerAnh);
        img = findViewById(R.id.imgLoaibienbao);
        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);
        spinnerLoaibienbao = findViewById(R.id.SpinerLoaibienbao);
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
    private void loadLoaiBienBao() {
        List<LoaiBienBao> loaiBienBaoList = loaiBienBaoDAO.getAlltenloai();
        if (loaiBienBaoList != null && !loaiBienBaoList.isEmpty()) {
            for (LoaiBienBao loaiBienBao : loaiBienBaoList) {
                Log.d("ThemBienbaoActivity", "LoaiBienBao: " + loaiBienBao.getTenloaiBB());
            }
            ArrayAdapter<LoaiBienBao> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiBienBaoList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLoaibienbao.setAdapter(adapter);
        } else {
            // Xử lý trường hợp danh sách rỗng
            Log.e("ThemBienbaoActivity", "Danh sách loại biển báo rỗng");
        }
    }
}
