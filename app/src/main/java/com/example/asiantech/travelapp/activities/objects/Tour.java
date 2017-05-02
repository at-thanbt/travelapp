package com.example.asiantech.travelapp.activities.objects;

import java.sql.Date;

/**
 * Created by asiantech on 30/04/2017.
 */
public class Tour {
    private String idTour;
    private String tourName;
    private String destination;
    private String descriptionn;
    private Date startDate ;
    private Date endDate ;
    private String idTourGide;
    private String idPiture;

    public Tour(String idTour, String tourName, String destination, String descriptionn, Date startDate, String idTourGide, String idPiture, Date endDate) {
        this.idTour = idTour;
        this.tourName = tourName;
        this.destination = destination;
        this.descriptionn = descriptionn;
        this.startDate = startDate;
        this.idTourGide = idTourGide;
        this.idPiture = idPiture;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIdTourGide() {
        return idTourGide;
    }

    public void setIdTourGide(String idTourGide) {
        this.idTourGide = idTourGide;
    }

    public String getIdPiture() {
        return idPiture;
    }

    public void setIdPiture(String idPiture) {
        this.idPiture = idPiture;
    }
}
