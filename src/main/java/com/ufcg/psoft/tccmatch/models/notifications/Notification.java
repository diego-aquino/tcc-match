package com.ufcg.psoft.tccmatch.models.notifications;

import com.ufcg.psoft.tccmatch.dto.notifications.NotificationResponseDTO;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {

  public enum EventType {
    TCC_SUBJECT_CREATED,
    TCC_SUBJECT_INTERESTED,
    TCC_GUIDANCE_REQUEST_CREATED,
    TCC_GUIDANCE_REQUEST_ACCEPTED,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @ManyToOne
  private User sentTo;

  private Date createdAt;

  protected Notification() {}

  protected Notification(EventType eventType) {
    this(eventType, null, null);
  }

  protected Notification(EventType eventType, User sentTo, Date createdAt) {
    this.eventType = eventType;
    this.sentTo = sentTo;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public EventType getEventType() {
    return eventType;
  }

  public User getSentTo() {
    return sentTo;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public abstract NotificationResponseDTO toDTO();
}
