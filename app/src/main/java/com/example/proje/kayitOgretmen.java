package com.example.proje;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class kayitOgretmen extends Fragment {
    SistemKayit sistemKayit ;
    Context context;
    Button btnOnay,btnKaydet;
    TextView txtOnayKodu,txtSifre,txtSifreTekrar;
    boolean onayDogruluk=false,sifreDogruluk=false;
    public kayitOgretmen(Context context) {
        this.context=context;
        Toast.makeText(context,"LUTFEN KAYIT KODUNUZU DOGRULAYINIZ",Toast.LENGTH_LONG).show();

    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.kayitogretmen,container,false);
       btnOnay = view.findViewById(R.id.btnOnay);
       txtOnayKodu=view.findViewById(R.id.onayKodu);
       btnKaydet=view.findViewById(R.id.btnKayitTamamla);
       txtSifre=view.findViewById(R.id.KayitSifre);
       txtSifreTekrar=view.findViewById(R.id.KayitSifreTekrar);
       sistemKayit=new SistemKayit();


       //EGER SIFREYI VE ONAY KODUNU DOGRU GIRDIYSE VERITABANINA KAYIT EDECEK BUTON!!!!!!
       btnKaydet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               sifreDogruluk=sistemKayit.sifreDogrulukKontrol(txtSifre.getText().toString(),txtSifreTekrar.getText().toString());

               if (sifreDogruluk&&onayDogruluk)
               {
                   Toast.makeText(context,"KAYIT YAPILABILIR",Toast.LENGTH_SHORT).show();


               }
               else
                   Toast.makeText(context,"GIRILEN SIFRELER UYUSMUYOR",Toast.LENGTH_SHORT).show();

           }
       });


       //OGRETMEN ONAY KODU TESTI
       btnOnay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Integer.parseInt(txtOnayKodu.getText().toString())==0000){
               Toast.makeText(context,"basarili",Toast.LENGTH_LONG).show();
               txtOnayKodu.setTextColor(Color.parseColor("#008000"));
               onayDogruluk=true;
               }
               else
               {
                   onayDogruluk=false;
                   AlertDialog.Builder alert = new AlertDialog.Builder(context);
                   alert.setTitle("UYARI!");
                   alert.setMessage("YANLIS KOD GIRIDINIZ EGER BIR ONAY KODUNUZ YOKSA LUTFEN YONETICINIZE BASVURUN.");
                   alert.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   }).show();

               }

           }
       });

        return view;// Bu javanin gosterecegi xmli tanimladik.

    }



}
