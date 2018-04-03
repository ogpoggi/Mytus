package com.example.poggipc.applr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poggipc.applr.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOGIN_URL = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/login.php";

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AVATAR = "avatar";

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    private String id;
    private String username;
    private String email;
    private String password;
    private String avatar;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();
        }

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void loginUser(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {
                                // user successfully logged in
                                // Create login session
                                session.setKeyName(et_username.getText().toString().trim());

                                session.setLogin(true);

                                // Now store the user in SQLite
                                id = jObj.getString("id");

                                JSONObject user = jObj.getJSONObject("user");
                                username = user.getString("username");
                                email = user.getString("email");
                                password = user.getString("password");
                                avatar = user.getString("avatar");
                                session.setKeyId(id);
                                session.setKeyUrlavatar(avatar);
                                session.setKeyMail(email);
                                openProfile();
                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,et_username.getText().toString().trim());
                params.put(KEY_PASSWORD,et_password.getText().toString().trim());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_USERNAME, username);
        intent.putExtra(KEY_EMAIL, email);
        intent.putExtra(KEY_PASSWORD,password);
        intent.putExtra(KEY_AVATAR, avatar);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        if(v == btn_login){
            loginUser();
        }
        if(v == btn_register){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
