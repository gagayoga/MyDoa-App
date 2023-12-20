package com.example.mydoaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mydoaapp.databinding.ActivityMapsMasjidBinding;

public class MapsMasjid extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsMasjidBinding binding;

    private RelativeLayout infoLayout;
    private TextView namaTempat, lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsMasjidBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inisialisasi tampilan
        infoLayout = findViewById(R.id.infoLayout);
        namaTempat = findViewById(R.id.namaTempat);
        lokasi = findViewById(R.id.lokasi);

        // Sembunyikan LinearLayout saat aplikasi dimulai
        infoLayout.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Masjid Terdekat");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Tambahkan marker untuk 10 masjid di Surakarta (Contoh koordinat)
        LatLng masjid1 = new LatLng(-7.555994042810241, 110.7970356321487);
        LatLng masjid2 = new LatLng(-7.561597948403601, 110.80643751047246);
        LatLng masjid3 = new LatLng(-7.553014443200942, 110.80512178239232);
        LatLng masjid4 = new LatLng(-7.558578998465833, 110.79574033131944);
        LatLng masjid5 = new LatLng(-7.561309618638138, 110.7993654590111);

        LatLng masjid6 = new LatLng(-7.553737952888311, 110.80297373240609);
        LatLng masjid7 = new LatLng( -7.555195388297061, 110.79572685353158);
        LatLng masjid8 = new LatLng(-7.555526940521335, 110.79997980441317);
        LatLng masjid9 = new LatLng(-7.556025293752108, 110.80052487558672);
        LatLng masjid10 = new LatLng(-7.557806100896356, 110.7985702157536);


        LatLng masjid11 = new LatLng(-7.556694676084244, 110.80138586468517);
        LatLng masjid12 = new LatLng(-7.552823279033529, 110.80600999302168);
        LatLng masjid13 = new LatLng(-7.546655827332162, 110.79630911364224);
        LatLng masjid14 = new LatLng(-7.54944242558269, 110.80781042586756);
        LatLng masjid15 = new LatLng(-7.549079612137655, 110.79573913423746);

        // Tambahkan marker ke peta
        mMap.addMarker(new MarkerOptions().position(masjid1).title("Masjid An-Nur Jamil"));
        mMap.addMarker(new MarkerOptions().position(masjid2).title("Masjid Kota Barat Surakarta"));
        mMap.addMarker(new MarkerOptions().position(masjid3).title("Masjid Siti Aisyah"));
        mMap.addMarker(new MarkerOptions().position(masjid4).title("Masjid Al Ikhlas"));
        mMap.addMarker(new MarkerOptions().position(masjid5).title("Masjid Istiqomah"));

        mMap.addMarker(new MarkerOptions().position(masjid6).title("Masjid Al-Iman"));
        mMap.addMarker(new MarkerOptions().position(masjid7).title("Masjid Nur Hidayah"));
        mMap.addMarker(new MarkerOptions().position(masjid8).title("Masjid At-Taqwa"));
        mMap.addMarker(new MarkerOptions().position(masjid9).title("Masjid Mubarokah"));
        mMap.addMarker(new MarkerOptions().position(masjid10).title("Masjid Al-Munawwaroh"));

        mMap.addMarker(new MarkerOptions().position(masjid11).title("Masjid Fadlilah"));
        mMap.addMarker(new MarkerOptions().position(masjid12).title("Masjid Al-Fattah"));
        mMap.addMarker(new MarkerOptions().position(masjid13).title("Masjid Baiturrohim Sumber"));
        mMap.addMarker(new MarkerOptions().position(masjid14).title("Masjid Al-Fath Sumber"));
        mMap.addMarker(new MarkerOptions().position(masjid15).title("Masjid Zam Zam Al Mustaqim Kerten"));
        // ... Tambahkan marker lainnya

        // Atur tampilan peta agar fokus pada area masjid di Surakarta
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(masjid1, 13f));

        // Tambahkan listener untuk menanggapi klik pada marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Tampilkan informasi di LinearLayout saat marker diklik
                showInfoLayout(marker);
                return true;
            }
        });
    }

    private void showInfoLayout(Marker marker) {
        // Tampilkan LinearLayout dengan informasi masjid
        infoLayout.setVisibility(View.VISIBLE);
        namaTempat.setText(marker.getTitle());
        lokasi.setText("Latitude: " + marker.getPosition().latitude + "\nLongitude: " + marker.getPosition().longitude);
    }

    @Override
    public void onBackPressed() {
        if (infoLayout.getVisibility() == View.VISIBLE) {
            // Sembunyikan LinearLayout jika sedang terlihat
            infoLayout.setVisibility(View.GONE);
        } else {
            // Lakukan aksi kembali standar jika LinearLayout tidak terlihat
            super.onBackPressed();
        }
    }
}