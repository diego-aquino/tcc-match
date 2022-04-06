package com.ufcg.psoft.tccmatch.services.notifications;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests.TCCGuidanceRequestAcceptedNotification;
import com.ufcg.psoft.tccmatch.models.notifications.tccGuidanceRequests.TCCGuidanceRequestCreatedNotification;
import com.ufcg.psoft.tccmatch.models.notifications.tccSubjects.TCCSubjectCreatedNotification;
import com.ufcg.psoft.tccmatch.models.notifications.tccSubjects.TCCSubjectInterestedNotification;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Coordinator;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.notifications.NotificationRepository;
import com.ufcg.psoft.tccmatch.services.users.coordinators.CoordinatorService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private StudentService studentService;

  @Autowired
  private CoordinatorService coordinatorService;

  public void handleTCCSubjectCreated(TCCSubject tccSubject) {
    List<Student> filteredStudents = studentService.filterByFieldsOfStudy(
      tccSubject.getFieldsOfStudy()
    );

    Set<TCCSubjectCreatedNotification> tccSubjectCreatedNotifications = new HashSet<>();
    for (Student student : filteredStudents) {
      TCCSubjectCreatedNotification notification = new TCCSubjectCreatedNotification(
        student,
        tccSubject,
        new Date()
      );
      tccSubjectCreatedNotifications.add(notification);
    }

    notificationRepository.saveAll(tccSubjectCreatedNotifications);
  }

  public void handleTCCSubjectInterested(TCCSubject tccSubject) {
    TCCSubjectInterestedNotification tccGuidanceInterestedNotification = new TCCSubjectInterestedNotification(
      tccSubject.getCreatedBy(),
      tccSubject,
      new Date()
    );
    notificationRepository.save(tccGuidanceInterestedNotification);
  }

  public void handleTCCGuidanceRequestCreated(
    TCCGuidanceRequest tccGuidanceRequest,
    User creatorOfTCCSubject
  ) {
    TCCGuidanceRequestCreatedNotification tccGuidanceRequestCreatedNotification = new TCCGuidanceRequestCreatedNotification(
      creatorOfTCCSubject,
      tccGuidanceRequest,
      new Date()
    );
    notificationRepository.save(tccGuidanceRequestCreatedNotification);
  }

  public void handleTCCGuidanceRequestAccepted(TCCGuidanceRequest tccGuidanceRequest) {
    List<Coordinator> coordinators = coordinatorService.listCoordinators();

    Set<TCCGuidanceRequestAcceptedNotification> tccGuidanceRequestAcceptedNotifications = new HashSet<>();
    for (Coordinator coordinator : coordinators) {
      TCCGuidanceRequestAcceptedNotification notification = new TCCGuidanceRequestAcceptedNotification(
        coordinator,
        tccGuidanceRequest,
        new Date()
      );
      tccGuidanceRequestAcceptedNotifications.add(notification);
    }

    notificationRepository.saveAll(tccGuidanceRequestAcceptedNotifications);
  }

  public List<Notification> listNotificationsOrderedByMostRecent(User userSentTo) {
    return listNotifications(userSentTo, Sort.by(Sort.Direction.DESC, "createdAt"));
  }

  public List<Notification> listNotifications(User userSentTo, Sort sort) {
    return notificationRepository.findAllBySentTo(userSentTo, sort);
  }
}
