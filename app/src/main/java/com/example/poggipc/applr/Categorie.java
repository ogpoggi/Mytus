package com.example.poggipc.applr;

/**
 * Created by basti on 13/02/2018.
 */

public class Categorie {

    private int idCateg;
    private String nomCateg;

    public Categorie(int idCateg, String nomCateg) {
        this.idCateg = idCateg;
        this.nomCateg = nomCateg;
    }

    public int getIdCateg() {
        return idCateg;
    }

    public void setIdCateg(int idCateg) {
        this.idCateg = idCateg;
    }

    public String getNomCateg() {
        return nomCateg;
    }

    public void setNomCateg(String nomCateg) {
        this.nomCateg = nomCateg;
    }
}
