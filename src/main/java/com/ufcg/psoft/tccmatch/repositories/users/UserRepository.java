package com.ufcg.psoft.tccmatch.repositories.users;

import java.util.List;
import java.util.Optional;
import com.ufcg.psoft.tccmatch.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<GenericUser extends User> extends JpaRepository<GenericUser, Long> {
  Optional<GenericUser> findByEmail(String email);
  List<GenericUser> findAllByType(User.Type type);
}
