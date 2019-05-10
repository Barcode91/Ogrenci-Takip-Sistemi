package com.example.proje;

import android.content.Context;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fragmentSiniflar extends Fragment {

    Database database;
    Context context;

import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.ArrayList;

public class fragmentSiniflar extends Fragment {
    RecyclerView recyclerView;
    Spinner sinifSecim;
    Context context;
    String secim;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    File localFile;
    ListAdapter listAdapter;
    ArrayList<Ogrenci> sinifArraylist;
    int i;

    public fragmentSiniflar(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ogretmen_siniflar, container, false);
        context = getActivity();

        View view =inflater.inflate(R.layout.fragment_ogretmen_siniflar,container,false);
        recyclerView = view.findViewById(R.id.recyler_view_sinif);
        sinifSecim = view.findViewById(R.id.sinifSecimi);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        db = FirebaseDatabase.getInstance();
        databaseReference=db.getReference().child("Class").child("2-C");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ilk hali
                sinifArraylist = new ArrayList<>();
                for (DataSnapshot gelenler : dataSnapshot.getChildren())
                    sinifArraylist.add(gelenler.getValue(Ogrenci.class));




                //BURADA RESİMLER ÇEKİLİP İLGİLİ ÖGRENCİ CLASS İÇİNDEKİ İLGİLİ YERE SET EDİLİR.
                for (i=0; i<sinifArraylist.size()-1;i++) {
                    try {
                        localFile = File.createTempFile(sinifArraylist.get(i).gettCNo(), "jpg");
                    } catch (
                            IOException e) {
                        e.printStackTrace();
                    }
                    storageReference = FirebaseStorage.getInstance().getReference("pht_" + sinifArraylist.get(i).gettCNo());
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                            Bitmap res = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            sinifArraylist.get(i).setResim(res);

                        }
                    });
                }
                listAdapter = new ListAdapter(context,sinifArraylist);
                recyclerView.setAdapter(listAdapter);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }






}
