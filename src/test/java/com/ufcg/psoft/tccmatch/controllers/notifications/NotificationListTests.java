package com.ufcg.psoft.tccmatch.controllers.notifications;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests.TCCGuidanceRequestAcceptedNotification;
import com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests.TCCGuidanceRequestCreatedNotification;
import com.ufcg.psoft.tccmatch.models.notifications.tccSubjects.TCCSubjectCreatedNotification;
import com.ufcg.psoft.tccmatch.models.notifications.tccSubjects.TCCSubjectInterestedNotification;
import com.ufcg.psoft.tccmatch.models.users.Coordinator;
import com.ufcg.psoft.tccmatch.repositories.notifications.NotificationRepository;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class NotificationListTests extends IntegrationTests {

  private Coordinator coordinator;
  private String coordinatorToken;

  @Autowired
  private UserService<Coordinator> userService;

  @Autowired
  private NotificationRepository notificationRepository;

  @BeforeEach
  void beforeEach() {
    Optional<Coordinator> optionalCoordinator = userService.findUserByEmail(
      defaultCoordinatorEmail
    );

    if (optionalCoordinator.isEmpty()) {
      throw new UnsupportedOperationException("Could not find the default coordinator.");
    }

    coordinator = optionalCoordinator.get();
    coordinatorToken = loginProgrammaticallyWithDefaultCoordinator();
  }

  @Test
  void listNotificationsSendToAUserOrderedByCreatedAt() throws Exception {
    List<Notification> notifications = createSampleNotifications();

    makeGetNotificationsRequest(coordinatorToken)
      .andExpect(status().isOk())
      .andExpect(jsonNotificationId(notifications, 0))
      .andExpect(jsonNotificationEventType(Notification.EventType.TCC_SUBJECT_CREATED, 0))
      .andExpect(jsonNotificationSentTo(notifications, 0))
      .andExpect(jsonNotificationCreatedAt(notifications, 0))
      .andExpect(jsonNotificationId(notifications, 1))
      .andExpect(jsonNotificationEventType(Notification.EventType.TCC_SUBJECT_INTERESTED, 1))
      .andExpect(jsonNotificationSentTo(notifications, 1))
      .andExpect(jsonNotificationCreatedAt(notifications, 1))
      .andExpect(jsonNotificationId(notifications, 2))
      .andExpect(jsonNotificationEventType(Notification.EventType.TCC_GUIDANCE_REQUEST_CREATED, 2))
      .andExpect(jsonNotificationSentTo(notifications, 2))
      .andExpect(jsonNotificationCreatedAt(notifications, 2))
      .andExpect(jsonNotificationId(notifications, 3))
      .andExpect(jsonNotificationEventType(Notification.EventType.TCC_GUIDANCE_REQUEST_ACCEPTED, 3))
      .andExpect(jsonNotificationSentTo(notifications, 3))
      .andExpect(jsonNotificationCreatedAt(notifications, 3));
  }

  private List<Notification> createSampleNotifications() {
    List<Date> dates = List.of(new Date(10000), new Date(15000), new Date(7500), new Date(25000));

    List<Notification> notifications = List.of(
      new TCCSubjectCreatedNotification(coordinator, dates.get(3)),
      new TCCSubjectInterestedNotification(coordinator, dates.get(1)),
      new TCCGuidanceRequestCreatedNotification(coordinator, dates.get(0)),
      new TCCGuidanceRequestAcceptedNotification(coordinator, dates.get(2))
    );
    notificationRepository.saveAll(notifications);

    return notifications;
  }

  private ResultActions makeGetNotificationsRequest(String token) throws Exception {
    return mvc.perform(authenticated(get("/api/notifications"), token));
  }

  private ResultMatcher jsonNotificationId(List<Notification> notifications, int index) {
    Notification notification = notifications.get(index);
    return jsonPath(String.format("$.[%d].id", index)).value(notification.getId().intValue());
  }

  private ResultMatcher jsonNotificationEventType(Notification.EventType eventType, int index) {
    return jsonPath(String.format("$.[%d].eventType", index)).value(eventType.toString());
  }

  private ResultMatcher jsonNotificationSentTo(List<Notification> notifications, int index) {
    Notification notification = notifications.get(index);
    return jsonPath(String.format("$.[%d].sentTo", index))
      .value(notification.getSentTo().getId().intValue());
  }

  private ResultMatcher jsonNotificationCreatedAt(List<Notification> notifications, int index) {
    Notification notification = notifications.get(index);
    return jsonPath(String.format("$.[%d].createdAt", index))
      .value(notification.getCreatedAt().toInstant().toString());
  }
}
