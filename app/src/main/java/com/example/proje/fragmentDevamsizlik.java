package com.example.proje;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class fragmentDevamsizlik extends Fragment {
    TextView ogreciKimlik;
    Button devamsizlikKayıt;
    Ogrenci ogrenci;
    CalendarView calendarView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String tarih;
    Context context;

    public fragmentDevamsizlik(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ogretmen_devamsizlik,container,false);
        calendarView = view.findViewById(R.id.calendarView);
        ogreciKimlik=view.findViewById(R.id.Devamsizilik_OgrenciAdiSoyadi);
        devamsizlikKayıt=view.findViewById(R.id.devamsizlik_btn);
        OgretmenActivity ogretmenActivity= new OgretmenActivity();
        ogrenci=ogretmenActivity.ogrenci1;

        if (ogrenci!=null)
            ogreciKimlik.setText(ogrenci.getAdSoyad());
        else
            Toast.makeText(getActivity(),"Lutfen Ogrenci Secimi yapiniz",Toast.LENGTH_SHORT).show();

        ogreciKimlik.setText(ogrenci.getAdSoyad());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int yil, int ay, int gun) {
                tarih=gun+"/"+(ay+1)+"/"+yil;
                Log.i("Tarih",tarih);
                view.setTag("devamsız");
            }
        });


        devamsizlikKayıt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitEt();
            }
        });


        return view;
    }
    public void kayitEt(){
        DatabaseReference yaz = databaseReference.child("Devamsızlık").child(ogrenci.gettCNo());
        yaz.push().setValue(tarih);
        Toast.makeText(context,"Kayıt Tamamlandı",Toast.LENGTH_SHORT).show();

    }


}
