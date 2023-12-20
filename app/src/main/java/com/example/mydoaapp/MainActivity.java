package com.example.mydoaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    TextInputEditText usernameTxt,passwordTxt;
    Button Login;

    TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // inisialisasikan avriabel
        usernameTxt = findViewById(R.id.usernameEditText);
        passwordTxt = findViewById(R.id.passwordEditText);
        Login = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.textRegister);

        CheckLogin();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPagee.class);
                startActivity(intent);
            }
        });

        // membuat fungsi ketika button login ditekan
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidasiLogin();
            }
        });
    }

    private void ValidasiLogin(){
        // membuat validasi inputan
        String user = usernameTxt.getText().toString().trim();
        String pass = passwordTxt.getText().toString().trim();

        if (TextUtils.isEmpty(user)){
            usernameTxt.setError("Masukan username Anda");
            usernameTxt.setFocusable(true);
        }else if(TextUtils.isEmpty(pass)){
            passwordTxt.setError("Masukan password Anda");
            passwordTxt.setFocusable(true);
        }else{
            // performLogin(username, password);
            loginTask logintask = new loginTask();
            logintask.execute(user, pass);
        }
    }

    public class loginTask extends AsyncTask<String, Void, String> {

        // membuat variabel yang menamping endpoint API
        private static final String API_URL = "https://doa-api-tan.vercel.app/api/auth/login";

        // membuat variabel string tag untuk memunculkannya di Log
        private static final String Tag = "Tag";

        @Override
        protected String doInBackground(String... strings) {
            String Username = strings[0];
            String Pass = strings[1];

            try {
                // membuat koneksi ke dalam API
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Mengirimkan data ke dalam Api menggunakan jsonobject
                JSONObject object = new JSONObject();
                object.put("username", Username);
                object.put("password", Pass);

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
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // menutup koneksi dan lain-lainnya
                reader.close();
                connection.disconnect();

                return response.toString();

            } catch (IOException ex) {
                Log.e(Tag, "Eror : " + ex.getMessage());
                return null;
            } catch (JSONException e) {
                throw new RuntimeException();
            }

        }

        @Override
        // membaca response yang dilakukan di belakang layar
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {

                try {
                    // menampilkan response dan memasukan dalam variabel
                    JSONObject object = new JSONObject(s);
                    String Token = object.getString("token");


                    // Cek apakah objek "data" ada dalam respons JSON
                    if (object.has("data")) {
                        // Jika "data" ada, ambil objek "data"
                        JSONObject dataObject = object.getJSONObject("data");

                        // Periksa apakah "nama" ada dalam objek "data"
                        if (dataObject.has("nama")) {
                            // Ambil nilai "nama"
                            String Nama = dataObject.getString("nama");

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", Token);
                            editor.putString("nama", Nama);
                            editor.apply();

                            Toast.makeText(MainActivity.this, "Login Succesfully , Welcome Back : " + Nama, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, HomePage.class);
                            intent.putExtra("nama", Nama);
                            startActivity(intent);
                        } else {
                            // Handle the case where "nama" is not present in the JSON response
                            Toast.makeText(MainActivity.this, "Login Successful, but 'nama' not found in response", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException ex) {
                    ex.printStackTrace(); // Print the stack trace of the JSONException
                    throw new RuntimeException("Error parsing JSON response", ex);
                }
            } else {
                Toast.makeText(MainActivity.this, "Login gagal, periksa username atau password Anda.", Toast.LENGTH_SHORT).show();
                usernameTxt.setText("");
                passwordTxt.setText("");
            }
        }
    }

    private void CheckLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("Chech Login Belum", MODE_PRIVATE);
        String Token = sharedPreferences.getString("token", "");

        if (Token != null) {
            Toast.makeText(MainActivity.this, "Anda Sudah Login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }
    }
}