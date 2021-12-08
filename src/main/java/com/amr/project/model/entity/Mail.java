package com.amr.project.model.entity;


import lombok.Data;

@Data
public class Mail {
    private String to;
    private String from;
    private String subject;
    private String message;
}