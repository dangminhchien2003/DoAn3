package com.example.apponthiblx;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFActivity extends AppCompatActivity {

    private ImageView imgQL,imageViewShare;
    private TextView textViewTen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity);

        imgQL = findViewById(R.id.imageViewQL);
        textViewTen = findViewById(R.id.textViewTen);
        imageViewShare = findViewById(R.id.imageViewShare);
        PDFView pdfView = findViewById(R.id.pdfView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("pdfFileName")) {
            String pdfFileName = intent.getStringExtra("pdfFileName");
            pdfView.fromAsset(pdfFileName).load();
            Log.d("PDFActivity", "Attempting to load PDF file");
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

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check this app\n" + "https://play.google.com/store/apps/details?id=com.vietauto.onthigiaypheplaixe.gplx.xemay.a1");
                intent.setType("text/plain");

                if (intent.resolveActivity(PDFActivity.this.getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Share this app"));
                }
            }
        });


    }
}