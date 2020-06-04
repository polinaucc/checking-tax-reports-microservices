package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);

}
