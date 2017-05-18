package com.example.asiantech.travelapp.activities.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/05/2017.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC, suppressConstructorProperties = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TourSchedule {
    private String idTourSchedule;
    private String idTour;
    private String description;
    private String date;
}
