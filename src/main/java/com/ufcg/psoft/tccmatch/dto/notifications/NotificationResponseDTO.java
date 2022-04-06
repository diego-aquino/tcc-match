package com.ufcg.psoft.tccmatch.dto.notifications;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NotificationResponseDTO {

  private Long id;
  private Notification.EventType eventType;
  private Long sentTo;
  private Date createdAt;

  public NotificationResponseDTO(Notification notification) {
    this.id = notification.getId();
    this.eventType = notification.getEventType();
    this.sentTo = notification.getSentTo().getId();
    this.createdAt = notification.getCreatedAt();
  }

  public Long getId() {
    return id;
  }

  public Notification.EventType getEventType() {
    return eventType;
  }

  public Long getSentTo() {
    return sentTo;
  }

  public String getCreatedAt() {
    return createdAt.toInstant().toString();
  }

  public static List<NotificationResponseDTO> fromNotifications(List<Notification> notifications) {
    return Arrays.asList(
      notifications.stream().map(Notification::toDTO).toArray(NotificationResponseDTO[]::new)
    );
  }
}
