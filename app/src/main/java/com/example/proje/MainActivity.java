package com.example.proje;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
Button btnKayitol,btnGiris;
CheckBox beniHatirla;
Context context;
TextView txtKullaniciAdi,txtKullaniciSifre;
PreferenceMekanizmasi preferenceMekanizmasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        preferencedanVeriCek();


        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference reference = db.getReference().child("kullanıcılar").child("mehmet");
                reference.setValue("hey adamım ");
                if(beniHatirla.isChecked()){
                    preferenceMekanizmasi.save(context,txtKullaniciAdi.getText().toString(),"KullaniciAdi");
                    preferenceMekanizmasi.save(context,txtKullaniciSifre.getText().toString(),"KullaniciSifre");
                }
                else
                    Toast.makeText(context,"KAYIT EDILMEDI",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, OgretmenActivity.class);
                startActivity(intent);
            }
        });


        btnKayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SistemKayit.class);
                startActivity(intent);
            }
        });


    }
    public void init(){
        context=this;
        btnGiris=findViewById(R.id.btnGiris);
        beniHatirla=findViewById(R.id.checkboxBeniHatirla);
        btnKayitol = findViewById(R.id.btnKayitOl);
        txtKullaniciAdi=findViewById(R.id.kullaniciTC);
        txtKullaniciSifre=findViewById(R.id.kullaniciSifre);
        preferenceMekanizmasi=new PreferenceMekanizmasi();
    }

    public void preferencedanVeriCek(){
        txtKullaniciAdi.setText(preferenceMekanizmasi.read(context,"KullaniciAdi"));
        txtKullaniciSifre.setText(preferenceMekanizmasi.read(context,"KullaniciSifre"));
    }




}
