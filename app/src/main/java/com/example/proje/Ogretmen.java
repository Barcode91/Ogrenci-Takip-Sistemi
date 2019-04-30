package com.example.proje;

public class Ogretmen {
    private String adSoyad;
    private String tCNo;
    private String pass;
    private String bolum;
    private String emailAdres;

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }

    private String onayKod;

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

    public String getBolum() {
        return bolum;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public String getOnayKod() {
        return onayKod;
    }

    public void setOnayKod(String onayKod) {
        this.onayKod = onayKod;
    }
}
