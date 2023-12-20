package com.example.mydoaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        // ini fungsi untuk progressbarnya
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000); // Waktu Pnding 3 Detik
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    SharedPreferences sharedPreferences = getSharedPreferences("Chech Login Belum", MODE_PRIVATE);
                    String Token = sharedPreferences.getString("token","");

                    if(Token != null){
                        Toast.makeText(MainActivity.this,"Anda Sudah Login",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreen.this, HomePage.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        thread.start();
        // end fungsi
    }
}