package com.example.user.pklassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class pengaturan_administrator extends AppCompatActivity {

    ImageButton btnKembali;
    Button btnGanti, btnKeluar;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_administrator);

        sessionManager = new SessionManager(getApplicationContext());

        btnKembali = (ImageButton) findViewById(R.id.btn_kembali);
        btnKeluar = (Button) findViewById(R.id.btn_keluar);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pengaturan_administrator.this, admin_main.class);
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
    }
}
