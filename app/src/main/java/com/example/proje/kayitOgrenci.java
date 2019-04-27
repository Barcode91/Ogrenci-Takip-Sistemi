package com.example.proje;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class kayitOgrenci extends Fragment {

    SistemKayit sistemKayit ;
    Context context;
    Button btnOnay,btnKaydet;
    TextView txtSifreTekrar;
    EditText tcNo, adSoyad,passwd;
    Database database;
    boolean sifreDogruluk=false;
    Spinner ogrenciSinif;
    Ogrenci ogrenci = new Ogrenci();

    kayitOgrenci(Context contex){
        this.context=contex;

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.kayitogrenci, container,false);// Bu javanin gosterecegi xmli tanimladik.
        //View nesne tanımları
        btnKaydet=view.findViewById(R.id.btnKayitTamamla);
        txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
        tcNo = view.findViewById(R.id.tc_kimlikNo);
        adSoyad = view.findViewById(R.id.AdiSoyadi);
        passwd = view.findViewById(R.id.KayitSifre);
        ogrenciSinif = view.findViewById(R.id.OgrenciSinif);

        sistemKayit=new SistemKayit();
        btnKaydet.setOnClickListener(new View.OnClickListener() { // kaydet butonu işlevi
            @Override
            public void onClick(View v) {
                sifreDogruluk = sistemKayit.sifreDogrulukKontrol(passwd.getText().toString(),txtSifreTekrar.getText().toString());
                if(sifreDogruluk)
                    kullaniciEkle();
                else
                    Toast.makeText(context,"Şifreler Uyuşmuyor",Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }


    public void kullaniciEkle(){
        // Arayüz Bilgilerini nesne değişkine atar ve veri tabanı insert eder.
//        String ad = adSoyad.getText().toString();
//        String tCNo = tcNo.getText().toString();
//        String sinif = ogrenciSinif.getSelectedItem().toString();
//        String sifre = passwd.getText().toString();

        //System.out.println(ad+" "+tCNo+" "+sinif+" "+sifre );

        ogrenci.setAdSoyad(adSoyad.getText().toString());
        ogrenci.settCNo(tcNo.getText().toString());
        ogrenci.setPass(passwd.getText().toString());
        ogrenci.setClassNumber(ogrenciSinif.getSelectedItem().toString());
        database = new Database(ogrenci); // ogretmen nesnesini veritabanı constructer aracılığyla gönderilir
        database.userAdd(); // kullanıcı veritabanına eklenir.

    }



}
