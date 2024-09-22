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
import DAO.CauHoiDAO;
import DAO.LoaiBienBaoDAO;
import DAO.LoaiCauHoiDAO;
import DTO.BienBao;
import DTO.CauHoi;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;

public class ThemCauHoiActivity extends AppCompatActivity {
    private Spinner spinnerLoaicauhoi, spinnerDe, spinnerAnh;
    private LoaiCauHoiDAO loaiCauHoiDAO;
    private EditText edtId, edtCau, edtNoidung, edtDapanA, edtDapanB, edtDapanC, edtDapanD, edtDapanDung, edtGiaithich, edtDe;
    private Button btnThem, btnHuy;
    private CauHoiDAO cauHoiDAO;
    private ImageView img;
    private List<LoaiCauHoi> loaiCauHoiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_cau_hoi);

        Anhxa();

        loaiCauHoiDAO = new LoaiCauHoiDAO(this);

        loadLoaicauhoi();

        cauHoiDAO = new CauHoiDAO(this);



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
                if (!selectedImage.isEmpty()) {
                    int imageResourceId = getResources().getIdentifier(selectedImage, "drawable", getPackageName());
                    // Cập nhật ImageView
                    img.setImageResource(imageResourceId);
                } else {
                    // Nếu không có hình ảnh nào được chọn, xóa hình ảnh trong ImageView
                    img.setImageResource(0);
                }
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
                LoaiCauHoi selectedLoaiCauHoi = (LoaiCauHoi) spinnerLoaicauhoi.getSelectedItem();
                String idloai = selectedLoaiCauHoi.getId();

                String id = edtId.getText().toString().trim();
                String cau = edtCau.getText().toString().trim();
                String noidung = edtNoidung.getText().toString().trim();
                String DapanA = edtDapanA.getText().toString().trim();
                String DapanB = edtDapanB.getText().toString().trim();
                String DapanC = edtDapanC.getText().toString().trim();
                String DapanD = edtDapanD.getText().toString().trim();
                String DapanDung = edtDapanDung.getText().toString().trim();
                String Giaithich = edtGiaithich.getText().toString().trim();
                String Made = edtDe.getText().toString().trim();

                String anh = spinnerAnh.getSelectedItem().toString();
                if (anh.isEmpty()) {
                    anh = null;
                }

                if (Giaithich.isEmpty()) {
                    Giaithich = null;
                }

                if (id.isEmpty() || idloai.isEmpty() || cau.isEmpty() || noidung.isEmpty() || DapanA.isEmpty() || DapanB.isEmpty() || DapanC.isEmpty() || DapanD.isEmpty() || DapanDung.isEmpty() || Made.isEmpty()) {
                    Toast.makeText(ThemCauHoiActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cauHoiDAO.Kiemtramacauhoi(id)) {
                    Toast.makeText(ThemCauHoiActivity.this, "Mã câu hỏi đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                CauHoi cauHoi = new CauHoi(id, idloai, cau, noidung, DapanA, DapanB, DapanC, DapanD, DapanDung, Giaithich, Made, anh);
                cauHoiDAO.ThemCauHoi(cauHoi);

                Toast.makeText(ThemCauHoiActivity.this, "Thêm câu hỏi thành công", Toast.LENGTH_SHORT).show();

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
        imageList.add("");
        imageList.add("bien301a");
        imageList.add("bien301b");
        imageList.add("bien301c");
        imageList.add("bien301d");
        imageList.add("bien301e");

        return imageList;
    }

    private void loadLoaicauhoi() {
        List<LoaiCauHoi> loaiCauHoiList = loaiCauHoiDAO.getAlltenloai();
        if (loaiCauHoiList != null && !loaiCauHoiList.isEmpty()) {
            for (LoaiCauHoi loaiCauHoi : loaiCauHoiList) {
                Log.d("ThemCauHoiActivity", "Loaicauhoi: " + loaiCauHoi.getTenloai());
            }
            ArrayAdapter<LoaiCauHoi> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiCauHoiList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLoaicauhoi.setAdapter(adapter);
        } else {
            // Xử lý trường hợp danh sách rỗng
            Log.e("ThemCauHoiActivity", "Danh sách loại cau hoi rỗng");
        }
    }

    private void Anhxa() {
        edtId = findViewById(R.id.edtid);
        edtCau = findViewById(R.id.edtcau);
        edtNoidung = findViewById(R.id.edtnoidung);
        edtDapanA = findViewById(R.id.edtDapAnA);
        edtDapanB = findViewById(R.id.edtDapAnB);
        edtDapanC = findViewById(R.id.edtDapAnC);
        edtDapanD = findViewById(R.id.edtDapAnD);
        edtDapanDung = findViewById(R.id.edtDapAnDung);
        edtGiaithich = findViewById(R.id.edtGiaithich);
        edtDe = findViewById(R.id.edtMade);
        spinnerAnh = findViewById(R.id.spinnerAnh);
        img = findViewById(R.id.img);
        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);
        spinnerLoaicauhoi = findViewById(R.id.SpinerLoaicauhoi);
    }
}
