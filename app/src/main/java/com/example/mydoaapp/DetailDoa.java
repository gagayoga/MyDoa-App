package com.example.mydoaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailDoa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_doa);

        Intent intent = getIntent();
        //String doaId = intent.getStringExtra("doa_id");
        //String doaName = intent.getStringExtra("doa_name");
        String doaAyat = intent.getStringExtra("doa_ayat");
        String doaLatin = intent.getStringExtra("doa_latin");
        String doaArtinya = intent.getStringExtra("doa_artinya");

//        TextView idTextView = findViewById(R.id.arab);
//        TextView nameTextView = findViewById(R.id.latin);
        TextView ayatTextView = findViewById(R.id.ayat);
        TextView latinTextView = findViewById(R.id.latin);
        TextView artinyaTextView = findViewById(R.id.artinya);
        Button buttonback = findViewById(R.id.buttonselesai);

        //untuk kembali ke halaman awal
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        // untuk kembali juga
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        idTextView.setText(doaId);
//        nameTextView.setText(doaName);
        ayatTextView.setText(doaAyat);
        latinTextView.setText(doaLatin);
        artinyaTextView.setText(doaArtinya);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Doa");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
    }
}