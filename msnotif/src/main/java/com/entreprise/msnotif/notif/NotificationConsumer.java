package com.entreprise.msnotif.notif;

import com.entreprise.msnotif.dtos.NotificationDto;
import com.entreprise.msnotif.dtos.NotificationKafkaDTO;
import com.entreprise.msnotif.dtos.UserDtoRs;
import com.entreprise.msnotif.services.EmailService;
import com.entreprise.msnotif.services.NotificationService;
import com.entreprise.msnotif.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "note-approbation", groupId = "msnotification-group")
    public void listen(String message) {
        try {
            NotificationKafkaDTO dto = objectMapper.readValue(message, NotificationKafkaDTO.class);
            UserDtoRs user = userService.getUserById(dto.getUserId());
            if (user != null && user.getEmail() != null) {
                String subject = "Notification : " + dto.getType();
                String text = "Bonjour " + user.getPrenom() + ", votre note de frais #" + dto.getNoteId()
                        + " a été " + dto.getType().toLowerCase() + ".";
                emailService.sendSimpleEmail(user.getEmail(), subject, text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}