package com.example.apponthiblx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DTO.TaiKhoan;

public class DangKiActivity extends AppCompatActivity {

    private TextView textViewDN;
    private Button btnSignUp;
    private EditText editTextName, editTextEmail, editTextPassword, editTextRePassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        textViewDN = findViewById(R.id.textViewDN);
        btnSignUp = findViewById(R.id.buttonDK);
        editTextName = findViewById(R.id.editTextTextName);
        editTextEmail = findViewById(R.id.editTextTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextRePassword = findViewById(R.id.editTextTextRePassword);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        textViewDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKiActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, password, repassword;
                name = String.valueOf(editTextName.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                repassword = String.valueOf(editTextRePassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DangKiActivity.this, "Nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(DangKiActivity.this, "Nhập email hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(DangKiActivity.this, "Nhập tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(DangKiActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(DangKiActivity.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    Toast.makeText(DangKiActivity.this, "Nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(repassword)) {
                    Toast.makeText(DangKiActivity.this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();
                                TaiKhoan taiKhoan = new TaiKhoan(name, email, password, "user"); // Mặc định đăng ký là "user"
                                databaseReference.child(uid).setValue(taiKhoan).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(DangKiActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DangKiActivity.this, DangNhapActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(DangKiActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(DangKiActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


}
