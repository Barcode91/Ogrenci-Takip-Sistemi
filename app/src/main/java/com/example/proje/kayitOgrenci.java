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
import com.google.firebase.auth.FirebaseAuth;

public class kayitOgrenci extends Fragment {

    SistemKayit sistemKayit ;
    Context context;
    Button btnOnay,btnKaydet;
    TextView txtSifreTekrar;
    EditText tcNo, adSoyad,passwd, emailAdres;
    Database database;
    boolean sifreDogruluk=false;
    Spinner ogrenciSinif;
    Ogrenci ogrenci = new Ogrenci();
    FirebaseAuth myAuth;

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
        emailAdres = view.findViewById(R.id.emailAdresi);
        myAuth=FirebaseAuth.getInstance();
        sistemKayit=new SistemKayit();
        btnKaydet.setOnClickListener(new View.OnClickListener() { // kaydet butonu işlevi
            @Override
            public void onClick(View v) {
                sifreDogruluk = sistemKayit.sifreDogrulukKontrol(passwd.getText().toString(),txtSifreTekrar.getText().toString());
                if(sifreDogruluk) {
                    kullaniciEkle();
                    Toast.makeText(context,"Kayıt Basarılı",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context,"Şifreler Uyuşmuyor",Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }


    private void kullaniciEkle(){
        ogrenci.setAdSoyad(adSoyad.getText().toString());
        ogrenci.settCNo(tcNo.getText().toString());
        ogrenci.setPass(passwd.getText().toString());
        ogrenci.setClassNumber(ogrenciSinif.getSelectedItem().toString());
        ogrenci.setEmailAdres(emailAdres.getText().toString());
        database = new Database(ogrenci); // ogretmen nesnesini veritabanı constructer aracılığyla gönderilir
        database.userAdd(new SistemKayit(),context);

    }



}
