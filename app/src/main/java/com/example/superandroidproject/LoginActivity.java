package com.example.superandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superandroidproject.dao.NguoiDungDao;
import com.example.superandroidproject.model.NguoiDungModel;

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    EditText edtTaiKhoan,edtNhapMatKhau;
    CheckBox chkNhoMatKhau;
    public static NguoiDungModel USER;
    NguoiDungDao nguoiDungDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNhapMatKhau = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        edtTaiKhoan = findViewById(R.id.editTextUsername);
        chkNhoMatKhau = findViewById(R.id.checkboxRemember);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtTaiKhoan.getText().toString();
                String password = edtNhapMatKhau.getText().toString();
                NguoiDungModel nguoiDung = new NguoiDungModel(userName, password);
                if (!checkValidation()){
                    Toast.makeText(LoginActivity.this, "blah blah", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (NguoiDungDao.checkNguoiDung(LoginActivity.this, userName, password)) {
                        rememberUser(userName, password, chkNhoMatKhau.isChecked());
                        USER = nguoiDung;
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void rememberUser(String username, String password, boolean status){
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            // TODO: XÓA TRẠNG THÁI LƯU TRƯỚC ĐÓ
            editor.clear();
        } else {
            // TODO: GHI DỮ LIỆU
            editor.putString("USERNAME", username);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REMEMBER", status);
        }
        // SAVE DATA
        editor.apply();
    }
    public boolean checkValidation(){
        return !edtTaiKhoan.getText().toString().equals("") || !edtNhapMatKhau.getText().toString().equals("");
    }
}