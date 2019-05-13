package com.example.proje;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.example.proje.OgretmenActivity.ogretmenBolum;

public class fragmentNotGoruntuleme  extends Fragment {
    TextView vizeNotu,finalNotu,odevNotu,projeNotu,txtHarfnotu;
    Spinner dersler;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    Ogrenci ogrenci;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_not_goruntuleme,container,false);
        vizeNotu=view.findViewById(R.id.txt_vizeNotu);
        finalNotu=view.findViewById(R.id.txt_finalNotu);
        odevNotu=view.findViewById(R.id.txt_odevNotu);
        projeNotu=view.findViewById(R.id.txt_ProjeNotu);
        txtHarfnotu=view.findViewById(R.id.txt_harfNotu);
        dersler=view.findViewById(R.id.spinnerDersler);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();

      dersler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if (position==0){
                  notGetir("MATEMATIK");
                  Toast.makeText(getActivity(),"0",Toast.LENGTH_SHORT).show();

              } else if (position==1){
                  notGetir("KIMYA");
                  Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();

              } else if (position==2){
                  Toast.makeText(getActivity(),"2",Toast.LENGTH_SHORT).show();

              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
        return view;
    }

    public void notGetir(String dersAdi) { // öğrenci notu veritabanından getirilir
        DatabaseReference oku = reference.child("Notlar").child("12345678911").child(dersAdi);
        ValueEventListener valueEventListener = oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap notlarGelen = null;
                HashMap notlar = (HashMap) dataSnapshot.getValue();
                if (notlar != null) {
                    vizeNotu.setText(notlar.get("Vize").toString());
                    finalNotu.setText(notlar.get("Final").toString());
                    projeNotu.setText(notlar.get("Ödev").toString());
                    odevNotu.setText(notlar.get("Proje").toString());
                    txtHarfnotu.setText(notlar.get("Harf").toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }}
