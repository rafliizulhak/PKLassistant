package com.example.user.pklassistant;

public class item_pesan_siswa {
    private String text;
    private String nama;
    private String photoUrl;

    public item_pesan_siswa() {
    }

    public item_pesan_siswa(String text, String nama, String photoUrl) {
        this.text = text;
        this.nama = nama;
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return nama;
    }

    public void setName(String nama) {
        this.nama = nama;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
