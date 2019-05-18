package com.example.proje.com.example.proje.ogretmenactivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proje.R;
import com.example.proje.com.example.proje.tanımliclasslar.Ogrenci;
import com.github.siyamed.shapeimageview.CircularImageView;
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
    Bitmap resim;
    Thread thread;
    ProgressDialog pd = null;
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
        storageReference = FirebaseStorage.getInstance().getReference();
//        pd = ProgressDialog.show(context,"Yükleniyor","bekleyiniz..",true,false);
//        pd.dismiss();
//        thread = new Thread(this);
//        thread.start();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.adSoyad.setText(sinifListe.get(i).getAdSoyad());
        viewHolder.tcNo.setText(sinifListe.get(i).gettCNo());
        Picasso.with(context).load(Uri.parse(sinifListe.get(i).getResim())).into(viewHolder.resim);
        viewHolder.linearLayout.setTag(viewHolder);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder viewHolder1 = (ViewHolder) v.getTag();
                int index = viewHolder1.getPosition();
                Toast.makeText(context,"Ögrenci Seçimi Yapıldı",Toast.LENGTH_SHORT).show();
                OgretmenActivity ogretmenActivity = new OgretmenActivity();
                ogretmenActivity.ogrenci1=sinifListe.get(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        //kaç defa döndüreceği gelecek veri tipine göre ayarlanacak
        return sinifListe.size();
    }

    public void resimGetir( String tcNo){





// //       for (j=0; j<sinifListe.size()-1;j++) {
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



            //System.out.println("getEncoded metodu     "+result.getEncodedPath());
            try {
                System.out.println("dosya oluşturuldu");
                localFile = File.createTempFile("image","jpg");
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            StorageReference ref = storageReference.child("pht_" + tcNo);
            //storageReference = FirebaseStorage.getInstance().getReference("pht_" + tcNo);
            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("onSuccess içerisndeyiz ");


                    Bitmap res = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    v.resim.setImageBitmap(res);
                    //v.resim.setImageURI();

                    //sinifListe.get(j).setResim(res);

                }
            });
             System.out.println("onSuccess dışındayiz ");
//
//
//
//
//
    }
int j;

   public void run() {

        try {
            for (j=0; j<sinifListe.size();j++) {

                localFile = File.createTempFile("resim", "jpg");

                storageReference = FirebaseStorage.getInstance().getReference("pht_" + sinifListe.get(j).gettCNo());
                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                        Bitmap res = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        resimler.add(res);
                        Log.i("resimgetir sayacı :", String.valueOf(j));
                        handler.obtainMessage();

                    }
                });

        }
        }
        catch (Exception e){  e.printStackTrace();}



    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            pd.dismiss();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView adSoyad, tcNo;
        CircularImageView resim;
        //ImageView resim;
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
