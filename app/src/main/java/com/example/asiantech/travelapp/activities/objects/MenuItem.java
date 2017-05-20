package com.example.asiantech.travelapp.activities.objects;

/**
 * Created by phuong on 25/03/2017.
 */

public class MenuItem {
    private int icon;
    private String title;
    private int id;

    public MenuItem(int icon, String title, int id) {
        this.icon = icon;
        this.title = title;
        this.id = id;
    }

    public MenuItem() {
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
