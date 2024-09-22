package com.example.apponthiblx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LogoActivity extends AppCompatActivity {

    private TextView textViewappName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        textViewappName = findViewById(R.id.textView7);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_text);
        textViewappName.setAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startActivity(new Intent(LogoActivity.this, DangNhapActivity.class));
                    finish();
                }catch (Exception e){

                }
            }
        };thread.start();
    }
}