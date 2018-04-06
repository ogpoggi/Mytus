package com.example.poggipc.applr;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String LOCATION_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getLocationAnnonce.php";

    public static String[] locations;
    public static String[] titles;
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TITLE = "title";
    private JSONArray ann = null;
    private ArrayList<String> lstLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sendRequestLocation(LOCATION_URL);
    }

    private void sendRequestLocation(String url) {

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ann = new JSONArray(response);
                            locations = new String[ann.length()];
                            titles = new String[ann.length()];
                            for (int i = 0; i < ann.length(); i++) {
                                JSONObject jo = ann.getJSONObject(i);
                                locations[i] = jo.getString(KEY_LOCATION);
                                titles[i] = jo.getString(KEY_TITLE);
                                lstLocation.add(locations[i]);
                                Log.d("LISTE LOCATION", "ok" + lstLocation);
                                LatLng address = getLocationFromAddress(getApplicationContext(), locations[i]);
                                mMap.addMarker(new MarkerOptions().position(address).title(titles[i]));
                                mMap.setMinZoomPreference(15.0f);
                                mMap.setMaxZoomPreference(20.0f);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
