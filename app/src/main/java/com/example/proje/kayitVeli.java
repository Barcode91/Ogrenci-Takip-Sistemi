package com.example.proje;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class kayitVeli extends Fragment {
    Context context;
    SistemKayit sistemKayit ;
    TextView txtSifre,txtSifreTekrar;
    Button btnKaydet;
    boolean sifreDogruluk=false;
    @SuppressLint("ValidFragment")
    public kayitVeli(Context context) {
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.kayitveli,container,false);
        btnKaydet=view.findViewById(R.id.btnVKayitTamamla);
        txtSifre=view.findViewById(R.id.KayitSifre);
        txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
        sistemKayit=new SistemKayit();

        //EGER SIFREYI VE ONAY KODUNU DOGRU GIRDIYSE VERITABANINA KAYIT EDECEK BUTON!!!!!!
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sifreDogruluk=sistemKayit.sifreDogrulukKontrol(txtSifre.getText().toString(),txtSifreTekrar.getText().toString());

                if (sifreDogruluk)
                {
                    Toast.makeText(context,"KAYIT YAPILABILIR",Toast.LENGTH_SHORT).show();


                }
                else
                    Toast.makeText(context,"GIRILEN SIFRELER UYUSMUYOR",Toast.LENGTH_SHORT).show();

            }
        });


        return inflater.inflate(R.layout.kayitveli, container,false);// Bu javanin gosterecegi xmli tanimladik.


    }

}
