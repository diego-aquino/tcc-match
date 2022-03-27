package com.ufcg.psoft.tccmatch.models.notifications.tccSubjects;

import com.ufcg.psoft.tccmatch.dto.notifications.NotificationResponseDTO;
import com.ufcg.psoft.tccmatch.dto.notifications.tccSubjects.TCCSubjectNotificationResponseDTO;
import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TCCSubjectNotification extends Notification {

  @ManyToOne
  private TCCSubject tccSubject;

  protected TCCSubjectNotification(Notification.EventType eventType) {
    super(eventType);
  }

  public TCCSubjectNotification(
    Notification.EventType eventType,
    User sentTo,
    TCCSubject tccSubject,
    Date createdAt
  ) {
    super(eventType, sentTo, createdAt);
    this.tccSubject = tccSubject;
  }

  public TCCSubject getTCCSubject() {
    return tccSubject;
  }

  public NotificationResponseDTO toDTO() {
    return new TCCSubjectNotificationResponseDTO(this);
  }
}
