package com.example.tripmei;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Path_old extends AppCompatActivity {

   // protected LatLng start ;//= new LatLng(31.0413814,31.4178592);
   // protected LatLng end ;//new LatLng(30.969614,31.1830803);
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_old);
       AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        id = getIntent().getIntExtra("idmap", 0);
        Log.i("sdfsdfsdf",id+"");
        List<Trip> list = db.tripDao().getRow(id);
       double latStar = list.get(0).getLatitudeStart();
        double lonStar = list.get(0).getLongitudeStart();
        double latEnd = list.get(0).getLatitudeEnd();
        double logEnd = list.get(0).getLongitudeEnd();

        LatLng start = new LatLng(latStar,lonStar);
        LatLng end = new LatLng(latEnd , logEnd);
        //LatLng start = new LatLng(31.0413814,31.4178592);
       // LatLng end=  new LatLng(30.969614,31.1830803);
        Log.i("sdfsdfsdf",start.toString());
        Map mapsFragment = new Map();
        mapsFragment.setLatlang(start,end);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,mapsFragment).commit();
    }
}