/* *
FİREBASE İŞLEMLERİNİ YAPACAK OLAN TEMEL SINIFTIR.
 */

package com.example.proje;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


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
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    byte [] profilPhoto;


    public Database(tip tip) {
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        myAuth=FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        this.t = tip;
        typeCont(); //ilk olarak nesne tipi belirlenir
    }
    public Database (){
        firebaseStorage = FirebaseStorage.getInstance(); // stroge bağlantısı
        storageReference = firebaseStorage.getReference(); // bağlantı referansı
        myAuth=FirebaseAuth.getInstance(); // yetkilendirme bağlantı referansı
        db = FirebaseDatabase.getInstance(); // realtime veri tabanı bağlantısı
        myRef = db.getReference();

    }
    //aldığı nesne tipine göre veritabanına kayıt yapıları.
    public void userAdd(Activity activity, final Context context){
        myAuth = FirebaseAuth.getInstance();
        myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){// yetkilendirme başarılı ise diğer bilgiler kayıt edilir
                    loginId = task.getResult().getUser().getUid();
                   // myRef.child("kullanicilar").child(loginId).setValue(t);

                    myRef.child("kullanicilar").child(userType).child(loginId).setValue(t);
                    myRef.child("girisBilgileri").child(loginId).setValue(userType);
                    if (t instanceof Ogrenci){
                        String sinif = ((Ogrenci) t).getClassNumber();
                        myRef.child("ogrenciler").child(((Ogrenci) t).gettCNo()).setValue(myAuth.getUid());
                        myRef.child("Class").child(sinif).child(myAuth.getUid()).setValue(((Ogrenci) t).getAdSoyad());

                    }


                }
                else {

                    Toast.makeText(context,"kayıt yapılamadı",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public String getLoginId() {
        return loginId;
    }


    public void typeCont() {
        // Nesne tipi kontrol edilir. Kullanıcı rolüne göre işlem yapılması için
        if (t instanceof Ogretmen) {
            userType = "ogretmen";
            id = ((Ogretmen) t).gettCNo();
            email = ((Ogretmen) t).getEmailAdres();
            password = ((Ogretmen) t).getPass();
        } else if (t instanceof Ogrenci) {
            userType = "ogrenci";
            id = ((Ogrenci) t).gettCNo();
            email = ((Ogrenci) t).getEmailAdres();
            password = ((Ogrenci) t).getPass();
            //profilPhoto = ((Ogrenci) t).getProfilPhoto();
        } else if (t instanceof Veli) {
            userType = "veli";
            id = ((Veli) t).gettCNo();
            email = ((Veli) t).getEmailAdres();
            password = ((Veli) t).getPass();
        }
    }

/*
    public int tipCont(){

            int veri;

            // Nesne tipi kontrol edilir. Kullanıcı rolüne göre işlem yapılması için
            if(t instanceof Ogretmen){
                veri=0;
            }
            else if (t instanceof Ogrenci){
                veri=1;
            }
            else if (t instanceof Veli){
               veri=2;
            }
            return veri;
    }
*/










}
