package com.example.proje.com.example.proje.ogrenci_veli_activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proje.R;
import com.example.proje.com.example.proje.tanımliclasslar.Ogrenci;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class fragmentNotGoruntuleme  extends Fragment {
    Button itirazEt, buttonSend, buttonCancel;
    TextView vizeNotu,finalNotu,odevNotu,projeNotu,txtHarfnotu;
    Spinner dersler;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    Ogrenci ogrenci;
    String dersadi;
    EditText kime, konu, mesaj;
    public fragmentNotGoruntuleme(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;

    }
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
        itirazEt=view.findViewById(R.id.btnItiraz);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();

      dersler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            dersadi=parent.getSelectedItem().toString();
            notGetir(dersadi);

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
      itirazEt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              mailGonderDialog();
//              AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
//              alert.setTitle("UYARI");
//              alert.setMessage("EMIN MISIN !!??");
//              alert.setIcon(R.drawable.surprised);
//              alert.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialog, int which) {
//                      //OGRETMENE MESAJ ATACAKKKKKK
//                      Toast.makeText(getActivity(),"ITIRAZ MAILINIZ GONDERILDI",Toast.LENGTH_SHORT).show();
//                  }
//              });
//              alert.setNegativeButton("VAZGECTIM", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getActivity(),"AFERIN",Toast.LENGTH_SHORT).show();
//                  }
//              });
//              alert.show();
          }
      });

        return view;
    }


    public void notGetir(String dersAdi) { // öğrenci notu veritabanından getirilir
        DatabaseReference oku = reference.child("Notlar").child(ogrenci.gettCNo()).child(dersAdi);
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
                    switch (txtHarfnotu.getText().toString()) {
                        case "AA":
                            txtHarfnotu.setTextColor(Color.parseColor("#008000"));
                            break;
                        case "BA":
                            txtHarfnotu.setTextColor(Color.parseColor("#008000"));
                            break;
                        case "BB":
                            txtHarfnotu.setTextColor(Color.parseColor("#008000"));
                            break;
                        case "CB":
                            txtHarfnotu.setTextColor(Color.parseColor("#008000"));
                            break;
                        case "CC":
                            txtHarfnotu.setTextColor(Color.parseColor("#008000"));
                            break;
                        default:
                            txtHarfnotu.setTextColor(Color.parseColor("#e5312e"));
                    }
                }
                else {
                    vizeNotu.setText("");
                    finalNotu.setText("");
                    projeNotu.setText("");
                    odevNotu.setText("");
                    txtHarfnotu.setText("");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    private void mailGonderDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.itiraz_alert_dialog);

        kime = dialog.findViewById(R.id.edit_text_to);
        konu =  dialog.findViewById(R.id.edit_text_subject);
        mesaj =  dialog.findViewById(R.id.edit_text_message);
        buttonSend =  dialog.findViewById(R.id.button_send);
        buttonCancel = dialog.findViewById(R.id.button_iptal);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailAdresi, mailKonu, mailMesaj;

                mailAdresi=kime.getText().toString();
                mailKonu=konu.getText().toString();
                mailMesaj = mesaj.getText().toString();
                String [] adresler = mailAdresi.split(",");
                if(mailAdresi.equals("") || mailKonu.equals("") || mailMesaj.equals(""))
                    Toast.makeText(getActivity(),"Lütfen Gerekli Yerleri Doldurun..",Toast.LENGTH_SHORT).show();
                else {
                    Log.i("mail",mailAdresi+mailKonu+mailMesaj);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, adresler);
                    intent.putExtra(Intent.EXTRA_SUBJECT, mailKonu);
                    intent.putExtra(Intent.EXTRA_TEXT, mailMesaj);

                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent, "Mail Hesabınızı Seçiniz..."));
                    dialog.dismiss();


                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
