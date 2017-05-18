package com.example.asiantech.travelapp.activities.objects;

import lombok.Data;

@Data
public class Message {
    private String id;
    private String content;
    private String senderId;
    private long timestamp;
}
