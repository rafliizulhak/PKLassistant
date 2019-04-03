package com.example.user.pklassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;
import static com.example.user.pklassistant.login.TAG_ID;
import static com.example.user.pklassistant.login.TAG_USERNAME;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String key_email = "keyemail";
    public static final String setlevel = "level";
    public static final String namauser = "nama_user";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession(String email){
        editor.putBoolean(is_login, true);
        editor.putString(key_email, email);
        editor.commit();
    }

    public void createSessionLevel(String level){
        editor.putString(setlevel, level);
        editor.commit();
    }

    public void createSessionUser(String nama_user){
        editor.putString(namauser, nama_user);
        editor.commit();
    }

    public void getNama(String user){
        pref.getString(namauser, null);
    }

    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            if (pref.getString(setlevel, null).equals("2")){
                Intent i = new Intent(context, admin_main.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(key_email, pref.getString(key_email, null));
        return user;
    }
}
