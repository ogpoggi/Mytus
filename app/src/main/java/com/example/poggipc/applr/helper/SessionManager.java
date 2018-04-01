package com.example.poggipc.applr.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	SharedPreferences.Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "MYTUS";
	
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_URLAVATAR = "urlAvatar";
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setKeyName(String keyname){
		editor.putString(KEY_NAME,keyname);
		editor.commit();
	}
	public void setKeyId(String id){
		editor.putString(KEY_ID,id);
		editor.commit();
	}
	public void setKeyUrlavatar(String urlavatar){
		editor.putString(KEY_URLAVATAR,urlavatar);
		editor.commit();
	}

	public String getKeyName(){
		return pref.getString(KEY_NAME,"");
	}

	public String getKeyId(){
		return pref.getString(KEY_ID,"");
	}
	public String getKeyUrlavatar(){
		return pref.getString(KEY_URLAVATAR,"");
	}
	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

		// commit changes
		editor.commit();

		Log.d(TAG, "User login session modified!");
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}
}
