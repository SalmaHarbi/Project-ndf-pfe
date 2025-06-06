package com.entreprise.msnotif.services;


import com.entreprise.msnotif.dtos.NotificationDto;
import com.entreprise.msnotif.dtos.NotificationKafkaDTO;
import com.entreprise.msnotif.entities.Enum.NotificationType;
import com.entreprise.msnotif.entities.Notification;
import com.entreprise.msnotif.mappers.NotificationMapper;
import com.entreprise.msnotif.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    public Notification saveNotification(NotificationKafkaDTO dto) {
        Notification notification = new Notification();
        notification.setMessage("Note de frais " + dto.getType().toLowerCase() + " : id=" + dto.getNoteId());
        notification.setDateCreation(LocalDateTime.now());
        notification.setType(NotificationType.valueOf(dto.getType()));
        return notificationRepository.save(notification);
    }

    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationDto> getApprovedNotifications() {
        return notificationRepository.findByType(NotificationType.APPROUVEE)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationDto> getRefundedNotifications() {
        return notificationRepository.findByType(NotificationType.REMBOURSEE)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }
}