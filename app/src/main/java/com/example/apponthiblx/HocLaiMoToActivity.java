package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HocLaiMoToActivity extends AppCompatActivity {
    private ImageView imgQL;
    private LinearLayout linearLayout12KN, linearLayout7PP, linearLayoutNN, linearLayoutLDXD,linearLayoutVX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_lai_mo_to);

        imgQL = findViewById(R.id.imageViewQL);
        linearLayout12KN = findViewById(R.id.linearLayout12KN);
        linearLayout7PP = findViewById(R.id.linearLayout7PP);
        linearLayoutNN = findViewById(R.id.linearLayoutNN);
        linearLayoutLDXD = findViewById(R.id.linearLayoutLDXD);
        linearLayoutVX = findViewById(R.id.linearLayoutVX);


        linearLayout12KN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLaiMoToActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "12_ky_nang_lai_xe_an_toan.pdf.pdf");
                intent.putExtra("textViewName", "12 Kỹ năng lái xe an toàn");
                startActivity(intent);
            }
        });

        linearLayout7PP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLaiMoToActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "Phuong_phap_tiet_kiem_xang_hieu_qua.pdf.pdf");
                intent.putExtra("textViewName", "7 phương pháp tiết kiệm xăng...");
                startActivity(intent);
            }
        });

        linearLayoutNN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLaiMoToActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "Ky_nang_di_xe_duong_ngap_nuoc.pdf.pdf");
                intent.putExtra("textViewName", "Kỹ năng đi xe khi đường ngập...");
                startActivity(intent);
            }
        });

        linearLayoutLDXD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLaiMoToActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "Ky_nang_lai_xe_lendoc_va_xuongdoc.pdf.pdf");
                intent.putExtra("textViewName", "Kỹ năng lái xe lên dốc và xuống...");
                startActivity(intent);
            }
        });
        linearLayoutVX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLaiMoToActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "Ky_nang_vuot_xe_dung_cach.pdf.pdf");
                intent.putExtra("textViewName", "Kỹ năng vượt xe đúng cách");
                startActivity(intent);
            }
        });

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}