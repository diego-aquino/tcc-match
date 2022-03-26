package com.ufcg.psoft.tccmatch.models.notifications.tccSubjects;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TCCSubjectCreatedNotification extends Notification {

  private static final EventType EVENT_TYPE = Notification.EventType.TCC_SUBJECT_CREATED;

  @ManyToOne
  private TCCSubject tccSubject;

  protected TCCSubjectCreatedNotification() {
    super(EVENT_TYPE);
  }

  public TCCSubjectCreatedNotification(User sentTo, TCCSubject tccSubject, Date createdAt) {
    super(EVENT_TYPE, sentTo, createdAt);
    this.tccSubject = tccSubject;
  }

  public TCCSubject getTccSubject() {
    return tccSubject;
  }
}
