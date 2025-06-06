package com.entreprise.msnotif.notif;

import com.entreprise.msnotif.dtos.NotificationDto;
import com.entreprise.msnotif.dtos.NotificationKafkaDTO;
import com.entreprise.msnotif.services.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "note-approbation", groupId = "msnotification-group")
    public void listen(String message) {
        try {
            NotificationKafkaDTO dto = objectMapper.readValue(message, NotificationKafkaDTO.class);
            notificationService.saveNotification(dto);
        } catch (Exception e) {
            // Log l'erreur ou gère-la selon le besoin
            System.err.println("Erreur lors du parsing du message Kafka : " + e.getMessage());
        }
    }
}