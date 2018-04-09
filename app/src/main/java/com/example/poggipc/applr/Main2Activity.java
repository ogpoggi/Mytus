package com.example.poggipc.applr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
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

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    // API REST URL
    public static final String JSON_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getAnnonce.php";
    public static final String INSERTANNONCE_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/insertAnnonce.php";
    public static final String INSERTPARTICIPATION_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/participeAnnonce.php";
    public static final String GETANNONCEBYCATEG_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/getAnnonceByCateg.php";
    private ListView lv_Annonce;
    private TextView tv_message;
    //GET ANNONCE BY CATEG
    private int idcatego;
    private String idcateg;
    private String itemIdAnnonce;
    public static final String KEY_IDCATEG = "idcateg";
    // BUTTON CREATE ET CATEG
    private Button btn_createAnnonce;
    private Button btn_sport;
    private Button btn_pleinAir;
    private Button btn_culture;
    private Button btn_soir;
    // CREATE ANNONCE
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_NBPLACE = "nbPlace";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IDUSER = "iduser";
    public static final String KEY_IDCATEGORIE = "idcategorie";
    public static final String KEY_IDANNONCE="idannonce";
    // SHARED PREFERENCES
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.inflateHeaderView(R.layout.nav_header_main2);

        navigationView.setNavigationItemSelectedListener(this);


        lv_Annonce = (ListView) findViewById(R.id.lv_Annonce);
        tv_message = (TextView) findViewById(R.id.tv_message);
        //iv_avatar = (ImageView) findViewById(R.id.iv_avatar);

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
            Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        btn_sport.setOnClickListener(this);
        btn_pleinAir.setOnClickListener(this);
        btn_culture.setOnClickListener(this);
        btn_soir.setOnClickListener(this);
        btn_createAnnonce.setOnClickListener(this);
        tv_message.setText("Mytus "+""+session.getKeyName());
        //tv_mess.setText("Bonjour "+intent.getStringExtra(LoginActivity.KEY_USERNAME));
        //Picasso.with(getBaseContext()).load(session.getKeyUrlavatar()).placeholder(R.mipmap.ic_launcher_round).into(iv_avatar);

        ImageView nav_ImgView = (ImageView) hView.findViewById(R.id.nav_ImgView);
        TextView tv_username = (TextView) hView.findViewById(R.id.tv_username);
        TextView tv_userMail = (TextView) hView.findViewById(R.id.tv_userMail);
        tv_username.setText(session.getKeyName());
        tv_userMail.setText(session.getKeyMail());
        Picasso.with(getBaseContext())
                .load(session.getKeyUrlavatar())
                .transform(new CropCircleTransformation())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(nav_ImgView);

        sendRequestAnnonce(JSON_URL);


        lv_Annonce.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemIdAnnonce = parent.getItemAtPosition(position).toString();
                session.setKeyIdannonce(itemIdAnnonce);
                //tv_message.setText(session.getKeyIdannonce());
                //Log.d("ITEMIDANNONCE",session.getKeyIdannonce());

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Main2Activity.this);
                builder1.setMessage("Vous voulez participer à l'activité ? ");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Je participe",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                final String idannonce = session.getKeyIdannonce();
                                final String iduser = session.getKeyId();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, INSERTPARTICIPATION_URL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(Main2Activity.this,response,Toast.LENGTH_LONG).show();

                                                //sendRequestAnnonce(JSON_URL);
                                                dialog.cancel();
                                                finish();
                                                startActivity(getIntent());
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Main2Activity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }){
                                    @Override
                                    protected Map<String,String> getParams(){
                                        Map<String,String> params = new HashMap<String, String>();
                                        params.put(KEY_IDANNONCE,idannonce);
                                        params.put(KEY_IDUSER,iduser);
                                        return params;
                                    }
                                };
                                Log.i("hehe", "participe à une annonce: " + stringRequest);
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);
                            }
                        });

                builder1.setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                session.setLogin(false);
                session.setKeyName("");
                Intent inte = new Intent(Main2Activity.this,LoginActivity.class);
                startActivity(inte);
                finish();
                break;
            case R.id.menu_afficherSurMap:
                Intent inten = new Intent(Main2Activity.this,MapsActivity.class);
                startActivity(inten);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {
            Intent inte = new Intent(Main2Activity.this,MyActivities.class);
            startActivity(inte);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                                Toast.makeText(Main2Activity.this,response,Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                sendRequestAnnonce(JSON_URL);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Main2Activity.this,error.getMessage(),Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Main2Activity.this,error.getMessage(),Toast.LENGTH_LONG).show();
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

    private void getAnnonceByCateg(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETANNONCEBYCATEG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_LONG).show();
                        showJSONAnnonce(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main2Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_IDCATEG,idcateg);
                return params;
            }
        };
        Log.i("MYTUS", "getAnnonceByCateg: " + stringRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_sport){
            idcatego = 1;
            idcateg = String.valueOf(idcatego);
            getAnnonceByCateg();
        }
        if(view == btn_pleinAir){
            idcatego = 2;
            idcateg = String.valueOf(idcatego);
            getAnnonceByCateg();
        }
        if(view == btn_culture){
            idcatego = 3;
            idcateg = String.valueOf(idcatego);
            getAnnonceByCateg();
        }
        if(view == btn_soir){
            idcatego = 4;
            idcateg = String.valueOf(idcatego);
            getAnnonceByCateg();
        }
        if(view == btn_createAnnonce){
            showCreateAnnonce();
        }

    }
}
