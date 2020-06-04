package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.polina.entity.Report;
import ua.polina.entity.Status;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByClientId(Long clientId);
    List<Report> findByStatusAndClientId(Status status, Long clientId);
}
