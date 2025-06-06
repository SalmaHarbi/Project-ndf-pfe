package com.entreprise.msnotif.dtos;

import com.entreprise.msnotif.entities.Enum.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long id;

    private String message;
    private NotificationType type;
    private LocalDateTime dateCreation;
}
