package com.example.user.pklassistant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PerusahaanViewHolder extends RecyclerView.Adapter<PerusahaanViewHolder.ViewHolder> {

    private ArrayList<Perusahaan> daftarPerusahaan;
    private Context context;
    FirebaseDataListener listener;

    public PerusahaanViewHolder(ArrayList<Perusahaan> perusahaan, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarPerusahaan = perusahaan;
        context = ctx;
        listener = (admin_crud_pkl)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvContent;
        ViewHolder(View v){
            super(v);
            tvTitle = (TextView)v.findViewById(R.id.tv_perusahaan);
            tvContent = (TextView)v.findViewById(R.id.tv_perusahaan_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_perusahaan, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String name = daftarPerusahaan.get(position).getNamaPerusahaan();
        final String content = daftarPerusahaan.get(position).getAlamatPerusahaan();
        System.out.println("BARANG DATA one by one "+position+daftarPerusahaan.size());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(activity_admindetailtempat.getActIntent((Activity) context).putExtra("data", daftarPerusahaan.get(position)));
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(admin_createdata.getActIntent((Activity) context).putExtra("data", daftarPerusahaan.get(position)));
                            }
                        }
                );

                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                listener.onDeleteData(daftarPerusahaan.get(position), position);
                            }
                        }
                );

                return true;
            }
        });
        holder.tvTitle.setText(name);
        holder.tvContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return daftarPerusahaan.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Perusahaan perusahaan, int position);
    }
}