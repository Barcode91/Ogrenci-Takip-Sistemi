package com.example.proje;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
Button btnKayitol,btnGiris;
CheckBox beniHatirla;
Context context;
TextView txtKullaniciAdi,txtKullaniciSifre, paraloSifirla;
PreferenceMekanizmasi preferenceMekanizmasi;
FirebaseAuth mAuth;
FirebaseUser firebaseUser;
FirebaseDatabase db;
DatabaseReference myRef, myRefOku;
private String email, passwd;
Bundle bundle;
ProgressDialog progressDialog;
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
            mAuth.signOut();

        btnGiris.setOnClickListener(new View.OnClickListener() { // GİRİŞ BUTONU AKSİYONU
            @Override
            public void onClick(View v) { //login işlemi
                email = txtKullaniciAdi.getText().toString();
                passwd = txtKullaniciSifre.getText().toString();

                if(beniHatirla.isChecked()){
                    preferenceMekanizmasi.save(context,txtKullaniciAdi.getText().toString(),"KullaniciAdi");
                    preferenceMekanizmasi.save(context,txtKullaniciSifre.getText().toString(),"KullaniciSifre");
                }
                else{
                    preferenceMekanizmasi.save(context,null,"KullaniciAdi");
                    preferenceMekanizmasi.save(context,null,"KullaniciSifre");
                }


                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Giriş Yapılıyor...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //Toast.makeText(context,"Giriş Başarılı Yönlendiriliyorsunuz",Toast.LENGTH_LONG).show();
                            final String loginId = task.getResult().getUser().getUid();
                            System.out.println("-------------------- "+loginId);
                            // İLGİLİ ACTİVİTY EKRANINA YÖNLENDİRMEK İÇİN LOGİN OLAN KULLANICININ TİPİ BELİRLENİR
                            db=FirebaseDatabase.getInstance();
                            myRef=db.getReference().child("girisBilgileri").child(loginId);

                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //veritabanında login idsi ile kullanıcı tipi eşleştirilir
                                    String kullaniciTur = dataSnapshot.getValue().toString();
                                    kullaniciBilgiGetir(kullaniciTur,loginId);
                                    progressDialog.dismiss();

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else { // GİRİŞ HATALI İSE
                            progressDialog.dismiss();
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
                finish();
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
        paraloSifirla = findViewById(R.id.txt_sifremiUnuttum);
    }
    public void parolaReset(View v){

        final String email=txtKullaniciAdi.getText().toString();
        if (!email.isEmpty()){ // mail adresi girme durum kontrolü
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("PARALO SIFIRLAMA");
            alert.setMessage("."+email+"."+"\nEMAIL ADRESINIZE GONDERILSIN MI?");
            alert.setPositiveButton("GONDER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //mail adresini alert dialogdaki edittextten alacak

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Yeni parola için gerekli bağlantı adresinize gönderildi!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Mail gönderme hatası!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            alert.setNegativeButton("IPTAL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create().show();

        } else
            Toast.makeText(context, "Lütfen Mail Adresini Giriniz!", Toast.LENGTH_SHORT).show();






    }

    public void kullaniciBilgiGetir(final String userType, final String userId){
        System.out.println("tip      :"+userType+"       ıd:"+userId);
        myRefOku = db.getReference().child("kullanicilar").child(userType).child(userId);
        myRefOku.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Intent intent;
                if(userType.equals("ogretmen")){
                    System.out.println("-------------13333");

                    Ogretmen ogretmen = dataSnapshot.getValue(Ogretmen.class);
                    intent=new Intent(context,OgretmenActivity.class);
                    intent.putExtra("ogretmen",ogretmen);
                    startActivity(intent);
                    finish();

                }
                else if(userType.equals("ogrenci")){
                    System.out.println("-------------14444");
                    Ogrenci ogrenci = dataSnapshot.getValue(Ogrenci.class);
                    System.out.println("-----------------------111111"+ogrenci.getAdSoyad());
                    intent=new Intent(context,OgrenciActivity.class);
                    intent.putExtra("ogrenci",ogrenci);
                    startActivity(intent);
                    finish();

                }
                else if(userType.equals("veli")){
                    System.out.println("-------------55555");

                    Veli veli = dataSnapshot.getValue(Veli.class);
                    intent=new Intent(context,VeliActivity.class);
                    intent.putExtra("veli",veli);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    public void preferencedanVeriCek(){
        txtKullaniciAdi.setText(preferenceMekanizmasi.read(context,"KullaniciAdi"));
        txtKullaniciSifre.setText(preferenceMekanizmasi.read(context,"KullaniciSifre"));
    }
    //kamera uygulaması eklenecek
    //Firebase stroge yüklenecek
    public void ekranTemizle(View v){
        txtKullaniciAdi.setText("");
        txtKullaniciSifre.setText("");
        //preferenceMekanizmasi.delete(context);

    }

    @Override
    public void onBackPressed() { // geri tuşuna basınca uygulama kapansın
       mAuth.signOut();
        finish();

    }



}
