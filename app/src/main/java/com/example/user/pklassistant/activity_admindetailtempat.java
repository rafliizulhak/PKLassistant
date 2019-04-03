package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class activity_admindetailtempat extends AppCompatActivity {

    private Button btSubmit;
    private TextView etNama, etAlamat, etTelp, etKeterangan, etLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindetailtempat);

        etNama = (TextView) findViewById(R.id.et_nama_perusahaan);
        etAlamat = (TextView) findViewById(R.id.et_alamat_perusahaan);
        etTelp = (TextView) findViewById(R.id.et_telp_perusahaan);
        etKeterangan = (TextView) findViewById(R.id.et_keterangan);
        etLink = (TextView) findViewById(R.id.linkMaps);

        final Perusahaan perusahaan = (Perusahaan) getIntent().getSerializableExtra("data");
        if(perusahaan!=null){
            etNama.setText(perusahaan.getNamaPerusahaan());
            etAlamat.setText(perusahaan.getAlamatPerusahaan());
            etTelp.setText(perusahaan.getTelpPerusahaan());
            etKeterangan.setText(perusahaan.getKeterangan());
            etLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*String strUri = ""+perusahaan.getLinkMaps();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent );*/

                    String strUri = "https://www.google.com/maps/search/?api=1&query="+Uri.encode(perusahaan.getLinkMaps());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent );
                }
            });

        }
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, activity_admindetailtempat.class);
    }
}
