package com.example.poggipc.applr.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poggipc.applr.Annonce;
import com.example.poggipc.applr.User;

import java.util.ArrayList;
import java.util.Date;

public class AnnoncesBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "bddMytus.db";
    private static final String TABLE_ANNONCES = "table_annonces";

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public AnnoncesBDD(Context context) {
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

    public long ajoutUser(Annonce uneAnnonce) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues valeurs = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        valeurs.put("IDANNONCE",uneAnnonce.getIdAnnonce());
        valeurs.put("TITLE", uneAnnonce.getTitle());
        valeurs.put("DURATION", uneAnnonce.getTitle());
        valeurs.put("NBPLACE", uneAnnonce.getNbPlace());
        valeurs.put("LOCATION", uneAnnonce.getLocation());
        valeurs.put("DESCRIPTION", uneAnnonce.getDescription());
        valeurs.put("IDUSER", uneAnnonce.getIdUser());
        valeurs.put("IDCATEG", uneAnnonce.getIdCategorie());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_ANNONCES, null, valeurs);
    }

    public void viderTable() {
        //Suppression de toutes les lignes de la table
        bdd.delete(TABLE_ANNONCES, null, null);
    }

    public int nombreMalades() {
        Cursor c = bdd.rawQuery("select * from TABLE_ANNONCES", null);
        int nombre = c.getCount();
        c.close();
        return nombre;
    }

    public ArrayList<Annonce> getToutesLesAnnonces() {

        ArrayList<Annonce> lesAnnonces = new ArrayList<Annonce>();
        Cursor leCurseur = bdd.rawQuery("select * from TABLE_ANNONCES", null);
        leCurseur.moveToFirst();
        while (!leCurseur.isAfterLast()){
            Annonce annonce = new Annonce(leCurseur.getInt(1), leCurseur.getString(2), new Date(leCurseur.getLong(3)), leCurseur.getInt(4), leCurseur.getString(5), leCurseur.getString(6), leCurseur.getInt(7), leCurseur.getInt(8));
            lesAnnonces.add(annonce);
            leCurseur.moveToNext();
        }
        leCurseur.close();
        return lesAnnonces;

    }

}
