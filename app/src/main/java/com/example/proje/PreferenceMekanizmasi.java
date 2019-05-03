package com.example.proje;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class PreferenceMekanizmasi {
    //public String key="kullaniciAdi";
    public void save(Context context,String icerik,String key){
        SharedPreferences sharedPreferences =context.getSharedPreferences("sistemeGiris",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,icerik);
        editor.commit();
    }
    public String read(Context context,String key){
        SharedPreferences sharedPreferences =context.getSharedPreferences("sistemeGiris",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,null);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void delete(Context context){
        boolean sonuc = context.deleteSharedPreferences("sistemeGiris");

    }
}
