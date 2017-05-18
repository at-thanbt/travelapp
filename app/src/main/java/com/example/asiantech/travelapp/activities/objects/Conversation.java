package com.example.asiantech.travelapp.activities.objects;

import java.io.Serializable;

import lombok.Data;

@Data
public class Conversation implements Serializable {
    private String id;
    private String anotherGuyName;
    private boolean isGroup;
}
