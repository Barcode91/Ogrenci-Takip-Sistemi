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
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragmentDevamsizlikGoruntuleme extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Ogrenci ogrenci;
    RecyclerView recyclerView;
    Context context;
    DevamsizlikGörüntülemAdapter adapter;
    ArrayList<Devamsizlik> liste;
    TextView kimlik;

    public fragmentDevamsizlikGoruntuleme(Ogrenci ogrenci, Context context) {
        this.ogrenci = ogrenci;
        this.context = context;
        liste=new ArrayList<>();
        Log.i("gelenveri",ogrenci.gettCNo());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_devamsizlik_goruntuleme,container,false);
        recyclerView = view.findViewById(R.id.listviewDevamsizlikCizelgesi);
        Log.i("gelenveri",ogrenci.getAdSoyad());
        kimlik=view.findViewById(R.id.OgrenciKimlik_devamsizlik);
        kimlik.setText(ogrenci.getAdSoyad());
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        devamsizlikGetir();
        adapter = new DevamsizlikGörüntülemAdapter(context,liste);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void devamsizlikGetir() {
        String ogrenciTc=ogrenci.gettCNo();
        DatabaseReference oku = databaseReference.child("Devamsızlık").child(ogrenciTc);
        oku.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Devamsizlik devamsizlik = dataSnapshot.getValue(Devamsizlik.class);
                liste.add(devamsizlik);
                Log.i("devam",devamsizlik.getTarih()+devamsizlik.getDersAdi());
                adapter.notifyDataSetChanged();

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
