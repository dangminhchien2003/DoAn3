package com.example.apponthiblx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class QuanTriActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout loaicauhoi,cauhoi,loaibienbao,bienbao,dethi;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView img_mucluc;
    private NavigationView navigationView;
    private Toolbar toolbar; // Thêm Toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_tri);

        Anhxa();

        // Thiết lập Toolbar thay vì ActionBar
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        // Đặt lắng nghe cho sự kiện khi người dùng chọn một mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(this);


        // Set up click listener for img_mucluc
        img_mucluc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the drawer when img_mucluc is clicked
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        loaicauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanTriActivity.this, QLLoaiCauHoiActivity.class);
                startActivity(intent);
            }
        });

        cauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanTriActivity.this, QLCauHoiActivity.class);
                startActivity(intent);
            }
        });

        loaibienbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanTriActivity.this, QLLoaiBienBaoActivity.class);
                startActivity(intent);
            }
        });

        bienbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanTriActivity.this, QLBienBaoActivity.class);
                startActivity(intent);
            }
        });

        dethi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanTriActivity.this, QLDeThiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        loaicauhoi = findViewById(R.id.Loaicauhoi);
        cauhoi = findViewById(R.id.Cauhoi);
        loaibienbao = findViewById(R.id.Loaibienbao);
        bienbao = findViewById(R.id.Bienbao);
        dethi = findViewById(R.id.Dethi);
        drawerLayout = findViewById(R.id.drawer_layout);
        img_mucluc = findViewById(R.id.img_mucluc);

        navigationView = findViewById(R.id.nav_view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {

        } else if (itemId == R.id.nav_settings) {

        } else if (itemId == R.id.nav_share) {


        } else if (itemId == R.id.nav_logout) {

            Toast.makeText(QuanTriActivity.this, "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            intent = new Intent(QuanTriActivity.this, DangNhapActivity.class);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}
