package com.example.proje;

public class Degerlendirme {
    private String  ogretmenBolum;
    private String ogretmenKimlik;
    private String degerlendirme;

    public String getOgretmenBolum() {
        return ogretmenBolum;
    }

    public void setOgretmenBolum(String ogretmenBolum) {
        this.ogretmenBolum = ogretmenBolum;
    }

    public String getOgretmenKimlik() {
        return ogretmenKimlik;
    }

    public void setOgretmenKimlik(String ogretmenKimlik) {
        this.ogretmenKimlik = ogretmenKimlik;
    }

    public String getDegerlendirme() {
        return degerlendirme;
    }

    public void setDegerlendirme(String degerlendirme) {
        this.degerlendirme = degerlendirme;
    }

    @Override
    public String toString() {
        return "Degerlendirme{" +
                "ogretmenBolum='" + ogretmenBolum + '\'' +
                ", ogretmenKimlik='" + ogretmenKimlik + '\'' +
                ", degerlendirme='" + degerlendirme + '\'' +
                '}';
    }
}
