package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.entity.Claim;
import ua.polina.entity.Status;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByClientId(Long clientId);
    List<Claim> findByStatus(Status status);
}
