package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.PhotoAdapter;
import DTO.Photo;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayoutHL, linearLayoutBB, linearLayoutHLMT, linearLayoutCH, linearLayoutBD, linearLayoutSH;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer mtimer;
    private PopupWindow popupWindow;
    private Button btntieptheo;
    private int nextButtonClickCount = 0;
    private ImageView arrowImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        linearLayoutHL = findViewById(R.id.LinearHocluat);
        linearLayoutBB = findViewById(R.id.LinearBB);
        linearLayoutHLMT = findViewById(R.id.LinearHLMT);
        linearLayoutCH = findViewById(R.id.LinearCH);
        linearLayoutBD = findViewById(R.id.LinearBDA1);
        linearLayoutSH = findViewById(R.id.linearSH);

        mListPhoto = getlistPhoto();

        photoAdapter = new PhotoAdapter(this, mListPhoto);
        viewPager.setAdapter(photoAdapter);

        autoSlideImgage();

        ImageView imageViewtrogiup = findViewById(R.id.imageViewtrogiup);

        // Khởi tạo PopupWindow
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.item_giaithich, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;

        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Định nghĩa màu xám nhạt với độ trong suốt
        int grayColor = Color.parseColor("#CCCCCCCC");

        // Tạo một ColorDrawable với màu xám nhạt
        ColorDrawable colorDrawable = new ColorDrawable(grayColor);

        // Gán ColorDrawable làm nền cho PopupWindow
        popupWindow.setBackgroundDrawable(colorDrawable);

        popupWindow.setOutsideTouchable(true);

        // ImageView cho mũi tên
        arrowImageView = popupView.findViewById(R.id.arrowImageView);

        imageViewtrogiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });


        linearLayoutHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HocLuatActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoaiBienBaoActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutHLMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HocLaiMoToActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoaiCauHoiActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoDeActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuytrinhPdfActivity.class);
                intent.putExtra("pdfFileName", "quytrinhsahinh.pdf");
                intent.putExtra("textViewName", "Quy Trình");
                startActivity(intent);
            }
        });
    }

    private List<Photo> getlistPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add((new Photo(R.drawable.image1)));
        list.add((new Photo(R.drawable.image2)));
        list.add((new Photo(R.drawable.image3)));
        list.add((new Photo(R.drawable.image4)));
        list.add((new Photo(R.drawable.image5)));

        return list;
    }
    private void autoSlideImgage(){
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }

        //Init Timer
        if (mtimer == null){
            mtimer = new Timer();
        }

        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mtimer != null){
            mtimer.cancel();
            mtimer = null;
        }
    }
}