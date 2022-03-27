package com.ufcg.psoft.tccmatch.models.notifications.tccSubjects;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class TCCSubjectCreatedNotification extends TCCSubjectNotification {

  private static final EventType EVENT_TYPE = Notification.EventType.TCC_SUBJECT_CREATED;

  protected TCCSubjectCreatedNotification() {
    super(EVENT_TYPE);
  }

  public TCCSubjectCreatedNotification(User sentTo, TCCSubject tccSubject, Date createdAt) {
    super(EVENT_TYPE, sentTo, tccSubject, createdAt);
  }
}
