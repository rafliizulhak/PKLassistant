package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class detail_tempat extends Activity {

    private TextView Nama, Alamat, Telp, Keterangan, Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tempat);

        Nama = (TextView) findViewById(R.id.etnama_perusahaan);
        Alamat = (TextView) findViewById(R.id.etalamat_perusahaan);
        Telp = (TextView) findViewById(R.id.ettelp_perusahaan);
        Keterangan = (TextView) findViewById(R.id.etketerangan);
        Link = (TextView) findViewById(R.id.linkMapsSiswa);

        final perusahaanSiswa perusahaan = (perusahaanSiswa) getIntent().getSerializableExtra("data");
        if (perusahaan != null) {
            Nama.setText(perusahaan.getNamaPerusahaan());
            Alamat.setText(perusahaan.getAlamatPerusahaan());
            Telp.setText(perusahaan.getTelpPerusahaan());
            Keterangan.setText(perusahaan.getKeterangan());
            Link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strUri = "" + perusahaan.getLinkMaps();
                    Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("https://www.google.com/maps/search/?api=1&query="+Uri.encode(strUri)));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            });
        }
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, detail_tempat.class);
    }
}
