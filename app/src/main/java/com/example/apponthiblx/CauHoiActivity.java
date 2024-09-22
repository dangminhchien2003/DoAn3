package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import DAO.CauHoiDAO;
import DTO.CauHoi;

public class CauHoiActivity extends AppCompatActivity {

    private ImageView imgQL,imageViewCH,imageViewDungSai;
    private LinearLayout linearGiaithich,linaerDapAn;
    private TextView textViewtenCH, textviewCauHoi,textViewSoCau,textViewDungSai,textViewDung,textViewDaChon;
    private CheckBox cbA, cbB, cbC, cbD;
    private Button btnTieptheo;
    private CauHoiDAO cauHoiDAO;
    private ArrayList<CauHoi> CauHoiList;
    private int CauHoiSize;
    private boolean answerred;
    private int CauHoiCounter;
    private CauHoi currentCauHoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hoi);

        imgQL = findViewById(R.id.imageViewQL);
        imageViewCH = findViewById(R.id.imageViewCH);

        textViewtenCH = findViewById(R.id.textViewTenloaiCH);
        textViewSoCau = findViewById(R.id.textViewSoCau);
        textviewCauHoi = findViewById(R.id.textView9);
        textViewDungSai = findViewById(R.id.textViewDungSai);
        textViewDung = findViewById(R.id.textViewDung);
        textViewDaChon = findViewById(R.id.textViewDaChon);
        imageViewDungSai = findViewById(R.id.imageViewDungSai);
        cbA = findViewById(R.id.checkBoxA);
        cbB = findViewById(R.id.checkBoxB);
        cbC = findViewById(R.id.checkBoxC);
        cbD = findViewById(R.id.checkBoxD);
        linearGiaithich = findViewById(R.id.LinearGiaiThich);
        linaerDapAn = findViewById(R.id.linearLayoutDapAn);
        btnTieptheo = findViewById(R.id.buttonTiepTheo);

        cauHoiDAO = new CauHoiDAO(this);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String idLoaiCauhoi = null;
        if (intent != null) {
            String tenLoaiCauHoi = intent.getStringExtra("ten_loai");
            idLoaiCauhoi = intent.getStringExtra("id_loai");

            // Gán tên loại câu hỏi vào TextView
            textViewtenCH.setText(tenLoaiCauHoi);
        }

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Lấy danh sách câu hỏi từ CSDL
        CauHoiList = cauHoiDAO.getCauHoiByLoai(idLoaiCauhoi);
        CauHoiSize = CauHoiList.size();

        //xáo trộn câu
        Collections.shuffle(CauHoiList);

        // Hiển thị câu hỏi đầu tiên
        showNextQuestion();

        btnTieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu chưa trả lời câu hỏi
                if (!answerred) {
                    // nếu chọn 1 trong 4 đáp án
                    if (cbA.isChecked() || cbB.isChecked() || cbC.isChecked() || cbD.isChecked()) {
                        // kiểm tra đáp án và hiển thị giải thích
                        checkAnswer();
                    } else {
                        Toast.makeText(CauHoiActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
                    }
                }
                // nếu đã trả lời, thực hiện chuyển câu
                else {
                    showNextQuestion();
                }
            }
        });
    }

    //Hien thi cau hoi
    private void showNextQuestion() {
        // Xóa chọn các CheckBox
        cbA.setChecked(false);
        cbB.setChecked(false);
        cbC.setChecked(false);
        cbD.setChecked(false);

        // Ẩn giải thích và đặt lại màu chữ
        linearGiaithich.setVisibility(View.GONE);
        linaerDapAn.setVisibility(View.GONE);
        textViewDungSai.setVisibility(View.GONE);
        textViewDung.setVisibility(View.GONE);
        textViewDaChon.setVisibility(View.GONE);
        cbA.setTextColor(Color.BLACK);
        cbB.setTextColor(Color.BLACK);
        cbC.setTextColor(Color.BLACK);
        cbD.setTextColor(Color.BLACK);

        //kiểm tra xem còn câu hỏi không
        if (CauHoiCounter < CauHoiSize) {

            //Lấy câu hỏi hiện tại từ danh sách câu hỏi CauHoiList dựa trên biến đếm CauHoiCounter
            currentCauHoi = CauHoiList.get(CauHoiCounter);

            textViewSoCau.setText(currentCauHoi.getCau());
            textviewCauHoi.setText(currentCauHoi.getNoidung());
            cbA.setText(currentCauHoi.getDapanA());
            cbB.setText(currentCauHoi.getDapanB());
            cbC.setText(currentCauHoi.getDapanC());
            cbD.setText(currentCauHoi.getDapanD());

            // Load hình ảnh từ drawable nếu có
            if (currentCauHoi.getAnh() != null && !currentCauHoi.getAnh().isEmpty()) {
                int imageResId = getResources().getIdentifier(currentCauHoi.getAnh(), "drawable", getPackageName());
                if (imageResId != 0) {
                    imageViewCH.setImageResource(imageResId);
                    imageViewCH.setVisibility(View.VISIBLE); // Hiển thị ImageView nếu có hình ảnh
                }
            } else {
                imageViewCH.setVisibility(View.GONE); // Ẩn ImageView nếu không có hình ảnh
            }

            CauHoiCounter++;

            // Đánh dấu rằng câu hỏi chưa được trả lời
            answerred = false;
            btnTieptheo.setText("Xác nhận");
        } else {
            // Nếu đã hiển thị hết câu hỏi, xử lý kết quả hoặc hiển thị thông báo
            finishQuestion();
            Toast.makeText(CauHoiActivity.this, "Đã hoàn thành câu hỏi", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishQuestion(){
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }
    public void onClickConfirm(View view) {
        if (isAnswerSelected()) {
            checkAnswer();
        } else {
            Toast.makeText(CauHoiActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAnswerSelected() {
        return cbA.isChecked() || cbB.isChecked() || cbC.isChecked() || cbD.isChecked();
    }

    private void checkAnswer() {
        // Lấy đáp án đúng của câu hiện tại
        String correctAnswer = currentCauHoi.getDapandung();

        // Kiểm tra xem đáp án được chọn có đúng không
        //xác định đáp án ng chọn có đúng không
        boolean isCorrect = false;

        //lưu trữ đáp án người dùng chọn
        String selectedAnswer = "";
        if (cbA.isChecked()) {
            selectedAnswer = "A";
            if (correctAnswer.equals("A")) isCorrect = true;
        } else if (cbB.isChecked()) {
            selectedAnswer = "B";
            if (correctAnswer.equals("B")) isCorrect = true;
        } else if (cbC.isChecked()) {
            selectedAnswer = "C";
            if (correctAnswer.equals("C")) isCorrect = true;
        } else if (cbD.isChecked()) {
            selectedAnswer = "D";
            if (correctAnswer.equals("D")) isCorrect = true;
        }

        // Hiển thị giải thích và đáp án
        linearGiaithich.setVisibility(View.VISIBLE);
        linaerDapAn.setVisibility(View.VISIBLE);
        textViewDungSai.setVisibility(View.VISIBLE);
        textViewDung.setVisibility(View.VISIBLE);
        textViewDaChon.setVisibility(View.VISIBLE);

        // Thiết lập TextView "Đúng/Sai"
        if (isCorrect) {
            textViewDungSai.setText("Đúng");
            textViewDungSai.setTextColor(Color.GREEN);
            imageViewDungSai.setImageResource(R.drawable.v);
        } else {
            textViewDungSai.setText("Sai");
            textViewDungSai.setTextColor(Color.RED);
            imageViewDungSai.setImageResource(R.drawable.x);

            // Đặt màu chữ cho đáp án sai
            switch (selectedAnswer) {
                case "A":
                    cbA.setTextColor(Color.RED);
                    break;
                case "B":
                    cbB.setTextColor(Color.RED);
                    break;
                case "C":
                    cbC.setTextColor(Color.RED);
                    break;
                case "D":
                    cbD.setTextColor(Color.RED);
                    break;
            }
        }

        // Thiết lập TextView "Đáp án đúng" và "Đáp án đã chọn"
        textViewDung.setText("Đáp án đúng: " + correctAnswer);
        textViewDaChon.setText("Đáp án đã chọn: " + selectedAnswer);

        TextView textViewGiaiThich = findViewById(R.id.textViewGiaiThich);
        textViewGiaiThich.setText(currentCauHoi.getGiaithich());

        // Đặt màu chữ cho đáp án đúng
        switch (correctAnswer) {
            case "A":
                cbA.setTextColor(Color.GREEN);
                break;
            case "B":
                cbB.setTextColor(Color.GREEN);
                break;
            case "C":
                cbC.setTextColor(Color.GREEN);
                break;
            case "D":
                cbD.setTextColor(Color.GREEN);
                break;
        }

        // Đánh dấu rằng câu hỏi đã được trả lời
        answerred = true;
        btnTieptheo.setText("Câu tiếp");
    }

    private void showSolution(String correctAnswer) {
        // Hiển thị giải thích
        linearGiaithich.setVisibility(View.VISIBLE);
        TextView textViewGiaiThich = findViewById(R.id.textViewGiaiThich);
        textViewGiaiThich.setText(currentCauHoi.getGiaithich());

        // Đặt màu chữ cho tất cả các CheckBox thành màu đỏ
        cbA.setTextColor(Color.RED);
        cbB.setTextColor(Color.RED);
        cbC.setTextColor(Color.RED);
        cbD.setTextColor(Color.RED);

        // Đặt màu chữ cho đáp án đúng thành màu xanh lá cây
        switch (correctAnswer) {
            case "A":
                cbA.setTextColor(Color.GREEN);
                break;
            case "B":
                cbB.setTextColor(Color.GREEN);
                break;
            case "C":
                cbC.setTextColor(Color.GREEN);
                break;
            case "D":
                cbD.setTextColor(Color.GREEN);
                break;
        }

        // Hiển thị LinearLayout chứa đáp án đúng
        linaerDapAn.setVisibility(View.VISIBLE);

        // Nếu còn câu hỏi để trả lời, đặt lại nút tiếp theo thành "Câu tiếp"
        if (CauHoiCounter < CauHoiSize) {
            btnTieptheo.setText("Câu tiếp");
        } else {
            // Nếu không còn câu hỏi, đặt lại nút tiếp theo thành "Hoàn thành"
            btnTieptheo.setText("Hoàn thành");
        }
    }

}
