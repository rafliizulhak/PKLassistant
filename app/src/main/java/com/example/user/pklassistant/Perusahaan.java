package com.example.user.pklassistant;

import com.google.firebase.database.Exclude;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Perusahaan implements Serializable {
    private String namaPerusahaan, alamatPerusahaan, telpPerusahaan, keterangan, linkMaps, key;

    public Perusahaan() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getAlamatPerusahaan() {
        return alamatPerusahaan;
    }

    public void setAlamatPerusahaan(String alamatPerusahaan) {
        this.alamatPerusahaan = alamatPerusahaan;
    }

    public String getTelpPerusahaan() {
        return telpPerusahaan;
    }

    public void setTelpPerusahaan(String telpPerusahaan) {
        this.telpPerusahaan = telpPerusahaan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan(){
        return keterangan;
    }

    public String getLinkMaps(){
        return linkMaps;
    }

    public void setLinkMaps(String linkMaps) {
        this.linkMaps = linkMaps;
    }

    @Override
    public String toString() {
        return " "+namaPerusahaan+"\n" +
                " "+alamatPerusahaan+"\n" +
                " "+telpPerusahaan+"\n" +
                " "+keterangan+"\n" +
                " "+linkMaps;
    }

    public Perusahaan( String nama, String alamat, String telp, String ket, String maps){
    namaPerusahaan = nama;
    alamatPerusahaan = alamat;
    telpPerusahaan = telp;
    keterangan = ket;
    linkMaps = maps;
}
}
