package com.example.proje.com.example.proje.tanımliclasslar;

import android.media.Image;

import java.io.Serializable;

public class Veli implements Serializable {
    private String adSoyad;
    private String tCNo;
    private String pass;
    private String cocukTc;
    private String emailAdres;
    final public String kulTip="veli";

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String gettCNo() {
        return tCNo;
    }

    public void settCNo(String tCNo) {
        this.tCNo = tCNo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCocukTc() {
        return cocukTc;
    }

    public void setCocukTc(String cocukTc) {
        this.cocukTc = cocukTc;
    }
}
