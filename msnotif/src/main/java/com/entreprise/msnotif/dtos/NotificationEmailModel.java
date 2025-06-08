package com.entreprise.msnotif.dtos;

import lombok.Data;

@Data
public class NotificationEmailModel {
    private String firstname;
    private String lastname;
    private String departementNom;
    private String noteId;
    private String type;
    private String message;
    private String email;
}
