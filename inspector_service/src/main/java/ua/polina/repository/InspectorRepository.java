package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.entity.Inspector;

import java.util.Optional;

public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    Optional<Inspector> findByUserId(Long userId);
}
