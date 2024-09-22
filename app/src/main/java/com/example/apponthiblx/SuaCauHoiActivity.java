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
import DAO.CauHoiDAO;
import DAO.LoaiBienBaoDAO;
import DAO.LoaiCauHoiDAO;
import DTO.LoaiCauHoi;

public class SuaCauHoiActivity extends AppCompatActivity {
    private EditText edtCau, edtNoidung, edtDapanA, edtDapanB, edtDapanC, edtDapanD, edtDapanDung, edtGiaithich, edtDe;
    private Spinner spinnerLoaiCauhoi;
    private ImageView imageView, imgQL;
    private Button buttonCapNhat;
    private TextView textViewMa;
    private CauHoiDAO cauHoiDAO;
    private LoaiCauHoiDAO loaiCauHoiDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_cau_hoi);

        Anhxa();

        loaiCauHoiDAO = new LoaiCauHoiDAO(this);

        loadLoaiCauhoi();

        cauHoiDAO = new CauHoiDAO(this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String ma = intent.getStringExtra("MA");
        String maloai = intent.getStringExtra("MA_LOAI");
        String cau = intent.getStringExtra("CAU");
        String noidung = intent.getStringExtra("NOI_DUNG");
        String dapAnA = intent.getStringExtra("DAP_AN_A");
        String dapAnB = intent.getStringExtra("DAP_AN_B");
        String dapAnC = intent.getStringExtra("DAP_AN_C");
        String dapAnD = intent.getStringExtra("DAP_AN_D");
        String dapAnDung = intent.getStringExtra("DAP_AN_DUNG");
        String giaiThich = intent.getStringExtra("GIAI_THICH");
        String id_de = intent.getStringExtra("ID_DE");
        String anh = intent.getStringExtra("ANH");

        // Hiển thị dữ liệu lên giao diện
        textViewMa.setText(ma);
        edtCau.setText(cau);
        edtNoidung.setText(noidung);
        edtDapanA.setText(dapAnA);
        edtDapanB.setText(dapAnB);
        edtDapanC.setText(dapAnC);
        edtDapanD.setText(dapAnD);
        edtDapanDung.setText(dapAnDung);
        edtGiaithich.setText(giaiThich);
        edtDe.setText(id_de);

        // Thiết lập lựa chọn đúng cho Spinner
        setSpinnerSelection(spinnerLoaiCauhoi, maloai);

        // Hiển thị hình ảnh
        int resourceId = getResources().getIdentifier(anh, "drawable", getPackageName());
        if (resourceId != 0) {
            Drawable drawable = getResources().getDrawable(resourceId);
            imageView.setImageDrawable(drawable);
        }

        // Xử lý sự kiện khi nhấn nút Cập nhật
        buttonCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy idloai từ Spinner
                LoaiCauHoi selectedLoaiCauHoi = (LoaiCauHoi) spinnerLoaiCauhoi.getSelectedItem();
                String idloai = selectedLoaiCauHoi.getId();

                String id = textViewMa.getText().toString().trim();
                String cau = edtCau.getText().toString().trim();
                String noidung = edtNoidung.getText().toString().trim();
                String DapanA = edtDapanA.getText().toString().trim();
                String DapanB = edtDapanB.getText().toString().trim();
                String DapanC = edtDapanC.getText().toString().trim();
                String DapanD = edtDapanD.getText().toString().trim();
                String DapanDung = edtDapanDung.getText().toString().trim();
                String Giaithich = edtGiaithich.getText().toString().trim();
                String Made = edtDe.getText().toString().trim();

                if (Giaithich.isEmpty()) {
                    Giaithich = null;
                }

                if (id.isEmpty() || idloai.isEmpty() || cau.isEmpty() || noidung.isEmpty() || DapanA.isEmpty() || DapanB.isEmpty() || DapanC.isEmpty() || DapanD.isEmpty() || DapanDung.isEmpty() || Made.isEmpty()) {
                    Toast.makeText(SuaCauHoiActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện cập nhật dữ liệu
                boolean isSuccess = cauHoiDAO.Suacauhoi(id, idloai, cau, noidung, DapanA, DapanB, DapanC, DapanD, DapanDung, Giaithich, Made, anh);
                if (isSuccess) {
                    Toast.makeText(SuaCauHoiActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    // Gửi kết quả về cho Activity gọi SuaBienBaoActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED", true); // Bạn có thể thêm bất kỳ dữ liệu nào bạn muốn gửi trở lại
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(SuaCauHoiActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
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

    private void loadLoaiCauhoi() {
        List<LoaiCauHoi> loaiCauHoiList = loaiCauHoiDAO.getAlltenloai();
        if (loaiCauHoiList != null && !loaiCauHoiList.isEmpty()) {
            for (LoaiCauHoi loaiCauHoi : loaiCauHoiList) {
                Log.d("ThemCauHoiActivity", "Loaicauhoi: " + loaiCauHoi.getTenloai());
            }
            ArrayAdapter<LoaiCauHoi> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiCauHoiList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLoaiCauhoi.setAdapter(adapter);
        } else {
            // Xử lý trường hợp danh sách rỗng
            Log.e("ThemCauHoiActivity", "Danh sách loại cau hoi rỗng");
        }
    }
    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            if (spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                return;
            }
        }
    }
    private void Anhxa() {
        textViewMa = findViewById(R.id.textviewMa);
        edtCau = findViewById(R.id.edtcau);
        edtNoidung = findViewById(R.id.edtnoidung);
        edtDapanA = findViewById(R.id.edtDapAnA);
        edtDapanB = findViewById(R.id.edtDapAnB);
        edtDapanC = findViewById(R.id.edtDapAnC);
        edtDapanD = findViewById(R.id.edtDapAnD);
        edtDapanDung = findViewById(R.id.edtDapAnDung);
        edtGiaithich = findViewById(R.id.edtGiaithich);
        edtDe = findViewById(R.id.edtMade);
        spinnerLoaiCauhoi = findViewById(R.id.SpinerLoaicauhoi);
        imageView = findViewById(R.id.img);
        buttonCapNhat = findViewById(R.id.btnCapnhat);
        imgQL = findViewById(R.id.imageViewQL);
    }
}