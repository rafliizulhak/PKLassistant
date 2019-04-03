package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class perusahaanAdapter extends RecyclerView.Adapter<perusahaanAdapter.ViewHolder>{
    private ArrayList<perusahaanSiswa> daftarTempat;
    private Context context;

    public perusahaanAdapter(ArrayList<perusahaanSiswa> daftarTempatPerusahaan, Context ctx){
        daftarTempat = daftarTempatPerusahaan;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title_siswa, tv_content_siswa;

        ViewHolder(View v){
            super(v);
            tv_title_siswa = (TextView)v.findViewById(R.id.tv_perusahaan_siswa);
            tv_content_siswa = (TextView)v.findViewById(R.id.tv_content_siswa);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perusahaan_siswa, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String titleSiswa = daftarTempat.get(position).getNamaPerusahaan();
        final String contentSiswa = daftarTempat.get(position).getAlamatPerusahaan();
        System.out.println("BARANG DATA one by one "+position+daftarTempat.size());
        holder.tv_title_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(detail_tempat.getActIntent((Activity) context).putExtra("data", daftarTempat.get(position)));
            }
        });
        holder.tv_title_siswa.setText(titleSiswa);
        holder.tv_content_siswa.setText(contentSiswa);
    }

    @Override
    public int getItemCount() {
        return daftarTempat.size();
    }
}
