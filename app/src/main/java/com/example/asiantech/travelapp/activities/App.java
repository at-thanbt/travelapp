package com.example.asiantech.travelapp.activities;

import android.app.Application;

/**
 * Created by phuong on 18/05/2017.
 */

public class App extends Application{
    private String idTour;
    private String name;
    private String idUser;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdTour() {
        return idTour;
    }

    public void setIdTour(String idTour) {
        this.idTour = idTour;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
