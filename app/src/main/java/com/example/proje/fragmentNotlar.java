package com.example.proje;

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

public class fragmentNotlar extends Fragment {
    @Nullable
    Button harfNotuHesapla;
    TextView vizeNotu,finalNotu,odevNotu,projeNotu,txtHarfNotu,txt55,txt56,txt57,txt58;
    double harfNotu;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ogretmen_notlar,container,false);
        vizeNotu=view.findViewById(R.id.vizeNotu);
        finalNotu=view.findViewById(R.id.FinalNotu);
        odevNotu=view.findViewById(R.id.odevNotu);
        projeNotu=view.findViewById(R.id.ProjeNotu);
        txtHarfNotu=view.findViewById(R.id.txt_SETharfNotu);
        harfNotuHesapla=view.findViewById(R.id.btnHarfNotuHesapla);
        txt55=view.findViewById(R.id.txt55);
        txt56=view.findViewById(R.id.txt56);
        txt57=view.findViewById(R.id.txt57);
        txt58=view.findViewById(R.id.txt58);

        harfNotuHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.e("SAAAAAA",finalNotu.getText().toString());
              Log.e("SAAAAA",vizeNotu.getText().toString());
                Log.e("SAAAAA",projeNotu.getText().toString());
                Log.e("SAAAAA",odevNotu.getText().toString());


                harfNotu= (Integer.parseInt(finalNotu.getText().toString())*0.4)+((Integer.parseInt(vizeNotu.getText().toString()))*0.5+((Integer.parseInt(projeNotu.getText().toString())*0.8)+(Integer.parseInt(odevNotu.getText().toString())*0.2))*0.5)*0.6;
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

                Log.e("SAAAAA",String.valueOf(harfNotu));
            }
        });



        return view;
    }
}
