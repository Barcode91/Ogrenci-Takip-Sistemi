package com.example.proje;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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






