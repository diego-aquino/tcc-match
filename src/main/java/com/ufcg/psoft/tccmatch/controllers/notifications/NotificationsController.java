package com.ufcg.psoft.tccmatch.controllers.notifications;

import com.ufcg.psoft.tccmatch.dto.notifications.NotificationResponseDTO;
import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.notifications.NotificationService;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<NotificationResponseDTO>> listNotifications() {
    User authenticatedUser = authenticationService.getAuthenticatedUser();

    List<Notification> notifications = notificationService.listNotificationsOrderedByMostRecent(
      authenticatedUser
    );

    return new ResponseEntity<>(
      NotificationResponseDTO.fromNotifications(notifications),
      HttpStatus.OK
    );
  }
}
