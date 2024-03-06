package com.example.lab1_2_ph34723.SignInEmail;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.lab1_2_ph34723.MainActivity;
import com.example.lab1_2_ph34723.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Email extends AppCompatActivity {
    TextInputEditText txtUser, txtPass;
    Button btndangnhap;
    TextView txtSignUp, txtResetPass;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            txtUser.setText(currentUser.getEmail());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhxa();
        mAuth = FirebaseAuth.getInstance();

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUser.getText().toString();
                String password = txtPass.getText().toString();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(Login_Email.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()) {
                    Toast.makeText(Login_Email.this, "Chưa nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(Login_Email.this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                btndangnhap.setEnabled(false);
                btndangnhap.setText("Đang xử lý...");

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login_Email.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login_Email.this, "Tài khoản hoặc mật khẩu sai.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                btndangnhap.setEnabled(true);
                                btndangnhap.setText("Login");
                            }
                        });
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register_Email.class);
                startActivity(intent);
                finish();
            }
        });

        txtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUser.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(Login_Email.this, "Vui lòng nhập email của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login_Email.this, "Vui lòng kiểm tra hộp thư email của bạn", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login_Email.this, "Lỗi gửi email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void anhxa() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btndangnhap = findViewById(R.id.btndangnhap);
        txtSignUp = findViewById(R.id.txtSignUp);
        txtResetPass = findViewById(R.id.txtResetPass);

    }
}