package com.example.asiantech.travelapp.activities;

import android.app.Application;

/**
 * Created by phuong on 18/05/2017.
 */

public class App extends Application{
    private String idTour;

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
