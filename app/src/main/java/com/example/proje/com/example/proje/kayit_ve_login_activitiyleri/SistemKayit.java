package com.example.proje.com.example.proje.kayit_ve_login_activitiyleri;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.proje.R;

public class SistemKayit extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Context context;
    boolean gate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem_kayit);
        init();
        viewPager.setAdapter(new fragmentAdapter(getSupportFragmentManager(),context));
        tabLayout.setupWithViewPager(viewPager);

    }

    public void init(){
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.view_Pager);
        context=this;

    }

    public boolean sifreDogrulukKontrol(String strSifre,String strSifreTekrar){
        return strSifre.equals(strSifreTekrar);

    }
}






