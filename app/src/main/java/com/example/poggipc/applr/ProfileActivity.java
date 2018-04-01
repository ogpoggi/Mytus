package com.example.poggipc.applr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poggipc.applr.helper.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getAnnonce.php";
    public static final String INSERTANNONCE_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/insertAnnonce.php";
    private ListView lv_Annonce;
    private TextView tv_mess;
    private ImageView iv_avatar;

    private Button btn_createAnnonce;
    private Button btn_sport;
    private Button btn_pleinAir;
    private Button btn_culture;
    private Button btn_soir;

    /*private String title;
    private String duration;
    private String nbplace;
    private String location;
    private String description;
    private String iduser;
    private String idcategorie;*/

    private ArrayList<String> lstLocation = new ArrayList<>();

    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_NBPLACE = "nbPlace";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IDUSER = "iduser";
    public static final String KEY_IDCATEGORIE = "idcategorie";

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lv_Annonce = (ListView) findViewById(R.id.lv_Annonce);
        tv_mess = (TextView) findViewById(R.id.tv_mess);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);

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
        //tv_mess.setText("Bonjour "+intent.getStringExtra(LoginActivity.KEY_USERNAME));
        Picasso.with(getBaseContext())
                .load(session.getKeyUrlavatar())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(iv_avatar);
        sendRequestAnnonce(JSON_URL);
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
                break;
            case R.id.menu_afficherSurMap:
                Intent inten = new Intent(ProfileActivity.this,MapsActivity.class);
                startActivity(inten);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showCreateAnnonce() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_create_annonce, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Créer une Annonce");
        dialogBuilder.setMessage("Remplir les champs pour publier une annonce");
        dialogBuilder.setPositiveButton("Publier l'annonce", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int whichButton) {

                final EditText et_title = (EditText) ((AlertDialog)dialog).findViewById(R.id.et_title);
                final EditText et_date = (EditText) ((AlertDialog)dialog).findViewById(R.id.et_date);
                final EditText et_lieu = (EditText) ((AlertDialog)dialog).findViewById(R.id.et_lieu);
                final EditText et_nbPlace = (EditText) ((AlertDialog)dialog).findViewById(R.id.et_nbPlace);
                final EditText et_description = (EditText) ((AlertDialog)dialog).findViewById(R.id.et_description);
                final Spinner spinner_categorie = (Spinner) ((AlertDialog)dialog).findViewById(R.id.spinner_categorie);


                final String title = et_title.getText().toString().trim();
                final String duration = et_date.getText().toString().trim();
                final String nbPlace = et_nbPlace.getText().toString().trim();
                final String location = et_lieu.getText().toString().trim();
                final String description = et_description.getText().toString().trim();
                final String iduser = session.getKeyId();

                int idCat = 0;
                // J'affecte l'id de la catégorie en fonction de la sélection dans le spinner(liste déroulante)
                if(spinner_categorie.getSelectedItem().toString().equals("sport")) idCat=1;
                if(spinner_categorie.getSelectedItem().toString().equals("plein-air")) idCat=2;
                if(spinner_categorie.getSelectedItem().toString().equals("visite culturelle")) idCat=3;
                if(spinner_categorie.getSelectedItem().toString().equals("sortir le soir")) idCat=4;

                final String idcategorie = String.valueOf(idCat);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, INSERTANNONCE_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(ProfileActivity.this,response,Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                sendRequestAnnonce(JSON_URL);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ProfileActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(KEY_TITLE,title);
                        params.put(KEY_DURATION, duration);
                        params.put(KEY_NBPLACE,nbPlace);
                        params.put(KEY_LOCATION,location);
                        params.put(KEY_DESCRIPTION,description);
                        params.put(KEY_IDUSER,iduser);
                        params.put(KEY_IDCATEGORIE,idcategorie);
                        return params;
                    }
                };
                Log.i("hehe", "insertAnnonce: " + stringRequest);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void sendRequestAnnonce(String url){

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