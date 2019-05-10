package com.example.proje;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    StorageReference storageReference;
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Ogrenci>sinifListe;
    File localFile;
    ViewHolder v;
    ArrayList<Bitmap> resimler= new ArrayList<>();


    public ListAdapter(Context context, ArrayList<Ogrenci> sinifListe) {
        this.context = context;
        this.sinifListe = sinifListe;
        //resimGetir();



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.satir_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        this.v = viewHolder;
        // satır içerikleri burada atanır
        viewHolder.adSoyad.setText(sinifListe.get(i).getAdSoyad());
        viewHolder.tcNo.setText(sinifListe.get(i).gettCNo());
        System.out.println("veritabanı dosya yolu :     "+"pht_"+sinifListe.get(i).gettCNo());
        System.out.println("veritabanı -----> :     "+"pht_"+sinifListe.get(i).gettCNo());


//        storageReference = FirebaseStorage.getInstance().getReference("pht_" + sinifListe.get(i).gettCNo());
//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                sinifListe.get(i).setResimUri(uri);
//                System.out.println(i);
//
//                System.out.println(sinifListe.get(i).getResimUri());
//
//
//
////                    System.out.println("getEncoded metodu     "+uri.getEncodedPath());
////                    System.out.println("getAuthoriy metodu     "+uri.getAuthority());
////                    System.out.println("getPatch metodu     "+uri.getPath());
//            }
//        });








        //viewHolder.resim.setImageURI((Uri.parse(sinifListe.get(i).getresimUrl())));
        viewHolder.linearLayout.setTag(viewHolder);
    }

    @Override
    public int getItemCount() {
        //kaç defa döndüreceği gelecek veri tipine göre ayarlanacak
        return sinifListe.size();
    }

    //public void resimGetir(){





 //       for (j=0; j<sinifListe.size()-1;j++) {
//           System.out.println("veritabanı -----> :     "+"pht_"+sinifListe.get(j).gettCNo());
//            storageReference = FirebaseStorage.getInstance().getReference("pht_" + sinifListe.get(j).gettCNo());
//            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    sinifListe.get(j).setresimUrl(uri.toString());
//                    System.out.println(j);
//
//                    System.out.println(sinifListe.get(j).getresimUrl());
//
////                    System.out.println("getEncoded metodu     "+uri.getEncodedPath());
////                    System.out.println("getAuthoriy metodu     "+uri.getAuthority());
////                    System.out.println("getPatch metodu     "+uri.getPath());
//                }
//            });


/*
            //System.out.println("getEncoded metodu     "+result.getEncodedPath());
            try {
                localFile = File.createTempFile(sinifListe.get(j).gettCNo(),"jpg");
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            storageReference = FirebaseStorage.getInstance().getReference("pht_" + sinifListe.get(j).gettCNo());
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                    Bitmap res = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    v.resim.setImageBitmap(res);
                    v.resim.setImageURI();

                    sinifListe.get(j).setResim(res);

                }
            });*/
//        }
//
//
//
//
//
//    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView adSoyad, tcNo;
        ImageView resim;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adSoyad = itemView.findViewById(R.id.list_kimlik);
            resim = itemView.findViewById(R.id.list_resim);
            tcNo = itemView.findViewById(R.id.list_tcNo);
            linearLayout = itemView.findViewById(R.id.list_linear);


        }
    }
}
