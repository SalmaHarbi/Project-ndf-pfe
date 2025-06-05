package com.entreprise.msnotif.notif;

import com.entreprise.msnotif.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "note-approbation", groupId = "msnotification-group")
    public void listen(String message) {
        notificationService.saveNotification(message);
    }
}