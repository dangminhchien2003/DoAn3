package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HocLuatActivity extends AppCompatActivity {
    private ImageView imgQL;
    private LinearLayout linearLayoutLGTDB, linearLayoutMXP, linearLayoutND, linearLayoutQD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_luat);

        imgQL = findViewById(R.id.imageViewQL);
        linearLayoutLGTDB = findViewById(R.id.linearLayoutLGTDB);
        linearLayoutMXP = findViewById(R.id.linearLayoutMXP);
        linearLayoutND = findViewById(R.id.linearLayoutND);
        linearLayoutQD = findViewById(R.id.linearLayoutQD);

        linearLayoutLGTDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLuatActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "luatGTDB.pdf");
                intent.putExtra("textViewName", "Luật GTDB 2008");
                startActivity(intent);
            }
        });

        linearLayoutMXP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLuatActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "mucxuphat.pdf");
                intent.putExtra("textViewName", "Mức xử phạt theo Nghị định 100");
                startActivity(intent);
            }
        });

        linearLayoutND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLuatActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "nghidinh.pdf");
                intent.putExtra("textViewName", "Nghị định 100 về Luật GTDB");
                startActivity(intent);
            }
        });

        linearLayoutQD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocLuatActivity.this, PDFActivity.class);
                intent.putExtra("pdfFileName", "qd5bgtvt.pdf");
                intent.putExtra("textViewName", "Quy định về tốc độ xe cơ giới 2007");
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