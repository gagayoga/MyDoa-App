package com.example.mydoaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomePage extends AppCompatActivity {

    Fragment fragment;
    FragmentManager manager;
    BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().hide();

        // inisialiasi variabel
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameconatiner,new Fragment2()).commit();
        bottom = findViewById(R.id.bottomnav);
        TextView NamaUser = (TextView) findViewById(R.id.textName);

        String userName = getIntent().getStringExtra("nama");

        NamaUser.setText(userName);
        // untuk memunculkan jam dan tanggal
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView jamViewItem = (TextView) findViewById(R.id.jamTextView);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, EEEE");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//        Calendar calendar = Calendar.getInstance();
//        Date currentDate = calendar.getTime();
//        String formattedDate = dateFormat.format(currentDate);
//        String formattedTime = timeFormat.format(currentDate);
//        dateTextView.setText(formattedDate + " " + formattedTime);

        // untuk mendapatkan waktu realtime
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Dapatkan waktu saat ini
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id"));
                String formattedTime = timeFormat.format(calendar.getTime());

                // Atur teks waktu pada TextView
                dateTextView.setText(formattedTime);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String formatDate = simpleDateFormat.format(calendar.getTime());

                jamViewItem.setText(formatDate);
                // Jalankan ulang Runnable setiap detik (1000 milidetik)
                handler.postDelayed(this, 1000);
            }
        };

        // untuk menjalankna handler
        handler.post(runnable);
        // membuat fungsi untuk bottom navigasi
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.about:
                        fragment = new Fragment3();
                        break;
                    case R.id.home:
                        fragment = new Fragment2();
                        break;
                    case R.id.alquran:
                        fragment = new Fragment1();
                        break;
                    case R.id.masjid:
                        Intent intent = new Intent(getApplicationContext(), MapsMasjid.class);
                        startActivity(intent);
                        break;
                    case R.id.doa:
                        fragment = new Fragment4();
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frameconatiner, fragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

}