package com.example.asiantech.travelapp.activities;

import android.app.Application;

/**
 * Created by phuong on 18/05/2017.
 */

public class App extends Application {
    private static App ourInstance = new App();
    private String idTour;
    private String nameTourist;
    private String idTourist;
    private String nameTourguide;
    private String idTourguide;

    public static App getInstance() {
        return ourInstance;
    }

    public String getNameTourguide() {
        return nameTourguide;
    }

    public void setNameTourguide(String nameTourguide) {
        this.nameTourguide = nameTourguide;
    }

    public String getIdTourguide() {
        return idTourguide;
    }

    public void setIdTourguide(String idTourguide) {
        this.idTourguide = idTourguide;
    }

    public String getIdTourist() {
        return idTourist;
    }

    public void setIdTourist(String idUser) {
        this.idTourist = idUser;
    }

    public String getNameTourist() {
        return nameTourist;
    }

    public void setNameTourist(String name) {
        this.nameTourist = name;
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
