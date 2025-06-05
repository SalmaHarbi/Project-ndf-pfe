package com.entreprise.msnotif.services;


import com.entreprise.msnotif.dtos.NotificationDto;
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

    public NotificationDto saveNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDateCreation(LocalDateTime.now());
        notification = notificationRepository.save(notification);
        return notificationMapper.toDto(notification);
    }

    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }
}