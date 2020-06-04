package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.polina.entity.Individual;
import ua.polina.entity.LegalEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {
    @Query(
            value = "SELECT l from LegalEntity l WHERE l.clientId IN (SELECT c.id FROM Client c WHERE c.inspectorId=?1)"
    )
    List<LegalEntity> findByInspector(Long inspectorId);
    Boolean existsByEdrpou(String edrpou);
    Boolean existsByMfo(String mfo);
}
