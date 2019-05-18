package com.example.proje.com.example.proje.kayit_ve_login_activitiyleri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proje.R;
import com.example.proje.com.example.proje.tanımliclasslar.Database;
import com.example.proje.com.example.proje.tanımliclasslar.Ogrenci;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


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
    File resimYolu;
    FirebaseDatabase db;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String resimUrl;
    String id;
    ProgressDialog pd;
    AlertDialog.Builder alertDialog;
    Uri uri=null;
    byte [] bytArray; //resimi tutacak hamveri
    static final int GALERI_CODE=11;
    static final  int KAMERA_CODE=22;


    kayitOgrenci(Context contex){
        this.context=contex;
    }



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.kayitogrenci, container,false);// Bu javanin gosterecegi xmli tanimladik.
        //View nesne tanımları

        //init(view);
        db = FirebaseDatabase.getInstance();
        databaseReference=db.getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("resimler");
        myAuth=FirebaseAuth.getInstance();
        btnKaydet=view.findViewById(R.id.btnKayitTamamla);
        txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
        tcNo = view.findViewById(R.id.tc_kimlikNo);
        adSoyad = view.findViewById(R.id.AdiSoyadi);
        passwd = view.findViewById(R.id.KayitSifre);
        ogrenciSinif = view.findViewById(R.id.OgrenciSinif);
        emailAdres = view.findViewById(R.id.emailAdresi);
        profilPhoto = view.findViewById(R.id.profil_photo);
        pd=new ProgressDialog(context);
        myAuth=FirebaseAuth.getInstance();
        sistemKayit=new SistemKayit();
        profilPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KAMERA İÇİN TETİKLEME YAPILIR
                alertDialog=new AlertDialog.Builder(context);
                alertDialog.setTitle("Fotograf Ekleme");
                alertDialog.setPositiveButton("GALERİ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,GALERI_CODE);

                    }
                });
                alertDialog.setNegativeButton("KAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,KAMERA_CODE);

                    }
                });
                alertDialog.show();








            }
        });



        btnKaydet.setOnClickListener(new View.OnClickListener() { // kaydet butonu işlevi
            @Override
            public void onClick(View v) {
                if(myAuth.getCurrentUser() != null){
                    myAuth.signOut();
                }


                sifreDogruluk = sistemKayit.sifreDogrulukKontrol(passwd.getText().toString(),txtSifreTekrar.getText().toString());
                if(sifreDogruluk) {
                    //kullaniciEkle();
                    pd.setMessage("Kayıt Yapılıyor...");
                    pd.show();
                    ogrenciAdd(uri);

                    Toast.makeText(context,"Kayıt Basarılı",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainActivity.class);
                    startActivity(intent);
                    sistemKayit.finish();

                }
                else
                    Toast.makeText(context,"Şifreler Uyuşmuyor",Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == KAMERA_CODE && resultCode == RESULT_OK){ // kameradan resim çekimi
            Bundle bundle = data.getExtras(); // kameradan dönen data alınır.
            resim=(Bitmap) bundle.get("data");
            profilPhoto.setImageBitmap(resim); // resim ekrana yerleştirilir
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // byte streamı oluşturulur
            resim.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream); // resim sıkıştırılır ve formatı değiştirilir.
            bytArray = byteArrayOutputStream.toByteArray();
            // TELEFON HAFIZASINA KAYIT İŞLEMİ
            File sd = Environment.getExternalStorageDirectory();
            File dir = new File(sd.getAbsolutePath()+"/OTS");
            dir.mkdirs();
            resimYolu = new File(dir,"fotograf.jpg");
            System.out.println("VERİ YOLU            "+dir);
            System.out.println("array verisi : "+"array boyutu  :"+bytArray.length+"     "+bytArray);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(resimYolu);
                fileOutputStream.write(bytArray);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == GALERI_CODE && resultCode == RESULT_OK){ //galeri resim seçimi
            Uri resimUri = data.getData();

            profilPhoto.setImageURI(resimUri);
            uri=resimUri;
            Log.i("galeri",resimUri.toString());






        }

    }

    public void ogrenciAdd(Uri resimuri){
        ogrenci.setAdSoyad(adSoyad.getText().toString());
        ogrenci.settCNo(tcNo.getText().toString());
        ogrenci.setPass(passwd.getText().toString());
        ogrenci.setClassNumber(ogrenciSinif.getSelectedItem().toString());
        ogrenci.setEmailAdres(emailAdres.getText().toString());
        ogrenci.setResim("default");
        Uri uri;
        if (resimuri==null)
            uri=Uri.fromFile(new File("/storage/emulated/0/OTS/fotograf.jpg"));
        else
            uri=resimuri;

        myAuth.createUserWithEmailAndPassword(ogrenci.getEmailAdres(),ogrenci.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
        final StorageReference ref = storageReference.child(ogrenci.gettCNo()+".jpg");

        //Uri uri = Uri.fromFile(new File("/storage/emulated/0/OTS/fotograf.jpg"));
        final UploadTask uploadTask = ref.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(task.isSuccessful())
                            resimUrl=ref.getDownloadUrl().toString();
                        return ref.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Kayıt İşlemi Başarılı",Toast.LENGTH_SHORT).show();

                            String sinif = ogrenci.getClassNumber();
                            resimUrl=task.getResult().toString();
                            String id =myAuth.getCurrentUser().getUid();
                            DatabaseReference yaz3=databaseReference;
                            DatabaseReference yaz4=databaseReference;
                            DatabaseReference yaz5=databaseReference;

                            DatabaseReference yaz=databaseReference.child("kullanicilar").child("ogrenci").child(id);
                            yaz.setValue(ogrenci);
                            yaz3.child("girisBilgileri").child(id).setValue("ogrenci");
                            yaz4.child("ogrenciler").child(ogrenci.gettCNo()).setValue(id);
                            yaz5.child("Class").child(sinif).child(id).setValue(ogrenci);

                            DatabaseReference yaziki=databaseReference.child("kullanicilar").child("ogrenci").child(myAuth.getCurrentUser().getUid());
                            DatabaseReference yazuc=databaseReference.child("Class").child(ogrenci.getClassNumber()).child(myAuth.getCurrentUser().getUid());
                            yaziki.child("resim").setValue(resimUrl);
                            yazuc.child("resim").setValue(resimUrl);
                            pd.dismiss();

                        }
                    }
                });

            }
        });










    }






}
