package com.example.proje;

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

public class DevamsizlikGörüntülemAdapter extends RecyclerView.Adapter<DevamsizlikGörüntülemAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Devamsizlik>devamsizlik;
    ProgressDialog pd = null;
    Date tarih = new Date();
    SimpleDateFormat girisTarihi = new SimpleDateFormat("yyyy/MM/dd HH:mm");



    public DevamsizlikGörüntülemAdapter(Context context, ArrayList<Devamsizlik> devamsizlik) {
        this.context = context;
        this.devamsizlik = devamsizlik;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.devamsizlik_satir_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
//        tarih = new Date();
//        girisTarihi = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
      viewHolder.ders.setText(devamsizlik.get(i).getDersAdi());
      viewHolder.devamsizlik_tarih.setText(devamsizlik.get(i).getTarih());

    }

    @Override
    public int getItemCount() {
        //kaç defa döndüreceği gelecek veri tipine göre ayarlanacak
        return devamsizlik.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView ders, devamsizlik_tarih;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ders = itemView.findViewById(R.id.devamsız_ders);
            devamsizlik_tarih = itemView.findViewById(R.id.devamsız_tarih);



        }
    }
}
