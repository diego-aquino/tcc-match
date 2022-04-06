package com.ufcg.psoft.tccmatch.dto.notifications.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.dto.notifications.NotificationResponseDTO;
import com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests.TCCGuidanceRequestNotification;

public class TCCGuidanceRequestNotificationResponseDTO extends NotificationResponseDTO {

  private Long tccGuidanceRequestId;

  public TCCGuidanceRequestNotificationResponseDTO(TCCGuidanceRequestNotification notification) {
    super(notification);
    this.tccGuidanceRequestId = notification.getTCCGuidanceRequest().getId();
  }

  public Long getTccGuidanceRequestId() {
    return tccGuidanceRequestId;
  }
}
