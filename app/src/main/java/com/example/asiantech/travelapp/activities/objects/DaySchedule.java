package com.example.asiantech.travelapp.activities.objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by phuong on 16/05/2017.
 */
@Getter
@Setter
public class DaySchedule {
    private String idDaySchedule;
    private String location;
    private String note;
    private String time;
    private double lat;
    private double lng;
}
