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

public class KoneksiAPI extends AsyncTask<Void,Void, List<ModelDoa>> {

    private static final String API_URL = "https://doa-doa-api-ahmadramadhan.fly.dev/api";
    private AdapterDoa adapter;

    public KoneksiAPI(AdapterDoa adapter) {
        this.adapter = adapter;
    }


    @Override
    protected List<ModelDoa> doInBackground(Void... voids) {
        List<ModelDoa> doaList = new ArrayList<>();

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
                    String id = jsonObject.getString("id");
                    String doa = jsonObject.getString("doa");
                    String ayat = jsonObject.getString("ayat");
                    String latin = jsonObject.getString("latin");
                    String artinya = jsonObject.getString("artinya");

                    ModelDoa item = new ModelDoa();
                    item.setId(id);
                    item.setDoa(doa);
                    item.setAyat(ayat);
                    item.setLatin(latin);
                    item.setArtinya(artinya);
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
    protected void onPostExecute(List<ModelDoa> modelDoas) {
        super.onPostExecute(modelDoas);
        adapter.setDoaList(modelDoas);
        adapter.notifyDataSetChanged();
    }
}
