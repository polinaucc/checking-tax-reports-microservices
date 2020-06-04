package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.entity.Renouncement;

import java.util.List;

public interface RenouncementRepository extends JpaRepository<Renouncement, Long> {
    List<Renouncement> findByReportId(Long reportId);

}
