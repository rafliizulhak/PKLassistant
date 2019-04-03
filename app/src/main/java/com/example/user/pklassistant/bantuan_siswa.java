package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class bantuan_siswa extends Activity {

    Button btnBantuan1, btnBantuan2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan_siswa);

        btnBantuan1 = (Button)findViewById(R.id.btn_bantuan_1);
        btnBantuan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bantuan_siswa.this, bantuan_siswa_1.class));
            }
        });
    }
}
