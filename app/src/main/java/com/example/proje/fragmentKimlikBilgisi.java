package com.example.proje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class fragmentKimlikBilgisi extends Fragment {
    private Ogretmen ogretmen =null;
    private Ogrenci ogrenci = null;
    private Veli veli = null;
    TextView adSoyad, email,tcNo, sinif, headerAd, headerMail ;
    ImageView resim, headerResim;
    LinearLayout linearLayout;
    FirebaseStorage storage;
    StorageReference storageReference;
    File localFile;
    FirebaseAuth auth;

//    T tip;
//    Database database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragmen_kimlik_bilgisi,container,false);
        View view1 = inflater.inflate(R.layout.nav_header_ogrenci,container,false);
        adSoyad = view.findViewById(R.id.OgrenciAdSoyad);
        email = view.findViewById(R.id.OgrenciEposta);
        tcNo = view.findViewById(R.id.ogrenci_tc_kimlikNo);
        sinif = view.findViewById(R.id.OgrenciSinif);
        resim = view.findViewById(R.id.ogrenciFotograf);
        //linearLayout = view.findViewById(R.id.ogrenci_nav_header);
        headerAd = view1.findViewById(R.id.OgrenciAdiSoyadi);
        headerMail = view1.findViewById(R.id.OgrenciEmail);
        headerResim = view1.findViewById(R.id.ogrenciFotograf);
        storageReference = FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        //ogrenci.setLoginId(auth.getUid());
        try {
            localFile = File.createTempFile("resim","jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bilgiGoster();





        //database = new Database(tip);

        return view;
    }
    public Ogretmen getOgretmen() {
        return ogretmen;
    }

    public void setOgretmen(Ogretmen ogretmen) {
        this.ogretmen = ogretmen;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public Veli getVeli() {
        return veli;
    }

    public void setVeli(Veli veli) {
        this.veli = veli;
    }

    public void bilgiGoster(){
        if(ogretmen!= null){
            adSoyad.setText(ogretmen.getAdSoyad());
            tcNo.setText(ogretmen.gettCNo());
            email.setText(ogretmen.getEmailAdres());

        }
        else if(ogrenci!= null){
            System.out.println("--------------------ögrenci içindeyiz");
            adSoyad.setText(ogrenci.getAdSoyad());
            tcNo.setText(ogrenci.gettCNo());
            email.setText(ogrenci.getEmailAdres());
            sinif.setText(ogrenci.getClassNumber());
            headerAd.setText(ogrenci.getAdSoyad());
            headerMail.setText(ogrenci.getEmailAdres());
            StorageReference ref = storageReference.child("pht_"+ogrenci.gettCNo());
            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap res = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    resim.setImageBitmap(res);
                    headerResim.setImageBitmap(res);

                }
            });

        }
        else if(veli!= null){
            adSoyad.setText(veli.getAdSoyad());
            tcNo.setText(veli.gettCNo());
            email.setText(veli.getEmailAdres());

        }


        System.out.println("------------------------tansit geçtik");
        //System.out.println(ogrenci.getAdSoyad());

    }












}
