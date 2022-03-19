package com.ufcg.psoft.tccmatch.repositories.notifications;

import com.ufcg.psoft.tccmatch.models.notifications.Notification;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findAllBySentTo(User userSentTo);

  List<Notification> findAllBySentTo(User userSentTo, Sort sort);
}
