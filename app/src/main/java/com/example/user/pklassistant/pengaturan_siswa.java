package com.example.user.pklassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;

public class pengaturan_siswa extends AppCompatActivity {

    ImageButton btnKembali;
    Button btnGanti, btnKeluar, btnBantuan;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_siswa);

        sessionManager = new SessionManager(getApplicationContext());

        btnKembali = (ImageButton) findViewById(R.id.btn_kembali);
        btnKeluar = (Button) findViewById(R.id.btn_keluar);
        btnBantuan = (Button) findViewById(R.id.btn_bantuan);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pengaturan_siswa.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                finish();
            }
        });

        btnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pengaturan_siswa.this, bantuan_siswa.class);
                startActivity(intent);
            }
        });

    }
}
