package com.example.poggipc.applr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poggipc.applr.helper.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class MyActivities extends AppCompatActivity {

    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getMyAnnonces.php";
    private ListView lv_myannonces;
    public static final String KEY_IDUSER = "idUser";
    public static final String KEY_IDANNONCE = "idAnnonce";
    private SessionManager session;
    private String idUser;
    private String itemIdAnnonce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activities);
        lv_myannonces = (ListView) findViewById(R.id.lv_myannonces);
        session = new SessionManager(getApplicationContext());
        idUser = session.getKeyId();
        sendRequestMyAnnonces(JSON_URL);

        lv_myannonces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemIdAnnonce = parent.getItemAtPosition(position).toString();
                String idAnnonce = itemIdAnnonce;
                Intent intent = new Intent(MyActivities.this,ContactActivity.class);
                intent.putExtra(KEY_IDANNONCE,idAnnonce);
                startActivity(intent);
            }
        });

    }

    private void sendRequestMyAnnonces(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONAnnonce(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyActivities.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_IDUSER,idUser);
                return params;
            }
        };
        Log.i("hehe", "MyAnnonces: " + stringRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showJSONAnnonce(String json){
        ParseJSONAnnonce pjA = new ParseJSONAnnonce(json);
        pjA.parseJSONAnnonce();
        CustomListAnnonce clA = new CustomListAnnonce(this, ParseJSONAnnonce.idAnnonce, ParseJSONAnnonce.title, ParseJSONAnnonce.duration, ParseJSONAnnonce.nbPlace, ParseJSONAnnonce.location, ParseJSONAnnonce.description,ParseJSONAnnonce.avatar/*, ParseJSONAnnonce.nomcateg*/);
        lv_myannonces.setAdapter(clA);
    }
}
