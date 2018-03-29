package com.example.poggipc.applr.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poggipc.applr.User;

import java.util.ArrayList;

public class UsersBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "bddMytus.db";
    private static final String TABLE_USERS = "table_users";

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public UsersBDD(Context context) {
        //On instancie l'objet de la classe permettant la gestion de la BDD
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        //on ouvre la BDD en lecture et écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public long ajoutUser(User unUser) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues valeurs = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        valeurs.put("ID",unUser.getId());
        valeurs.put("USERNAME", unUser.getUsername());
        valeurs.put("EMAIL", unUser.getEmail());
        valeurs.put("AVATAR", unUser.getAvatar());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_USERS, null, valeurs);
    }

    public void viderTable() {
        //Suppression de toutes les lignes de la table
        bdd.delete(TABLE_USERS, null, null);
    }

    public int nombreMalades() {
        Cursor c = bdd.rawQuery("select * from TABLE_USERS", null);
        int nombre = c.getCount();
        c.close();
        return nombre;
    }

    public ArrayList<User> getTousLesUsers() {

        ArrayList<User> lesUsers = new ArrayList<User>();
        Cursor leCurseur = bdd.rawQuery("select * from TABLE_USERS", null);
        leCurseur.moveToFirst();
        while (!leCurseur.isAfterLast()){
            User user = new User(leCurseur.getInt(1), leCurseur.getString(2), leCurseur.getString(3), leCurseur.getString(4));
            lesUsers.add(user);
            leCurseur.moveToNext();
        }
        leCurseur.close();
        return lesUsers;

    }
}
