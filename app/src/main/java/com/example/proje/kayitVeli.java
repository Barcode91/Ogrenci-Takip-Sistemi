package com.example.proje;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class kayitVeli extends Fragment {
    Context context;
    SistemKayit sistemKayit ;
    TextView txtSifreTekrar;
    EditText tcNo, adSoyad,passwd, cocukTc,emailAdres;
    Button btnKaydet;
    Veli veli = new Veli();
    boolean sifreDogruluk=false;
    Database database;
    @SuppressLint("ValidFragment")
    public kayitVeli(Context context) {
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.kayitveli,container,false);
        btnKaydet=view.findViewById(R.id.btnVKayitTamamla);
        passwd=view.findViewById(R.id.KayitSifre);
        txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
        tcNo = view.findViewById(R.id.tc_kimlikNo);
        adSoyad = view.findViewById(R.id.AdiSoyadi);
        cocukTc = view.findViewById(R.id.ogrenci_tc_kimlikNo);
        emailAdres = view.findViewById(R.id.emailAdresi);
        sistemKayit=new SistemKayit();


        //EGER SIFREYI VE ONAY KODUNU DOGRU GIRDIYSE VERITABANINA KAYIT EDECEK BUTON!!!!!!
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sifreDogruluk=sistemKayit.sifreDogrulukKontrol(passwd.getText().toString(),txtSifreTekrar.getText().toString());

                if (sifreDogruluk)
                {
                    Toast.makeText(context,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                    kullaniciEkle();

                }
                else
                    Toast.makeText(context,"GIRILEN SIFRELER UYUSMUYOR",Toast.LENGTH_SHORT).show();

            }
        });


        return view;


    }

    public void kullaniciEkle(){
        // Arayüz Bilgilerini nesne değişkine atar ve veri tabanı insert eder.

        veli.setAdSoyad(adSoyad.getText().toString());
        veli.settCNo(tcNo.getText().toString());
        veli.setPass(passwd.getText().toString());
        veli.setCocukTc(cocukTc.getText().toString());
        veli.setEmailAdres(emailAdres.getText().toString());
        database = new Database(veli); // ogretmen nesnesini veritabanı constructer aracılığyla gönderilir
        database.userAdd(new SistemKayit(),context); // kullanıcı veritabanına eklenir.

    }





}
