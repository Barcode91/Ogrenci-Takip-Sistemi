package com.example.proje.com.example.proje.ogrenci_veli_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proje.R;
import com.example.proje.com.example.proje.ogretmenactivity.DegerlendirmeOgretmenListAdapter;
import com.example.proje.com.example.proje.tanımliclasslar.Degerlendirme;
import com.example.proje.com.example.proje.tanımliclasslar.Ogrenci;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragmentDegerlendirmeGoruntuleme extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Ogrenci ogrenci;
    RecyclerView recyclerView;
    TextView kimlik;
    Context context;
    DegerlendirmeOgretmenListAdapter adapter;
    ArrayList<Degerlendirme> liste;
    VeliActivity veliActivity;

    public fragmentDegerlendirmeGoruntuleme(Context context) {
        this.context = context;
    }

    public fragmentDegerlendirmeGoruntuleme(Context context, Ogrenci ogrenci ) {
        this.ogrenci = ogrenci;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_degerlendirme_goruntuleme,container,false);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
//        veliActivity = new VeliActivity();
//        ogrenci=veliActivity.ogrenci;
        liste=new ArrayList<>();
        recyclerView=view.findViewById(R.id.Veli_DegerlendirmeGecmisi);
        kimlik=view.findViewById(R.id.OgrenciKimlik_degerlendirme);
        kimlik.setText(ogrenci.getAdSoyad());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        veriGetir();
        adapter = new DegerlendirmeOgretmenListAdapter(context,liste);
        recyclerView.setAdapter(adapter);




        return view;
    }
    private void veriGetir() {
        Log.i("tcNo",ogrenci.gettCNo());
        DatabaseReference oku = databaseReference.child("Degerlendirme").child(ogrenci.gettCNo());
        oku.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.i("ögrenci tc:",ogrenci.gettCNo());
                Degerlendirme e = dataSnapshot.getValue(Degerlendirme.class);
                    liste.add(e);
                    Log.i("veriler ",e.toString());
//                liste.add(e);
//                Log.i("veriler ",e.toString());
//                Log.i("veriler_liste",liste.get((liste.size()-1)).toString());

                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(liste.size()-1);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
