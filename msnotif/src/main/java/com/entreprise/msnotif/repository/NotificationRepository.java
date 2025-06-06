package com.entreprise.msnotif.repository;

import com.entreprise.msnotif.entities.Enum.NotificationType;
import com.entreprise.msnotif.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByType(NotificationType type);
}
