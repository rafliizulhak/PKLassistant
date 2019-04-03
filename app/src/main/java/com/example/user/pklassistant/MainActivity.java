package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private DatabaseReference mDatabase;
    private RecyclerView rvViewSiswa;
    private RecyclerView.Adapter adapterSiswa;
    private RecyclerView.LayoutManager layoutManagerSiswa;
    private ArrayList<perusahaanSiswa> daftarTempat;
    ImageButton btnPengaturan, btnUser, btnHome, btnPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("rekomendasi_pkl");

        rvViewSiswa = (RecyclerView) findViewById(R.id.rv_main_siswa);
        rvViewSiswa.setHasFixedSize(true);
        layoutManagerSiswa = new LinearLayoutManager(this);
        rvViewSiswa.setLayoutManager(layoutManagerSiswa);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daftarTempat = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    perusahaanSiswa perusahaan = noteDataSnapshot.getValue(perusahaanSiswa.class);
                    perusahaan.setKey(noteDataSnapshot.getKey());
                    daftarTempat.add(perusahaan);
                }
                adapterSiswa = new perusahaanAdapter(daftarTempat, MainActivity.this);
                rvViewSiswa.setAdapter(adapterSiswa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

        btnPengaturan = (ImageButton) findViewById(R.id.btn_pengaturan);
        btnPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, pengaturan_siswa.class));
            }
        });

        btnUser = (ImageButton)findViewById(R.id.btn_user);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, profil_siswa.class));
            }
        });

        btnHome = (ImageButton)findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPesan = (ImageButton)findViewById(R.id.btn_pesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, pesan_siswa.class));
            }
        });
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, MainActivity.class);
    }
}
