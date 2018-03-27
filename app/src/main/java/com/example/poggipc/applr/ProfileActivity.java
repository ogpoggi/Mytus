package com.example.poggipc.applr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poggipc.applr.helper.SessionManager;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getAnnonce.php";
    private ListView lv_Annonce;
    private TextView tv_mess;

    private Button btn_createAnnonce;
    private Button btn_sport;
    private Button btn_pleinAir;
    private Button btn_culture;
    private Button btn_soir;
    private SessionManager session;

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
        btn_createAnnonce = (Button) findViewById(R.id.btn_createAnnonce);

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            session.setLogin(false);
            //logoutUser();
            // Launching the login activity
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        btn_sport.setOnClickListener(this);
        btn_pleinAir.setOnClickListener(this);
        btn_culture.setOnClickListener(this);
        btn_soir.setOnClickListener(this);
        btn_createAnnonce.setOnClickListener(this);


        tv_mess.setText(session.getKeyName());
        //Intent intent = getIntent();
        //tv_mess.setText("Bonjour "+intent.getStringExtra(LoginActivity.KEY_USERNAME));
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
            case R.id.menu_logout:
                session.setLogin(false);
                session.setKeyName("");
                Intent inte = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(inte);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showCreateAnnonce() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_create_annonce, null);
        dialogBuilder.setView(dialogView);

        EditText et_title = (EditText) findViewById(R.id.et_title);
        EditText et_date = (EditText) findViewById(R.id.et_date);
        EditText et_lieu = (EditText) findViewById(R.id.et_lieu);
        EditText et_nbPlace = (EditText) findViewById(R.id.et_nbPlace);
        EditText et_description = (EditText) findViewById(R.id.et_description);
        Spinner spinner_categorie = (Spinner) findViewById(R.id.spinner_categorie);

        dialogBuilder.setTitle("Créer une Annonce");
        dialogBuilder.setMessage("Remplir les champs pour publier une annonce");
        dialogBuilder.setPositiveButton("Publier l'annonce", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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
        if(view == btn_createAnnonce){
            showCreateAnnonce();
        }

    }
}