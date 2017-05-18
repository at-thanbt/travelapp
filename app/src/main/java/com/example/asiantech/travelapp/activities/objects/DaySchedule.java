package com.example.asiantech.travelapp.activities.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phuong on 16/05/2017.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC, suppressConstructorProperties = true)
public class DaySchedule {
    private String idDaySchedule;
    private String location;
    private String note;
    private String time;
    private double lat;
    private double lng;
    private String content;
    private String title;
}
