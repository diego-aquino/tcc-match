package com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.dto.notifications.NotificationResponseDTO;
import com.ufcg.psoft.tccmatch.dto.notifications.tccGuidanceRequests.TCCGuidanceRequestNotificationResponseDTO;
import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TCCGuidanceRequestNotification extends Notification {

  @ManyToOne
  private TCCGuidanceRequest tccGuidanceRequest;

  protected TCCGuidanceRequestNotification(Notification.EventType eventType) {
    super(eventType);
  }

  public TCCGuidanceRequestNotification(
    Notification.EventType eventType,
    User sentTo,
    TCCGuidanceRequest tccGuidanceRequest,
    Date createdAt
  ) {
    super(eventType, sentTo, createdAt);
    this.tccGuidanceRequest = tccGuidanceRequest;
  }

  public TCCGuidanceRequest getTCCGuidanceRequest() {
    return tccGuidanceRequest;
  }

  public NotificationResponseDTO toDTO() {
    return new TCCGuidanceRequestNotificationResponseDTO(this);
  }
}
