package com.example.poggipc.applr;

import java.util.Date;

/**
 * Created by basti on 13/02/2018.
 */

public class Annonce {

    private int idAnnonce;
    private String title;
    private Date duration;
    private int nbPlace;
    private String location;
    private String description;
    private int idUser;
    private int idCategorie;

    public Annonce(int idAnnonce, String title, Date duration, int nbPlace, String location, String description, int idUser, int idCategorie) {
        this.idAnnonce = idAnnonce;
        this.title = title;
        this.duration = duration;
        this.nbPlace = nbPlace;
        this.location = location;
        this.description = description;
        this.idUser = idUser;
        this.idCategorie = idCategorie;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
}
