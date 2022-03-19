package com.ufcg.psoft.tccmatch.services.notifications;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.notifications.NotificationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  public List<Notification> listNotificationsOrderedByMostRecent(User userSentTo) {
    return listNotifications(userSentTo, Sort.by(Sort.Direction.DESC, "createdAt"));
  }

  public List<Notification> listNotifications(User userSentTo, Sort sort) {
    return notificationRepository.findAllBySentTo(userSentTo, sort);
  }
}
