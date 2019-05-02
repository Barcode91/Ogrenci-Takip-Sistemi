package com.example.proje;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class kayitOgretmen extends Fragment {
    SistemKayit sistemKayit ;
    Context context;
    Button btnOnay,btnKaydet;
    TextView txtOnayKodu,txtSifre,txtSifreTekrar;
    EditText tcNo, adSoyad,passwd, emailAdres;
    Spinner bolum;
    Database database;
    Ogretmen ogretmen = new Ogretmen();
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
        tcNo = view.findViewById(R.id.tc_kimlikNo);
        adSoyad = view.findViewById(R.id.AdiSoyadi);
        passwd = view.findViewById(R.id.KayitSifre);
        bolum = view.findViewById(R.id.OgretmenBolum);
        emailAdres = view.findViewById(R.id.emailAdresi);

        sistemKayit=new SistemKayit();


       //EGER SIFREYI VE ONAY KODUNU DOGRU GIRDIYSE VERITABANINA KAYIT EDECEK BUTON!!!!!!
       btnKaydet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               sifreDogruluk=sistemKayit.sifreDogrulukKontrol(txtSifre.getText().toString(),txtSifreTekrar.getText().toString());

               if (sifreDogruluk&&onayDogruluk)
               {
                   Toast.makeText(context,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                    kullaniciEkle();
                   Toast.makeText(context,"Kayıt Basarılı",Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(context,MainActivity.class);
                   startActivity(intent);

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

        return view;

    }

    public void kullaniciEkle(){
    // Arayüz Bilgilerini nesne değişkine atar ve veri tabanı insert eder.
    ogretmen.setAdSoyad(adSoyad.getText().toString());
    ogretmen.settCNo(tcNo.getText().toString());
    ogretmen.setPass(passwd.getText().toString());
    ogretmen.setBolum(bolum.getSelectedItem().toString());
    ogretmen.setEmailAdres(emailAdres.getText().toString());
    database = new Database(ogretmen); // ogretmen nesnesini veritabanı constructer aracılığyla gönderilir
    database.userAdd(new SistemKayit(),context); // kullanıcı veritabanına eklenir.

    }



}
