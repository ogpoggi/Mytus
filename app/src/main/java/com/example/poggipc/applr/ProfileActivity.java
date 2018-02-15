package com.example.poggipc.applr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ProfileActivity extends AppCompatActivity {

    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getAnnonce.php";
    private ListView lv_Annonce;
    private TextView tv_mess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lv_Annonce = (ListView) findViewById(R.id.lv_Annonce);
        tv_mess = (TextView) findViewById(R.id.tv_mess);
        Intent intent = getIntent();
        tv_mess.setText("Bonjour "+intent.getStringExtra(LoginActivity.KEY_USERNAME));
        sendRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_main_2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_mess:
                Intent intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONAnnonce(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSONAnnonce(String json){
        ParseJSONAnnonce pjA = new ParseJSONAnnonce(json);
        pjA.parseJSONAnnonce();
        CustomListAnnonce clA = new CustomListAnnonce(this, ParseJSONAnnonce.idAnnonce, ParseJSONAnnonce.title, ParseJSONAnnonce.duration, ParseJSONAnnonce.nbPlace, ParseJSONAnnonce.location, ParseJSONAnnonce.description);
        lv_Annonce.setAdapter(clA);
    }
}