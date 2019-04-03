package com.example.user.pklassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.pklassistant.app.AppController;
import com.example.user.pklassistant.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.user.pklassistant.SessionManager.key_email;

public class list_pengajuan extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    SwipeRefreshLayout swipeRefreshLayout;
    List<data> itemList = new ArrayList<data>();
    pengajuanAdapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    String id, nama, alamat, telp, periode, tahun, status, id_user, created;

    private static final String TAG = list_pengajuan.class.getSimpleName();

    private static String URL_SELECT = Server.URL + "select.php";

    private String username;

    public static final String TAG_ID = "id_pengajuan";
    public static final String TAG_PERUSAHAAN = "nama_perusahaan";
    public static final String TAG_ALAMAT = "alamat_perusahaan";
    public static final String TAG_TELP = "no_telp_perusahaan";
    public static final String TAG_PERIODE = "periode";
    public static final String TAG_TAHUN = "tahun_ajaran";
    public static final String TAG_STATUS = "status";
    public static final String TAG_ID_USER = "id_user";
    public static final String TAG_CREATED = "created";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGES = "messages";
    SessionManager sessionManager;

    String TAG_JSON_OBJ = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pengajuan);

        sessionManager = new SessionManager(getApplicationContext());

        username = sessionManager.pref.getString(key_email, "");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        list = (ListView) findViewById(R.id.list);

        adapter = new pengajuanAdapter(list_pengajuan.this, itemList);
        list.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley();
            }
        });

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(URL_SELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        data item = new data();

                        item.setId(obj.getString(TAG_ID));
                        item.setNama(obj.getString(TAG_PERUSAHAAN));
                        item.setAlamat(obj.getString(TAG_ALAMAT));
                        item.setTelp(obj.getString(TAG_TELP));
                        item.setPeriode(obj.getString(TAG_PERIODE));
                        item.setTahun(obj.getString(TAG_TAHUN));
                        item.setStatus(obj.getString(TAG_STATUS));
                        item.setId_user(obj.getString(TAG_ID_USER));
                        item.setCreated(obj.getString(TAG_CREATED));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void sendUser(){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id_user", username);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_SELECT, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
