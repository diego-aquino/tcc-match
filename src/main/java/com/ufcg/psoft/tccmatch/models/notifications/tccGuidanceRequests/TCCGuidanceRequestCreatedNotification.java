package com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class TCCGuidanceRequestCreatedNotification extends Notification {

  private static final EventType EVENT_TYPE = Notification.EventType.TCC_GUIDANCE_REQUEST_CREATED;

  protected TCCGuidanceRequestCreatedNotification() {
    super(EVENT_TYPE);
  }

  public TCCGuidanceRequestCreatedNotification(User sentTo, Date createdAt) {
    super(EVENT_TYPE, sentTo, createdAt);
  }
}
