package com.ufcg.psoft.tccmatch.repositories.notifications;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {}
