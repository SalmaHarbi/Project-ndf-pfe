package com.entreprise.msexpensereport.notif;

import com.entreprise.msexpensereport.entities.Enum.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final String TOPIC = "note-approbation";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(Long noteId, NotificationType type) {
        String message = "{ \"noteId\": " + noteId + ", \"type\": \"" + type + "\" }";
        kafkaTemplate.send("note-approbation", message);
    }
}