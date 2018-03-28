package com.example.poggipc.applr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by INNAX on 05/02/2018.
 */

public class ParseJSONAnnonce {

    public static String[] idAnnonce;
    public static String[] title;
    public static String[] duration;
    public static String[] nbPlace;
    public static String[] location;
    public static String[] description;
    public static String[] avatar;
    //public static String[] nomcateg;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID_ANNONCE = "idAnnonce";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_NBPLACE = "nbPlace";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_AVATAR = "avatar";
    //public static final String KEY_NOMCATEG = "nomcateg";
    private JSONArray annonce = null;
    private String json;

    public ParseJSONAnnonce(String json){
        this.json = json;
    }

    protected void parseJSONAnnonce(){
        //JSONObject jsonObject=null;
        try {
            annonce = new JSONArray(json);
            //annonce = jsonObject.getJSONArray(String.valueOf(JSON_ARRAY));
            idAnnonce = new String[annonce.length()];
            title = new String[annonce.length()];
            duration = new String[annonce.length()];
            nbPlace = new String[annonce.length()];
            location = new String[annonce.length()];
            description = new String[annonce.length()];
            avatar = new String[annonce.length()];
            //nomcateg = new String[annonce.length()];

            for(int i=0;i<annonce.length();i++){
                JSONObject jo = annonce.getJSONObject(i);
                idAnnonce[i] = jo.getString(KEY_ID_ANNONCE);
                title[i] = jo.getString(KEY_TITLE);
                duration[i] = jo.getString(KEY_DURATION);
                nbPlace[i] = jo.getString(KEY_NBPLACE);
                location[i] = jo.getString(KEY_LOCATION);
                description[i] = jo.getString(KEY_DESCRIPTION);
                avatar[i] = jo.getString(KEY_AVATAR);
                //nomcateg[i] = jo.getString(KEY_NOMCATEG);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
