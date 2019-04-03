package com.example.user.pklassistant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Html;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class pengajuanAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<data> items;

    public pengajuanAdapter(Activity activity, List<data> item){
        this.activity = activity;
        this.items = item;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater)activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view == null)
                view = layoutInflater.inflate(R.layout.list_row, null);

            TextView id = (TextView)view.findViewById(R.id.id);
            TextView nama = (TextView)view.findViewById(R.id.nama);
            TextView alamat = (TextView)view.findViewById(R.id.alamat);
            TextView telp = (TextView)view.findViewById(R.id.telp);
            TextView periode = (TextView)view.findViewById(R.id.periode);
            TextView tahun = (TextView)view.findViewById(R.id.tahun);
            TextView status = (TextView)view.findViewById(R.id.status);
            TextView id_user = (TextView)view.findViewById(R.id.id_user);
            TextView created = (TextView)view.findViewById(R.id.created);

            data Data = items.get(position);

            id.setText(Data.getId());
            nama.setText(Data.getNama());
            alamat.setText(Data.getAlamat());
            telp.setText(Data.getTelp());
            periode.setText(Data.getPeriode());
            tahun.setText(Data.getTahun());
            status.setText(Data.getStatus());
            id_user.setText(Data.getId_user());
            created.setText(Data.getCreated());
        return view;
    }
}
