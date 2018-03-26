package com.example.poggipc.applr;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getAnnonce.php";
    private ListView lv_Annonce;
    private TextView tv_mess;

    private Button btn_CreateAnnonce;
    private Button btn_sport;
    private Button btn_pleinAir;
    private Button btn_culture;
    private Button btn_soir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lv_Annonce = (ListView) findViewById(R.id.lv_Annonce);
        tv_mess = (TextView) findViewById(R.id.tv_mess);

        btn_sport = (Button) findViewById(R.id.btn_sport);
        btn_pleinAir = (Button) findViewById(R.id.btn_pleinAir);
        btn_culture = (Button) findViewById(R.id.btn_culture);
        btn_soir = (Button) findViewById(R.id.btn_soir);
        btn_CreateAnnonce = (Button) findViewById(R.id.btn_CreateAnnonce);


        btn_sport.setOnClickListener(this);
        btn_pleinAir.setOnClickListener(this);
        btn_culture.setOnClickListener(this);
        btn_soir.setOnClickListener(this);
        btn_CreateAnnonce.setOnClickListener(this);

        Intent intent = getIntent();
        tv_mess.setText("Bonjour "+intent.getStringExtra(LoginActivity.KEY_USERNAME));
        sendRequest(JSON_URL);
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

    private void sendRequest(String url){

        StringRequest stringRequest = new StringRequest(url,
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
        CustomListAnnonce clA = new CustomListAnnonce(this, ParseJSONAnnonce.idAnnonce, ParseJSONAnnonce.title, ParseJSONAnnonce.duration, ParseJSONAnnonce.nbPlace, ParseJSONAnnonce.location, ParseJSONAnnonce.description,ParseJSONAnnonce.avatar/*, ParseJSONAnnonce.nomcateg*/);
        lv_Annonce.setAdapter(clA);
    }

    @Override
    public void onClick(View view) {

        if(view == btn_sport){
            //sendRequest(SPORT_URL);
        }
        if(view == btn_pleinAir){
            //sendRequest(PA_URL);
        }
        if(view == btn_culture){
            //sendRequest(CULTURE_URL);
        }
        if(view == btn_soir){
            //sendRequest(SOIR_URL);
        }

    }
}