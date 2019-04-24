package com.example.proje;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class fragmentAdapter extends FragmentPagerAdapter {
    private String tabTitle[]=new String[]{"Ogretmen","Veli","Ogrenci"};
    Context context;
    public fragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }


    @Override
    public Fragment getItem(int i) {
        //Gelen pozizyona gore fragment gosterme. her seferinde 1 tane fragment gosterilecek
        if (i==0){
            kayitOgretmen kayitOgretmen=new kayitOgretmen(context);
            return kayitOgretmen;}
         else if (i==1){
            kayitVeli kayitVeli = new kayitVeli(context);
            return kayitVeli;
        }
        else
        {
            kayitOgrenci kayitOgrenci=new kayitOgrenci();
            return kayitOgrenci;

        }




}
    @Override
    public int getCount() {
        return tabTitle.length;
    }
    @Nullable
    @Override
    //Sayfa basliklarini geri dondurmek icin
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}

