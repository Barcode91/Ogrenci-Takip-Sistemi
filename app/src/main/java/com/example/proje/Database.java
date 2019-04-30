/* *
FİREBASE İŞLEMLERİNİ YAPACAK OLAN TEMEL SINIFTIR.
 */

package com.example.proje;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



//generic tip tanımlaması kullanıldı.
public class Database <tip> {
    FirebaseDatabase db;
    private String loginId;
    DatabaseReference myRef, oku;
    public static String veri;
    public static boolean sonuc;
    String userType, id, password, email;
    tip t;
    FirebaseAuth myAuth;


    public Database(tip tip) {
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        myAuth=FirebaseAuth.getInstance();
        this.t = tip;
        typeCont(); //ilk olarak nesne tipi belirlenir
    }
    public Database (){
        myAuth=FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

    }
    //aldığı nesne tipine göre veritabanına kayıt yapıları.
    public void userAdd(Activity activity, final Context context){
        myAuth = FirebaseAuth.getInstance();
        myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){// yetkilendirme başarılı ise diğer bilgiler kayıt edilir
                    loginId = task.getResult().getUser().getUid();
                    myRef.child("kullanicilar").child(userType).child(loginId).setValue(t);
                    myRef.child("girisBilgileri").child(loginId).setValue(userType);
                }
                else {

                    Toast.makeText(context,"kayıt yapılamadı",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public String loginCont(String email, String password, Activity activity, final Context context){ // PASİF

        //System.out.println("tc no : "+ tcNo+ "  activity gelen şifre : "+password);

        myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Giriş Başarılı Yönlendiriliyorsunuz",Toast.LENGTH_LONG).show();
                    loginId = task.getResult().getUser().getUid();
                    // İLGİLİ ACTİVİTY EKRANINA YÖNLENDİRMEK İÇİN LOGİN OLAN KULLANICININ TİPİ BELİRLENİR
                }
                else {
                    Toast.makeText(context,"Giriş Bilgileriniz Hatalı",Toast.LENGTH_LONG).show();
                }
            }
        });

        return loginId; // kullanıcını tipini döndürdü
    }

    public void typeCont(){
    // Nesne tipi kontrol edilir. Kullanıcı rolüne göre işlem yapılması için
        if(t instanceof Ogretmen){
            userType = "ogretmen";
            id = ((Ogretmen) t).gettCNo();
            email = ((Ogretmen) t).getEmailAdres();
            password = ((Ogretmen) t).getPass();
        }
        else if (t instanceof Ogrenci){
            userType = "ogrenci";
            id = ((Ogrenci) t).gettCNo();
            email = ((Ogrenci) t).getEmailAdres();
            password = ((Ogrenci) t).getPass();
        }
        else if (t instanceof Veli){
            userType = "veli";
            id = ((Veli) t).gettCNo();
            email = ((Veli) t).getEmailAdres();
            password = ((Veli) t).getPass();
        }
    }








}
