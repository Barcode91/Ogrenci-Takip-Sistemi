package com.example.proje;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
Button btnKayitol,btnGiris;
CheckBox beniHatirla;
Context context;
TextView txtKullaniciAdi,txtKullaniciSifre;
PreferenceMekanizmasi preferenceMekanizmasi;
FirebaseAuth mAuth;
FirebaseUser firebaseUser;
FirebaseDatabase db;
DatabaseReference myRef;
private String email, passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        preferencedanVeriCek();
        //oturum açıkmı kapalımı kontrol edilir.
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        if (firebaseUser!= null)
            Toast.makeText(context,"oturum açık devam et",Toast.LENGTH_SHORT).show();

        btnGiris.setOnClickListener(new View.OnClickListener() { // GİRİŞ BUTONU AKSİYONU
            @Override
            public void onClick(View v) { //login işlemi
                email = txtKullaniciAdi.getText().toString();
                passwd = txtKullaniciSifre.getText().toString();

//                if(beniHatirla.isChecked()){
//                    preferenceMekanizmasi.save(context,txtKullaniciAdi.getText().toString(),"KullaniciAdi");
//                    preferenceMekanizmasi.save(context,txtKullaniciSifre.getText().toString(),"KullaniciSifre");
//                }
//                else
//                    Toast.makeText(context,"KAYIT EDILMEDI",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, OgretmenActivity.class);
//                startActivity(intent);
                ///database = new Database();
                //String kulllaniciId = database.loginCont(email,passwd,MainActivity.this,context);
               // System.out.println("-------------------- "+kulllaniciId);


                mAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(context,"Giriş Başarılı Yönlendiriliyorsunuz",Toast.LENGTH_LONG).show();
                            String loginId = task.getResult().getUser().getUid();
                            System.out.println("-------------------- "+loginId);
                            // İLGİLİ ACTİVİTY EKRANINA YÖNLENDİRMEK İÇİN LOGİN OLAN KULLANICININ TİPİ BELİRLENİR
                            db=FirebaseDatabase.getInstance();
                            myRef=db.getReference().child("girisBilgileri").child(loginId);
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //veritabanında login idsi ile kullanıcı tipi eşleştirilir
                                    String kullaniciTur = dataSnapshot.getValue().toString();
                                    Intent intent;
                                    // ilgili türe göre activity sayfasına yönlendirilir.
                                    if(kullaniciTur.equals("ogretmen")){
                                        intent=new Intent(context,OgretmenActivity.class);
                                        startActivity(intent);
                                    }
                                    else if(kullaniciTur.equals("ogrenci")){
                                        intent=new Intent(context,OgrenciActivitiy.class);
                                        startActivity(intent);
                                    }
                                    else if(kullaniciTur.equals("veli")){
                                        intent = new Intent(context,VeliActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else { // GİRİŞ HATALI İSE
                            Toast.makeText(context,"Giriş Bilgileriniz Hatalı",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        btnKayitol.setOnClickListener(new View.OnClickListener() { // KAYIT BUTONU AKSİYONU
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
        txtKullaniciAdi=findViewById(R.id.emailAdresi);
        txtKullaniciSifre=findViewById(R.id.kullaniciSifre);
        preferenceMekanizmasi=new PreferenceMekanizmasi();
    }

    public void preferencedanVeriCek(){
        txtKullaniciAdi.setText(preferenceMekanizmasi.read(context,"KullaniciAdi"));
        txtKullaniciSifre.setText(preferenceMekanizmasi.read(context,"KullaniciSifre"));
    }
    //kamera uygulaması eklenecek
    //Firebase stroge yüklenecek





}
