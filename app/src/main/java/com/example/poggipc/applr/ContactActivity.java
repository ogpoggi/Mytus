package com.example.poggipc.applr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ContactActivity extends AppCompatActivity {

    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getUsers.php";
    private ListView lst_contact;
    private SessionManager session;
    public final static String KEY_IDANNONCE = "idAnnonce";
    private String idAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        lst_contact = (ListView) findViewById(R.id.lst_contact);
        sendRequest();
    }

    private void sendRequest(){
        Intent intent = getIntent();
        idAnnonce = intent.getStringExtra(KEY_IDANNONCE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_LONG).show();
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_IDANNONCE,idAnnonce);
                return params;
            }
        };
        Log.i("MYTUS", "getAnnonceByCateg: " + stringRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.usernames, ParseJSON.emails , ParseJSON.avatars);
        lst_contact.setAdapter(cl);
    }
}