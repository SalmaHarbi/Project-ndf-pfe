package com.entreprise.msexpensereport.notif;

import com.entreprise.msexpensereport.dtos.NotificationKafkaDTO;
import com.entreprise.msexpensereport.entities.Enum.NotificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final String TOPIC = "note-approbation";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendNotification(NotificationKafkaDTO dto) {
        try {
            String message = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("note-approbation", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
}}