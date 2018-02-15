package com.example.poggipc.applr;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by POGGIPC on 08/06/2017.
 */

public class ParseJSON {
    public static String[] ids;
    public static String[] usernames;
    public static String[] emails;
    //public static String[] images;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    //public static final String KEY_IMAGE = "image";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            ids = new String[users.length()];
            usernames = new String[users.length()];
            emails = new String[users.length()];
            //images = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                usernames[i] = jo.getString(KEY_USERNAME);
                emails[i] = jo.getString(KEY_EMAIL);
                //images[i] = jo.getString(KEY_IMAGE);
                //replaceAll("\\\\", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
