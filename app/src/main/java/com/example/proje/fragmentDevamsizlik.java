package com.example.proje;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class fragmentDevamsizlik extends Fragment {
    TextView ogreciKimlik;
    Ogrenci ogrenci;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ogretmen_devamsizlik,container,false);
        ogreciKimlik=view.findViewById(R.id.Devamsizilik_OgrenciAdiSoyadi);
        OgretmenActivity ogretmenActivity= new OgretmenActivity();
        ogrenci=ogretmenActivity.ogrenci1;
        ogreciKimlik.setText(ogrenci.getAdSoyad());
        return view;
    }
}
