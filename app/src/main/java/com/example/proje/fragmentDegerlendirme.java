package com.example.proje;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragmentDegerlendirme extends Fragment {
    Button btn_degerlendirmeKayit;
    EditText degerlendirmetxt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Ogrenci ogrenci;
    OgretmenActivity ogretmenActivity;
    RecyclerView recyclerView;
    Context context;
    DegerlendirmeOgretmenListAdapter adapter;
    ArrayList<Degerlendirme> liste;
    public fragmentDegerlendirme(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ogretmen_degerlendirme,container,false);
        liste=new ArrayList<>();
        btn_degerlendirmeKayit = view.findViewById(R.id.btn_degerlendirmeKayit);
        degerlendirmetxt=view.findViewById(R.id.editxt_ogretmenDegerlendirmeGirisi);
        recyclerView=view.findViewById(R.id.DegerlendirmeGecmisi);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        ogretmenActivity=new OgretmenActivity();
        ogrenci=ogretmenActivity.ogrenci1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new DegerlendirmeOgretmenListAdapter(context,liste);


        btn_degerlendirmeKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degerlendirmeKayit();
            }
        });

        veriGetir();
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void veriGetir() {
        DatabaseReference oku = databaseReference.child("Degerlendirme").child(ogrenci.gettCNo());
        oku.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.i("ögrenci tc:",ogrenci.gettCNo());
                Degerlendirme e = dataSnapshot.getValue(Degerlendirme.class);
                if (e.getOgretmenBolum().equals(ogretmenActivity.ogretmenBolum)){
                    liste.add(e);


                Log.i("veriler ",e.toString());}
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

    private void degerlendirmeKayit() {
        DatabaseReference yaz = databaseReference;
        Degerlendirme degerlendirme = new Degerlendirme();
        degerlendirme.setOgretmenBolum(ogretmenActivity.ogretmenBolum);
        degerlendirme.setOgretmenKimlik(ogretmenActivity.ogretmen.getAdSoyad());
        degerlendirme.setDegerlendirme(degerlendirmetxt.getText().toString());
        yaz.child("Degerlendirme").child(ogrenci.gettCNo()).push().setValue(degerlendirme);


    }
}
