package com.example.asiantech.travelapp.activities.objects;

import java.sql.Date;

import lombok.Getter;

/**
 * Created by asiantech on 30/04/2017.
 */
public class Tour {
    private String idTour;
    private String tourName;
    private String destination;
    private String descriptionn;
    private String startDate ;
    private String endDate ;
    private String usernameTourGuide;
    @Getter
    private String idTourGuide;
    private int maximumPerson;

    public int getMaximumPerson() {
        return maximumPerson;
    }

    public void setMaximumPerson(int maximumPerson) {
        this.maximumPerson = maximumPerson;
    }

    public Tour(String idTour, String tourName, String destination, String descriptionn, String startDate, String usernameTourGuide, String endDate) {
        this.idTour = idTour;
        this.tourName = tourName;
        this.destination = destination;
        this.descriptionn = descriptionn;
        this.startDate = startDate;
        this.usernameTourGuide = usernameTourGuide;
        this.endDate = endDate;
    }

    public Tour() {
    }

    public String getIdTour() {
        return idTour;
    }

    public void setIdTour(String idTour) {
        this.idTour = idTour;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescriptionn() {
        return descriptionn;
    }

    public void setDescriptionn(String descriptionn) {
        this.descriptionn = descriptionn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUsernameTourGuide() {
        return usernameTourGuide;
    }

    public void setUsernameTourGuide(String idTourGide) {
        this.usernameTourGuide = idTourGide;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "idTour='" + idTour + '\'' +
                ", tourName='" + tourName + '\'' +
                ", destination='" + destination + '\'' +
                ", descriptionn='" + descriptionn + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", usernameTourGuide='" + usernameTourGuide + '\'' +
                ", maximumPerson=" + maximumPerson +
                '}';
    }
}
