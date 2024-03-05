package com.example.lab1_2_ph34723.SignInEmail;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab1_2_ph34723.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Email extends AppCompatActivity {

    TextInputEditText txtUser, txtPass;
    Button btnDangKy;
    TextView txtLogin;

    FirebaseAuth mAuth;
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        anhxa();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = txtUser.getText().toString();
                password = txtPass.getText().toString();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(Register_Email.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()) {
                    Toast.makeText(Register_Email.this, "Chưa nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(Register_Email.this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(Register_Email.this, "Mật khẩu phải từ 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.matches("^(?=.*[a-zA-Z]).*$")) {
                    Toast.makeText(Register_Email.this, "Mật khẩu phải có ít nhất 1 chữ cái", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.matches("^(?=.*[0-9]).*$")) {
                    Toast.makeText(Register_Email.this, "Mật khẩu phải có ít nhất 1 số", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnDangKy.setEnabled(false);
                btnDangKy.setText("Đang xử lý...");
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register_Email.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login_Email.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Register_Email.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                }

                                btnDangKy.setEnabled(true);
                                btnDangKy.setText("Register");
                            }
                        });

            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Email.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void anhxa() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnDangKy = findViewById(R.id.btnDangKy);
        txtLogin = findViewById(R.id.txtLogin);
    }
}