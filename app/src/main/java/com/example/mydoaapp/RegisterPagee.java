package com.example.mydoaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterPagee extends AppCompatActivity {

    TextView btnLogin;

    TextInputEditText nameuser,username,password;

    MaterialButton buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pagee);
        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.textLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        nameuser = findViewById(R.id.namapengguna);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        buttonRegister = findViewById(R.id.registerButton);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidasiRwgsiter();
            }
        });

    }

    private void ValidasiRwgsiter(){
        String namePengguna = nameuser.getText().toString().trim();
        String usernamePengguna = username.getText().toString().trim();
        String passwordPengguna = password.getText().toString().trim();

        if (TextUtils.isEmpty(namePengguna)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nameuser.setError("Masukan Nama Pengguna");
                }
            });
        } else if (TextUtils.isEmpty(usernamePengguna)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    username.setError("Masukan Username");
                }
            });
        }else if (TextUtils.isEmpty(passwordPengguna)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    password.setError("Masukan Password");
                }
            });
        }else {
            CheckRegistrasi Task = new CheckRegistrasi();
            Task.execute(namePengguna,usernamePengguna,passwordPengguna);
        }


    }

    public class CheckRegistrasi extends AsyncTask<String,Void,String> {
        private static final String ENDPOINT_URL = "https://doa-api-tan.vercel.app/api/users/add-user";

        // membuat variabel string tag untuk memunculkannya di Log
        private static final String Tag ="Tag";


        @Override
        protected String doInBackground(String... strings) {
            String Name = strings[0];
            String User = strings[1];
            String Pass = strings[2];

            try {
                // membuat koneksi ke dalam API
                URL url = new URL(ENDPOINT_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);

                // Mengirimkan data ke dalam Api menggunakan jsonobject
                JSONObject object = new JSONObject();
                object.put("nama",Name);
                object.put("username",User);
                object.put("password",Pass);

                // Server Api membaca inputan yang di input oleh user
                DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
                stream.writeBytes(object.toString());
                stream.flush();
                stream.close();

                // Server Mengirimkan Response
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder response = new StringBuilder();

                //Membuat variabel line
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }

                // menutup koneksi dan lain-lainnya
                reader.close();
                connection.disconnect();

                return response.toString();

            }catch (IOException ex){
                Log.e(Tag,"Eror : " + ex.getMessage());
                return null;
            }catch (JSONException e){
                throw new RuntimeException();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null)
            {
                Toast.makeText(RegisterPagee.this, "Register Succes, silakan login kembali", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterPagee.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(RegisterPagee.this, "Register Faiiled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}