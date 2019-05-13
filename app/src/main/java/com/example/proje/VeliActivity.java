package com.example.proje;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VeliActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    Veli veli;
    TextView headerAd, headerMail ;
    ImageView headerResim;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    static Ogrenci ogrenci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veli);
        context=this;
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        Intent intent=getIntent();
        veli=(Veli) intent.getSerializableExtra("veli");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentKimlikBilgisi kimlikBilgisi = new fragmentKimlikBilgisi(context);
        kimlikBilgisi.setVeli(veli); // veli nesnesi fragmente taşınır.
        fragmentTransaction.replace(R.id.content_frame,kimlikBilgisi);
        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View head = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ogrenciGetir();


        headerAd = head.findViewById(R.id.VeliAdiSoyadi);
        headerMail = head.findViewById(R.id.VeliEposata);
        headerResim = head.findViewById(R.id.VeliFotograf);
        headerAd.setText(veli.getAdSoyad());
        headerMail.setText(veli.getEmailAdres());


    }

    private void ogrenciGetir() {
        String ogrenciTc=veli.getCocukTc();
        Log.i("ögrenci",ogrenciTc);
        DatabaseReference oku = databaseReference.child("ogrenciler").child(ogrenciTc);
        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getValue().toString();
                Log.i("key------",key);
                bilgiGetir(key);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void bilgiGetir(String key) {
        DatabaseReference oku = databaseReference.child("kullanicilar").child("ogrenci").child(key);
        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ogrenci = new Ogrenci();
                ogrenci = dataSnapshot.getValue(Ogrenci.class);
                Log.i("key-ögrenci",ogrenci.gettCNo());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.veli, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_kimlikBilgisi) {
            fragmentKimlikBilgisi kimlikBilgisi =new fragmentKimlikBilgisi(context, ogrenci);
            fragmentTransaction.replace(R.id.content_frame,kimlikBilgisi);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_notlar) {
            fragmentNotGoruntuleme fragmentNotGoruntuleme =new fragmentNotGoruntuleme();
            fragmentTransaction.replace(R.id.content_frame,fragmentNotGoruntuleme);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_devamsizlik) {
            fragmentDevamsizlikGoruntuleme fragmentDevamsizlikGoruntuleme=new fragmentDevamsizlikGoruntuleme();
            fragmentTransaction.replace(R.id.content_frame,fragmentDevamsizlikGoruntuleme);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_degerlendirme) {
            fragmentDegerlendirmeGoruntuleme fragmentDegerlendirmeGoruntuleme = new fragmentDegerlendirmeGoruntuleme(context, ogrenci);
            fragmentTransaction.replace(R.id.content_frame,fragmentDegerlendirmeGoruntuleme);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_cikis){
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(context,MainActivity.class));
            finish();



        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
