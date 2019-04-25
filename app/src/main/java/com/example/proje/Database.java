/* *
FİREBASE İŞLEMLERİNİ YAPACAK OLAN TEMEL SINIFTIR.
 */

package com.example.proje;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



//generic tip tanımlaması kullanıldı.
public class Database <tip> {
    FirebaseDatabase db;
    DatabaseReference myRef, oku;
    public static String veri;
    public static boolean sonuc;
    String userType, id, password;
    tip t;


    public Database(tip tip) {
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        this.t = tip;
        typeCont(); //ilk olarak nesne tipi belirlenir
    }
    public Database (){
        db = FirebaseDatabase.getInstance();

    }
    //aldığı nesne tipine göre veritabanına kayıt yapıları.
    public void userAdd(){

        myRef.child("kullanicilar").child(userType).child(id).setValue(t);
        myRef.child("girisBilgileri").child(id).setValue(password);




//        if(t instanceof Ogretmen){
//            myRef.child("kullanicilar").child("ogretmen").child(((Ogretmen) t).gettCNo()).setValue(t);
//            myRef.child("girisBilgileri").child("ogretmen").child(((Ogretmen) t).gettCNo()).setValue(((Ogretmen) t).getPass()); }
//        else if (t instanceof Ogrenci) {
//            myRef.child("kullanicilar").child("ogrenci").child(((Ogrenci) t).gettCNo()).setValue(t);
//            myRef.child("girisBilgileri").child("ogrenci").setValue(((Ogrenci) t).gettCNo());
//        }
//        else if (t instanceof Veli)
//            myRef.child("kullanicilar").child("veli").child(((Veli) t).gettCNo()).setValue(t);

    }

    public boolean log(String tcNo, String password) {
        return loginCont(tcNo,password);



    }

    public boolean loginCont(String tcNo, String password){

        System.out.println("tc no : "+ tcNo+ "  activity gelen şifre : "+password);

        oku = db.getInstance().getReference().child("girisBilgileri").child(tcNo);


        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ilgili kullanıcı şifresi veri tabanında tc nosuna göre aranır
                if (dataSnapshot.getValue() != null) { //tc kimlik numarası veritabanında bulunursa
                    System.out.println("onDAtachange"+ veri);
                    veri = dataSnapshot.getValue().toString();
                    sonuc = true;
                    System.out.println("veri tabanı sorusu sonas onDAtachange değişti "+ veri);
                } else{ //tc no databasede yoksa
                    sonuc = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };


        //kontrol için
        System.out.println("sistemden çekilen şifre :"+veri);
        System.out.println("Girilen şifre :"+password);

        oku.addListenerForSingleValueEvent(listener);
        if(password.equals(veri) && sonuc==true) {
            System.out.println("mainacitiy dönüş");
            return true; //tc databasede var ve şifre doğru ise

        }
        else {
            System.out.println("mainacitiy dönüş");
            return false; // eşleme yoksa
        }

    }

    public void typeCont(){
    // Nesne tipi kontrol edilir. Kullanıcı rolüne göre işlem yapılması için
        if(t instanceof Ogretmen){
            userType = "ogretmen";
            id = ((Ogretmen) t).gettCNo();
            password = ((Ogretmen) t).getPass();
        }
        else if (t instanceof Ogrenci){
            userType = "ogrenci";
            id = ((Ogrenci) t).gettCNo();
            password = ((Ogrenci) t).getPass();
        }
        else if (t instanceof Veli){
            userType = "veli";
            id = ((Veli) t).gettCNo();
            password = ((Veli) t).getPass();
        }
    }








}
