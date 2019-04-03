package com.example.user.pklassistant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.PriorityQueue;

public class admin_createdata extends AppCompatActivity {

    private DatabaseReference database, dataref;

    private Button btSubmit;
    private EditText etNama, etAlamat, etTelp, etKeterangan, etlinkMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_createdata);

        etNama = (EditText)findViewById(R.id.et_nama_perusahaan);
        etAlamat = (EditText)findViewById(R.id.et_alamat_perusahaan);
        etTelp = (EditText)findViewById(R.id.et_telp_perusahaan);
        etKeterangan = (EditText)findViewById(R.id.et_keterangan);
        etlinkMaps = (EditText)findViewById(R.id.linkMaps);
        btSubmit = (Button)findViewById(R.id.bt_submit);

        database = FirebaseDatabase.getInstance().getReference();
        dataref = database.child("rekomendasi_pkl");

        final Perusahaan perusahaan = (Perusahaan)getIntent().getSerializableExtra("data");

        if (perusahaan != null){
            etNama.setText(perusahaan.getNamaPerusahaan());
            etAlamat.setText(perusahaan.getAlamatPerusahaan());
            etTelp.setText(perusahaan.getTelpPerusahaan());
            etKeterangan.setText(perusahaan.getKeterangan());
            etlinkMaps.setText(perusahaan.getLinkMaps());

            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    perusahaan.setNamaPerusahaan(etNama.getText().toString());
                    perusahaan.setAlamatPerusahaan(etAlamat.getText().toString());
                    perusahaan.setTelpPerusahaan(etTelp.getText().toString());
                    perusahaan.setKeterangan(etKeterangan.getText().toString());
                    perusahaan.setLinkMaps(etlinkMaps.getText().toString());

                    updatePerusahaan(perusahaan);
                }
            });
        }else {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Empty(etNama.getText().toString()) && !Empty(etAlamat.getText().toString()) && !Empty(etTelp.getText().toString()) && !Empty(etKeterangan.getText().toString()) && !Empty(etlinkMaps.getText().toString()))
                        submitPerusahaan(new Perusahaan(etNama.getText().toString(), etAlamat.getText().toString(), etTelp.getText().toString(), etKeterangan.getText().toString(), etlinkMaps.getText().toString()));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etNama.getWindowToken(), 0);
                }
            });
        }

    }

    private boolean Empty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updatePerusahaan(Perusahaan perusahaan) {
        dataref.child(perusahaan.getKey())
                .setValue(perusahaan)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diubah", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }

    private void submitPerusahaan(Perusahaan perusahaan){
        dataref.push().setValue(perusahaan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNama.setText("");
                etAlamat.setText("");
                etTelp.setText("");
                etKeterangan.setText("");
                etlinkMaps.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data Berhasil Ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, admin_createdata.class);
    }

}
