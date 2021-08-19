package com.example.superandroidproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superandroidproject.dao.NguoiDungDao;
import com.example.superandroidproject.model.NguoiDungModel;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edtMatKhauCu, edtMatKhauMoi, edtMatKhauMoi2;
    Button btnDoiMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        edtMatKhauCu = findViewById(R.id.editTextOldPassword);
        edtMatKhauMoi= findViewById(R.id.editTextNewPassword);
        edtMatKhauMoi2 = findViewById(R.id.editTextReNewPassword);

        btnDoiMatKhau = findViewById(R.id.buttonChange);

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiMatKhau();
            }
        });

    }

    public void DoiMatKhau(){
        String passCu = edtMatKhauCu.getText().toString();
        String passMoi = edtMatKhauMoi.getText().toString();
        String passMoi2 = edtMatKhauMoi2.getText().toString();
        String username = LoginActivity.USER.getUserName();
        if (!NguoiDungDao.CheckPassCu(passCu)){
            Toast.makeText(this, "Mật khẩu cũ ko đúng", Toast.LENGTH_SHORT).show();
        } else if (passMoi.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!passMoi.equals(passMoi2)){
                Toast.makeText(this, "Mật khẩu mới ko trùng khớp", Toast.LENGTH_SHORT).show();
            } else {
                if (NguoiDungDao.ChangePassword(new NguoiDungModel(username, passMoi), DoiMatKhauActivity.this)){
//                    Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoiMatKhauActivity.this);
                    // Add the buttons
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(DoiMatKhauActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton("Về trang chủ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(DoiMatKhauActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
// 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("Đổi mật khẩu thành công. Bạn muốn đăng xuất?")
                            .setTitle("Đổi mật khẩu");

// 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(this, "Đã xảy ra sự cố. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}