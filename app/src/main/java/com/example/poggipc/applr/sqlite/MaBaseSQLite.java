package com.example.poggipc.applr.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BASTIEN on 10/11/2016.
 */

public class MaBaseSQLite extends SQLiteOpenHelper {
    private static final String TABLE_USERS= "table_users";
    private static final String COL_ID = "ID";
    private static final String COL_USERNAME = "USERNAME";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_AVATAR = "AVATAR";

    private static final String TABLE_ANNONCES = "table_annonces";
    private static final String COL_IDANNONCE = "IDANNONCE";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_DURATION = "DURATION";
    private static final String COL_NBPLACE = "NBPLACE";
    private static final String COL_LOCATION = "LOCATION";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_IDUSER = "IDUSER";
    private static final String COL_IDCATEGORIE ="IDCATEGORIE";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + " ("
            + COL_ID + " INTEGER PRIMARY KEY, "
            + COL_USERNAME + " TEXT NOT NULL, "
            + COL_PASSWORD + " TEXT NOT NULL, "
            + COL_EMAIL + " TEXT NOT NULL, "
            + COL_AVATAR + " TEXT NOT NULL);";


    private static final String CREATE_TABLE_ANNONCES = "CREATE TABLE "
            + TABLE_ANNONCES + " ("
            + COL_IDANNONCE + "INTEGER PRIMARY KEY, "
            + COL_TITLE + " TEXT NOT NULL, "
            + COL_DURATION + "DATE NOT NULL, "
            + COL_NBPLACE + " INTEGER NOT NULL, "
            + COL_LOCATION + " TEXT NOT NULL, "
            + COL_DESCRIPTION + " TEXT NOT NULL, "
            + COL_IDUSER + " INTEGER NOT NULL, "
            + COL_IDCATEGORIE + "INTEGER NOT NULL);";

    //Le constructeur
    public MaBaseSQLite(Context context, String nomBDD, CursorFactory factory, int version) {
        super(context, nomBDD, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Création des tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ANNONCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // A écrire
        // actions à exécuter lors d'un changement de version
    }
}
