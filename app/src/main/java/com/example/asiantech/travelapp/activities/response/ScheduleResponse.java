package com.example.asiantech.travelapp.activities.response;

import com.example.asiantech.travelapp.activities.objects.TourSchedule;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/05/2017.
 */
@Getter
@Setter
public class ScheduleResponse {
    @JsonProperty("schedules")
    private List<TourSchedule> schedules;
    @JsonProperty("title")
    private String title;
}
