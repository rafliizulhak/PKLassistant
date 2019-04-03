package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.user.pklassistant.app.AppController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.user.pklassistant.SessionManager.key_email;
import static com.example.user.pklassistant.SessionManager.namauser;

public class profil_siswa extends AppCompatActivity {

    Button btn_form_pengajuan;
    ImageButton btn_user, btn_home, btn_pesan, btn_list_pengajuan;
    CircleImageView imageView;
    private TextView tv_nis, tv_nama, tv_kelas, tv_jurusan, tv_alamat, tv_telp;
    private String url = "http://api.schoolbooster.agyson.com/api/siswa/";
    private static final String TAG = profil_siswa.class.getSimpleName();
    String tag_json_obj = "json_obj_req";
    String username, namaLengkap, foto, jurusan, idKelas, namaKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_siswa);

        tv_nis = (TextView)findViewById(R.id.tv_nis);
        tv_nama = (TextView)findViewById(R.id.tv_nama);
        tv_kelas = (TextView)findViewById(R.id.tv_kelas);
        tv_jurusan = (TextView)findViewById(R.id.tv_jurusan);
        tv_alamat = (TextView)findViewById(R.id.tv_alamat);
        tv_telp = (TextView)findViewById(R.id.tv_no_telp);
        imageView = (CircleImageView)findViewById(R.id.profile_image);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        String nis = sessionManager.pref.getString(key_email,"");

        String urlGetProfil = url+nis+"?detail=true&ortu=true&kelas=true&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYXBpLnNjaG9vbGJvb3N0ZXIuYWd5c29uLmNvbS9hdXRoL2xvZ2luIiwiaWF0IjoxNTQ4NzcyNzg0LCJuYmYiOjE1NDg3NzI3ODQsImp0aSI6IjJObVE5NWluWFlYV2NCVzkiLCJzdWIiOjgwOSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.K-_sDGTy3LazOJVM4FBH4DD_rBtCavnszMPemeDnpYM";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetProfil, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONArray kelasArray = jObj.getJSONArray("kelas");
                    for (int i = 0; i < kelasArray.length(); i++) {
                        JSONObject kelasObject = kelasArray.getJSONObject(i);

                        idKelas = kelasObject.getString("id_kelas");
                        namaKelas = kelasObject.getString("nama_kelas");
                    }

                    JSONObject siswaObject = jObj.getJSONObject("siswa");

                    username = siswaObject.getString("nis");
                    namaLengkap = siswaObject.getString("nama_lengkap");
                    foto = siswaObject.getString("foto");
                    jurusan = siswaObject.getString("nama_keahlian");

                    Glide.with(profil_siswa.this)
                            .load(foto)
                            .into(imageView);

                    tv_nis.setText(username);
                    tv_nama.setText(namaLengkap);
                    tv_kelas.setText(namaKelas);
                    tv_jurusan.setText(jurusan);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login gagal: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        btn_user = (ImageButton)findViewById(R.id.btn_user);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profil_siswa.this, MainActivity.class));
            }
        });

        btn_pesan = (ImageButton)findViewById(R.id.btn_pesan);
        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profil_siswa.this, pesan_siswa.class));
            }
        });

        btn_list_pengajuan = (ImageButton)findViewById(R.id.btn_list_pengajuan);
        btn_list_pengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profil_siswa.this, list_pengajuan.class));
            }
        });

        tv_nis.setText(getIntent().getStringExtra("username"));
        tv_nama.setText(getIntent().getStringExtra("nama_lengkap"));
        tv_kelas.setText(getIntent().getStringExtra("nama_kelas"));

    }


}
