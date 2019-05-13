package com.example.proje;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

@SuppressLint("ValidFragment")
public class fragmentNotlar extends Fragment {
    @Nullable
    Button harfNotuHesapla,btnNotKaydet ;
    TextView vizeNotu,finalNotu,odevNotu,projeNotu,txtHarfNotu,txt55,txt56,txt57,txt58, ogrenciKimlik;
    double harfNotu;
    Ogrenci ogrenci;
    String ogretmenBolum;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    HashMap not;
    String vize, finalnot, projenot, odev, harf;
    Context context;
    OgretmenActivity ogretmenActivity;
    ProgressDialog progressDialog;


    @SuppressLint("ValidFragment")
    public fragmentNotlar(Context context) {
        ogrenci=ogretmenActivity.ogrenci1;
        ogretmenActivity=new OgretmenActivity();
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ogretmen_notlar,container,false);
        vizeNotu=view.findViewById(R.id.vizeNotu);
        finalNotu=view.findViewById(R.id.FinalNotu);
        odevNotu=view.findViewById(R.id.odevNotu);
        projeNotu=view.findViewById(R.id.ProjeNotu);
        txtHarfNotu=view.findViewById(R.id.txt_SETharfNotu);
        harfNotuHesapla=view.findViewById(R.id.btnHarfNotuHesapla);
        btnNotKaydet = view.findViewById(R.id.btnNotKaydet);
        txt55=view.findViewById(R.id.txt55);
        txt56=view.findViewById(R.id.txt56);
        txt57=view.findViewById(R.id.txt57);
        txt58=view.findViewById(R.id.txt58);
        ogrenciKimlik= view.findViewById(R.id.NotlarOgrenciAdiSoyadi);
        OgretmenActivity ogretmenActivity=new OgretmenActivity();
        ogrenci=ogretmenActivity.ogrenci1;

        if (ogrenci!=null)
        ogrenciKimlik.setText(ogrenci.getAdSoyad());
        else
            Toast.makeText(getActivity(),"Lutfen Ogrenci Secimi yapiniz",Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
=======

>>>>>>> mehmet/master
        //OgretmenActivity ogretmenActivity=new OgretmenActivity();
        //ogrenci=ogretmenActivity.ogrenci1;
        ogretmenBolum=ogretmenActivity.ogretmenBolum;
        ogrenciKimlik.setText(ogrenci.getAdSoyad());
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Ögrenci Notları Getiriliyor");
        progressDialog.show();
        notGetir();
<<<<<<< HEAD
=======

>>>>>>> mehmet/master

        harfNotuHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harfNotu= (Integer.parseInt(finalNotu.getText().toString())*0.4)+
                        ((Integer.parseInt(vizeNotu.getText().toString()))*0.5+
                                ((Integer.parseInt(projeNotu.getText().toString())*0.8)+
                                        (Integer.parseInt(odevNotu.getText().toString())*0.2))*0.5)*0.6;
                if (harfNotu>80){txtHarfNotu.setText("AA"); txtHarfNotu.setTextColor(Color.parseColor("#008000"));}
                else if(harfNotu<79&&harfNotu>=74){txtHarfNotu.setText("BA"); txtHarfNotu.setTextColor(Color.parseColor("#008000")); }
                else if(harfNotu<73&&harfNotu>=65){txtHarfNotu.setText("BB"); txtHarfNotu.setTextColor(Color.parseColor("#008000")); }
                else if(harfNotu<64&&harfNotu>=55){txtHarfNotu.setText("CB"); txtHarfNotu.setTextColor(Color.parseColor("#008000")); }
                else if(harfNotu<54&&harfNotu>=45){txtHarfNotu.setText("CC"); txtHarfNotu.setTextColor(Color.parseColor("#008000")); }
                else if(harfNotu<44&&harfNotu>31){txtHarfNotu.setText("DC"); txtHarfNotu.setTextColor(Color.parseColor("#e5312e")); }
                else if(harfNotu<30&&harfNotu>=27){txtHarfNotu.setText("DD"); txtHarfNotu.setTextColor(Color.parseColor("#e5312e")); }
                else if(harfNotu<26&&harfNotu>=25){txtHarfNotu.setText("FD"); txtHarfNotu.setTextColor(Color.parseColor("#e5312e")); }
                else if(harfNotu<24&&harfNotu>=0){txtHarfNotu.setText("FF"); txtHarfNotu.setTextColor(Color.parseColor("#e5312e")); }
                else {txtHarfNotu.setText("XX");txtHarfNotu.setTextColor(Color.parseColor("#e5312e"));}
                txt55.setText(vizeNotu.getText());
                txt56.setText(finalNotu.getText());
                txt57.setText(odevNotu.getText());
                txt58.setText(projeNotu.getText());
                Toast.makeText(getActivity(),"NOT HESAPLANDI LUTFEN KAYDEDINIZ.",Toast.LENGTH_LONG).show();

            }
        });

        btnNotKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notKaydet();
            }
        });



        return view;
    }
    public void notKaydet(){ // notlar veritabanına kayıt edilir.
        vize=txt55.getText().toString();
        finalnot=txt56.getText().toString();
        odev=txt57.getText().toString();
        projenot=txt58.getText().toString();
        harf=txtHarfNotu.getText().toString();
        not = new HashMap<>();
        not.put("Vize",vize);
        not.put("Final",finalnot);
        not.put("Ödev",odev);
        not.put("Proje",projenot);
        not.put("Harf",harf);

        DatabaseReference kayit = reference.child("Notlar").child(ogrenci.gettCNo()).child(ogretmenBolum);
        kayit.setValue(not);
        Toast.makeText(context,"Notlar Kaydedildi",Toast.LENGTH_SHORT).show();
    }
    public void notGetir(){ // öğrenci notu veritabanından getirilir
        DatabaseReference oku = reference.child("Notlar").child(ogrenci.gettCNo()).child(ogretmenBolum);
        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap notlarGelen = null;
                    HashMap notlar = (HashMap) dataSnapshot.getValue();
                    if(notlar!=null){
                    txt55.setText(notlar.get("Vize").toString());
                    txt56.setText(notlar.get("Final").toString());
                    txt57.setText(notlar.get("Ödev").toString());
                    txt58.setText(notlar.get("Proje").toString());

                    txtHarfNotu.setText(notlar.get("Harf").toString());}
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
