package com.example.proje;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DegerlendirmeOgretmenListAdapter extends RecyclerView.Adapter<DegerlendirmeOgretmenListAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Degerlendirme>degerlendirmeListe;
    ProgressDialog pd = null;
    Date tarih = new Date();
    SimpleDateFormat girisTarihi = new SimpleDateFormat("yyyy/MM/dd HH:mm");



    public DegerlendirmeOgretmenListAdapter(Context context, ArrayList<Degerlendirme> degerlendirmeListe) {
        this.context = context;
        this.degerlendirmeListe = degerlendirmeListe;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.degerlendirme_satir_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        tarih = new Date();
        girisTarihi = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String kimlikSatiri;
      viewHolder.degerlendirme.setText(degerlendirmeListe.get(i).getDegerlendirme());
      kimlikSatiri=degerlendirmeListe.get(i).getOgretmenBolum()+"\t\t"+
              degerlendirmeListe.get(i).getOgretmenKimlik()+"\t\t"+
              girisTarihi.format(tarih);

      viewHolder.ogretmenKimlik.setText(kimlikSatiri);


    }

    @Override
    public int getItemCount() {
        //kaç defa döndüreceği gelecek veri tipine göre ayarlanacak
        return degerlendirmeListe.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView ogretmenKimlik, degerlendirme;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ogretmenKimlik = itemView.findViewById(R.id.ogretmenKimlik_degerlendirme);
            degerlendirme = itemView.findViewById(R.id.list_degerlendirme);
            linearLayout = itemView.findViewById(R.id.degerlendirme_list_linear);


        }
    }
}
