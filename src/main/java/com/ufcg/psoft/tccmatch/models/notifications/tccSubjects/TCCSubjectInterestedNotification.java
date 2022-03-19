package com.ufcg.psoft.tccmatch.models.notifications.tccSubjects;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class TCCSubjectInterestedNotification extends Notification {

  private static final EventType EVENT_TYPE = Notification.EventType.TCC_SUBJECT_INTERESTED;

  protected TCCSubjectInterestedNotification() {
    super(EVENT_TYPE);
  }

  public TCCSubjectInterestedNotification(User sentTo, Date createdAt) {
    super(EVENT_TYPE, sentTo, createdAt);
  }
}
