package com.entreprise.msnotif.entities;

import com.entreprise.msnotif.entities.Enum.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime dateCreation;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

}