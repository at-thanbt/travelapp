package com.example.asiantech.travelapp.activities.objects;

import java.io.Serializable;

import lombok.Data;

@Data
public class LocationNotification implements Serializable {
    private String id;
    private String content;
    private double latitude;
    private double longitude;
    private String who;
}
