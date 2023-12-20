package com.example.mydoaapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KoneksiSurah extends AsyncTask<Void,Void, List<Surah>> {

    private static final String API_URL = " https://api.npoint.io/99c279bb173a6e28359c/data";
    private SurahAdapter adapter;

    public KoneksiSurah(SurahAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<Surah> doInBackground(Void... voids) {
        List<Surah> doaList = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String urut = jsonObject.getString("urut");
                    String asma = jsonObject.getString("asma");
                    String nama = jsonObject.getString("nama");
                    String keterangan = jsonObject.getString("keterangan");
                    String arti = jsonObject.getString("arti");

                    Surah item = new Surah();
                    item.setUrut(urut);
                    item.setAsma(asma);
                    item.setNama(nama);
                    item.setKeterangan(keterangan);
                    item.setArti(arti);
                    doaList.add(item);
                }
            }

            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return doaList;
    }

    @Override
    protected void onPostExecute(List<Surah> surahs) {
        super.onPostExecute(surahs);
        adapter.setDoaList(surahs);
        adapter.notifyDataSetChanged();
    }
}
