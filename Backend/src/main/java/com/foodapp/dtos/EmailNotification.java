package com.foodapp.dtos;

import lombok.Data;

@Data
public class EmailNotification {
    private String to;
    private String subject;
    private String message;
}