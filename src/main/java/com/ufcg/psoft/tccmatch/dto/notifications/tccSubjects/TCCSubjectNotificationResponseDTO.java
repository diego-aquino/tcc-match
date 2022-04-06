package com.ufcg.psoft.tccmatch.dto.notifications.tccSubjects;

import com.ufcg.psoft.tccmatch.dto.notifications.NotificationResponseDTO;
import com.ufcg.psoft.tccmatch.models.notifications.tccSubjects.TCCSubjectNotification;

public class TCCSubjectNotificationResponseDTO extends NotificationResponseDTO {

  private Long tccSubjectId;

  public TCCSubjectNotificationResponseDTO(TCCSubjectNotification notification) {
    super(notification);
    this.tccSubjectId = notification.getTCCSubject().getId();
  }

  public Long getTccSubjectId() {
    return tccSubjectId;
  }
}
