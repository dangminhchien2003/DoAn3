package com.example.apponthiblx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import DAO.DeThiDAO;
import DTO.CauHoi;

public class DethiActivity extends AppCompatActivity {
    private ImageView imgQL, imageViewCH;
    private CheckBox cbA, cbB, cbC, cbD;
    private Button btnTieptheo;
    private DeThiDAO deThiDAO;
    private ArrayList<CauHoi> CauHoiList;
    private int CauHoiSize;
    private boolean answered;
    private int CauHoiCounter;
    private CauHoi currentCauHoi;
    private TextView textviewCauHoi, textViewSocau, textViewchamdiem, textViewthoigian;
    private int correctAnswers; // Theo dõi số câu trả lời đúng
    private ArrayList<CauHoi> wrongQuestions = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60000; // 10 phút
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dethi);

        imgQL = findViewById(R.id.imageViewQL);
        imageViewCH = findViewById(R.id.imageViewCH);
        textviewCauHoi = findViewById(R.id.textView9);
        textViewSocau = findViewById(R.id.textViewcau);
        textViewthoigian = findViewById(R.id.textViewthoigian);
        textViewchamdiem = findViewById(R.id.textViewchamdiem);
        cbA = findViewById(R.id.checkBoxA);
        cbB = findViewById(R.id.checkBoxB);
        cbC = findViewById(R.id.checkBoxC);
        cbD = findViewById(R.id.checkBoxD);
        btnTieptheo = findViewById(R.id.buttonTiepTheo);

        TextView textView = findViewById(R.id.textViewDethi);

        deThiDAO = new DeThiDAO(this);
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String idde = null;
        if (intent != null) {
            String tende = intent.getStringExtra("ten_de");
            idde = intent.getStringExtra("id_de");

            // Gán tên loại câu hỏi vào TextView
            textView.setText(tende);
        }
        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DethiActivity.this)
                        .setTitle("Nộp bài")
                        .setMessage("Bạn có chắc chắn muốn nộp bài không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishQuestion();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DethiActivity.super.onBackPressed();
                            }
                        })
                        .setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        textViewchamdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DethiActivity.this)
                        .setMessage("Bạn muốn kết thúc bài thi?")
                        .setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishQuestion();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        // Lấy danh sách câu hỏi từ CSDL
        CauHoiList = deThiDAO.getCauHoiByLoaiDe(idde);
        CauHoiSize = CauHoiList.size();
        //Xáo trộn câu
        Collections.shuffle(CauHoiList);

        correctAnswers = 0; // Khởi tạo số câu trả lời đúng
        showNextQuestion();

        btnTieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (cbA.isChecked() || cbB.isChecked() || cbC.isChecked() || cbD.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(DethiActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });

        // Khởi tạo và bắt đầu CountDownTimer
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //cập nhật giá trị của biến timeLeftInMillis với thời gian còn lại
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText(textViewthoigian);
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText(textViewthoigian);

                // Hiển thị thông báo hết thời gian làm bài
                new AlertDialog.Builder(DethiActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Đã hết thời gian làm bài!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Kết thúc bài kiểm tra khi người dùng nhấn OK
                                finishQuestion();
                            }
                        })
                        .setCancelable(false) // Người dùng phải nhấn OK để đóng thông báo
                        .show();
            }
        }.start();

    }

    private void showNextQuestion() {
        cbA.setChecked(false);
        cbB.setChecked(false);
        cbC.setChecked(false);
        cbD.setChecked(false);

        cbA.setTextColor(Color.BLACK);
        cbB.setTextColor(Color.BLACK);
        cbC.setTextColor(Color.BLACK);
        cbD.setTextColor(Color.BLACK);

        //kiểm tra xem số lượng câu hỏi đã được hiển thị
        if (CauHoiCounter < CauHoiSize) {
            //lấy ra câu hỏi tiếp theo từ danh sách
            currentCauHoi = CauHoiList.get(CauHoiCounter);

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
            //set vi tri cau hoi hiện tại
            textViewSocau.setText("Câu: " + CauHoiCounter);

            //đánh dấu rằng câu hỏi hiện tại chưa được trả lời.
            answered = false;
            btnTieptheo.setText("Xác nhận");
        } else {
            finishQuestion();
            Toast.makeText(DethiActivity.this, "Đã hoàn thành câu hỏi", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishQuestion() {
        countDownTimer.cancel();
        calculateScore();
    }

    private void calculateScore() {
        Intent resultIntent = new Intent(this, KetQuaActivity.class);

        //tính số câu sai
        int wrongAnswers = CauHoiSize - correctAnswers;

        resultIntent.putExtra("correctAnswers", correctAnswers);
        //resultIntent.putExtra("wrongAnswers", wrongAnswers);

        //tổng số câu hỏi.
        resultIntent.putExtra("totalQuestions", CauHoiSize);

        //danh sách các câu hỏi sai.
        resultIntent.putParcelableArrayListExtra("wrongQuestions", wrongQuestions);

        startActivity(resultIntent);
        finish();
    }


    public void onClickConfirm(View view) {
        if (isAnswerSelected()) {
            checkAnswer();
            finishQuestion();
        } else {
            Toast.makeText(DethiActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAnswerSelected() {
        return cbA.isChecked() || cbB.isChecked() || cbC.isChecked() || cbD.isChecked();
    }

    private void checkAnswer() {
        String correctAnswer = currentCauHoi.getDapandung();
        String selectedAnswer = "";

        if (cbA.isChecked()) {
            selectedAnswer = "A";
        } else if (cbB.isChecked()) {
            selectedAnswer = "B";
        } else if (cbC.isChecked()) {
            selectedAnswer = "C";
        } else if (cbD.isChecked()) {
            selectedAnswer = "D";
        }

        //kiểm tra đáp án có đúng k bằng cách so sánh
        if (selectedAnswer.equals(correctAnswer)) {
//            Toast.makeText(DethiActivity.this, "Đáp án đúng!", Toast.LENGTH_SHORT).show();
            correctAnswers++; // Tăng số câu trả lời đúng
        } else {
//            Toast.makeText(DethiActivity.this, "Đáp án sai!", Toast.LENGTH_SHORT).show();
            wrongQuestions.add(currentCauHoi); // Lưu câu hỏi sai vào danh sách
        }

        if (CauHoiCounter < CauHoiSize) {
            btnTieptheo.setText("Câu tiếp");
        } else {
            btnTieptheo.setText("Hoàn thành");
        }

        answered = true;
    }

    private void updateCountDownText(TextView textViewthoigian) {
        //Tính số phút
        int minutes = (int) (timeLeftInMillis / 1000) / 60;

        //Tính số giây
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);

        textViewthoigian.setText(timeFormatted);
    }
}
