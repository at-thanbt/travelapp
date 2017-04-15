package com.example.asiantech.travelapp.activities.objects;

/**
 * Created by phuong on 08/04/2017.
 */

public class TouristItem {
    private long id;
    private String tourist;
    private String icon;

    public TouristItem() {
    }

    public TouristItem(long id, String tourist, String icon) {

        this.id = id;
        this.tourist = tourist;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTourist() {
        return tourist;
    }

    public void setTourist(String tourist) {
        this.tourist = tourist;
    }
}
