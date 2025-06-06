package com.entreprise.msnotif.controllers;

import com.entreprise.msnotif.dtos.NotificationDto;
import com.entreprise.msnotif.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<NotificationDto> getAll() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/approuvees")
    public List<NotificationDto> getApproved() {
        return notificationService.getApprovedNotifications();
    }

    @GetMapping("/remboursees")
    public List<NotificationDto> getRefunded() {
        return notificationService.getRefundedNotifications();
    }
}