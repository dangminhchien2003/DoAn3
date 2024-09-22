package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class QuytrinhPdfActivity extends AppCompatActivity {

    private ImageView imgQL;
    private TextView textViewTen, textViewMeo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quytrinh);

        imgQL = findViewById(R.id.imageViewQL);
        textViewTen = findViewById(R.id.textViewTen);
        textViewMeo = findViewById(R.id.textViewMeo);

        PDFView pdfView = findViewById(R.id.pdfView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("pdfFileName")) {
            String pdfFileName = intent.getStringExtra("pdfFileName");
            pdfView.fromAsset(pdfFileName).load();
        }

        if (intent != null && intent.hasExtra("textViewName")) {
            String textViewName = intent.getStringExtra("textViewName");
            textViewTen.setText(textViewName);
        }

        imgQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewMeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuytrinhPdfActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "kinh_nghiem_hoc_va_thi_lai_xe.pdf.pdf");
                intent.putExtra("textViewName", "Kinh nghiệm học và thi lái xe");
                startActivity(intent);
            }
        });
    }
}