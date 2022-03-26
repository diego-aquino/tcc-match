package com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class TCCGuidanceRequestAcceptedNotification extends TCCGuidanceRequestNotification {

  private static final EventType EVENT_TYPE = Notification.EventType.TCC_GUIDANCE_REQUEST_ACCEPTED;

  protected TCCGuidanceRequestAcceptedNotification() {
    super(EVENT_TYPE);
  }

  public TCCGuidanceRequestAcceptedNotification(
    User sentTo,
    TCCGuidanceRequest tccGuidanceRequest,
    Date createdAt
  ) {
    super(EVENT_TYPE, sentTo, tccGuidanceRequest, createdAt);
  }
}
