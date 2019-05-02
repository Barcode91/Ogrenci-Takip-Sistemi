package com.example.proje;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;


import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class kayitOgrenci extends Fragment {

    SistemKayit sistemKayit ;
    Context context;
    Button btnOnay,btnKaydet;
    TextView txtSifreTekrar;
    EditText tcNo, adSoyad,passwd, emailAdres;
    Database database;
    boolean sifreDogruluk=false;
    Spinner ogrenciSinif;
    ImageView profilPhoto;
    Ogrenci ogrenci = new Ogrenci();
    FirebaseAuth myAuth;
    Bitmap resim;
    byte [] bytArray; //resimi tutacak hamveri
    StorageReference storageReference ;

    kayitOgrenci(Context contex){
        this.context=contex;


    }



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.kayitogrenci, container,false);// Bu javanin gosterecegi xmli tanimladik.
        //View nesne tanımları

        //init(view);
        storageReference = FirebaseStorage.getInstance().getReference();
        btnKaydet=view.findViewById(R.id.btnKayitTamamla);
        txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
        tcNo = view.findViewById(R.id.tc_kimlikNo);
        adSoyad = view.findViewById(R.id.AdiSoyadi);
        passwd = view.findViewById(R.id.KayitSifre);
        ogrenciSinif = view.findViewById(R.id.OgrenciSinif);
        emailAdres = view.findViewById(R.id.emailAdresi);
        profilPhoto = view.findViewById(R.id.profil_photo);
        //myAuth=FirebaseAuth.getInstance();
        sistemKayit=new SistemKayit();
        profilPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KAMERA İÇİN TETİKLEME YAPILIR
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,13);

            }
        });



        btnKaydet.setOnClickListener(new View.OnClickListener() { // kaydet butonu işlevi
            @Override
            public void onClick(View v) {
                sifreDogruluk = sistemKayit.sifreDogrulukKontrol(passwd.getText().toString(),txtSifreTekrar.getText().toString());
                if(sifreDogruluk) {
                    kullaniciEkle();
                    Toast.makeText(context,"Kayıt Basarılı",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(context,"Şifreler Uyuşmuyor",Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }

//    public void init(View view){
//        btnKaydet=view.findViewById(R.id.btnKayitTamamla);
//        txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
//        tcNo = view.findViewById(R.id.tc_kimlikNo);
//        adSoyad = view.findViewById(R.id.AdiSoyadi);
//        passwd = view.findViewById(R.id.KayitSifre);
//        ogrenciSinif = view.findViewById(R.id.OgrenciSinif);
//        emailAdres = view.findViewById(R.id.emailAdresi);
//        profilPhoto = view.findViewById(R.id.profil_photo);
//        //myAuth=FirebaseAuth.getInstance();
//        sistemKayit=new SistemKayit();
//
//        storageReference = FirebaseStorage.getInstance().getReference();
//
//    }


    private void kullaniciEkle(){
        ogrenci.setAdSoyad(adSoyad.getText().toString());
        ogrenci.settCNo(tcNo.getText().toString());
        ogrenci.setPass(passwd.getText().toString());
        ogrenci.setClassNumber(ogrenciSinif.getSelectedItem().toString());
        ogrenci.setEmailAdres(emailAdres.getText().toString());
        database = new Database(ogrenci); // ogretmen nesnesini veritabanı constructer aracılığyla gönderilir
        database.userAdd(new SistemKayit(),context);
        String dosya = tcNo.getText().toString();
        System.out.println("----------------"+dosya+"-----------------------");
        StorageReference ref = storageReference.child("pht_"+dosya);
        UploadTask uploadTask = ref.putBytes(bytArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context,"Fotoğraf yükleme hatası",Toast.LENGTH_SHORT);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
/*
        Uri file = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/OTS/fotograf.jpg"));
        StorageReference ref = storageReference.child("resim");
        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // Toast.makeText(MainActivity.this,"veri kaydedildi",Toast.LENGTH_SHORT);

            }
        });




        /*

        StorageReference ref = storageReference.child(loginId);
        UploadTask uploadTask = ref.putBytes(profilPhoto);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context,"veri kaydedildi",Toast.LENGTH_SHORT);
            }
        }); */






    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 13 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras(); // kameradan dönen data alınır.
            resim=(Bitmap) bundle.get("data");
            profilPhoto.setImageBitmap(resim); // resim ekrana yerleştirilir
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // byte streamı oluşturulur
            resim.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream); // resim sıkıştırılır ve formatı değiştirilir.
            bytArray = byteArrayOutputStream.toByteArray();

            /*
            // TELEFON HAFIZASINA KAYIT İŞLEMİ
            File sd = Environment.getExternalStorageDirectory();
            File dir = new File(sd.getAbsolutePath()+"/OTS");
            dir.mkdirs();
            File file = new File(dir,"fotograf.jpg");
            System.out.println("VERİ YOLU            "+dir);
            System.out.println("array verisi : "+"array boyutu  :"+bytArray.length+"     "+bytArray);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytArray);
                fileOutputStream.close();
                //byteArrayOutputStream.close();
                System.out.println("yazdırma tmama");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(e+"hataaaaaa");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e+"hataaaaaa");
            }*/


            /*

            Uri file = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/cameraApp/foto1.jpg"));
            StorageReference ref = mStorageRef.child("resimler");




            ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this,"veri kaydedildi",Toast.LENGTH_SHORT);

                }
            });
            */





        }
    }






}
