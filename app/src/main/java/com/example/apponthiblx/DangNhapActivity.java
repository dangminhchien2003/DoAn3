package com.example.apponthiblx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangNhapActivity extends AppCompatActivity {

    private TextView textViewDK;
    private Button btnLogin;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        textViewDK = findViewById(R.id.textViewDK);
        btnLogin = findViewById(R.id.buttonLogin);
        editTextEmail = findViewById(R.id.editTextTextPersonName);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        textViewDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DangNhapActivity.this, "Nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(DangNhapActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                //phương thức để đăng nhập bằng email và password
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //kiểm tra đang nhập có thành công hay không
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                checkUserRole(user.getUid());
                            }
                        } else {
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void checkUserRole(String uid) {
        //chỉ định một tham chiếu đến một nút cụ thể trong cơ sở dữ liệu Firebase.
        databaseReference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //kiểm tra xem việc truy xuất dữ liệu từ Firebase có thành công hay không
                if (task.isSuccessful()) {

                    DataSnapshot dataSnapshot = task.getResult();

                    //kiểm tra xem dữ liệu có tồn tại hay không
                    if (dataSnapshot.exists()) {
                        //Lấy giá trị của trường "role" trong dữ liệu người dùng và lưu vào biến role.
                        String role = dataSnapshot.child("role").getValue(String.class);

                        //hiển thị vai trò người dùng
                        Toast.makeText(getApplicationContext(),""+ role, Toast.LENGTH_SHORT).show();
                        if ("admin".equals(role)) {

                            // Chuyển hướng tới trang admin
                            Intent adminIntent = new Intent(DangNhapActivity.this, QuanTriActivity.class);
                            startActivity(adminIntent);
                            finish();
                        } else {
                            // Chuyển hướng tới trang user
                            Intent userIntent = new Intent(DangNhapActivity.this, MainActivity.class);
                            startActivity(userIntent);
                            finish();
                        }
                    } else {
                        Toast.makeText(DangNhapActivity.this, "Tài khoản không tồn tại trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DangNhapActivity.this, "Lỗi khi truy xuất dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
