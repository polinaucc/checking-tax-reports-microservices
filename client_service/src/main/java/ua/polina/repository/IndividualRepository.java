package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.polina.entity.Individual;

import java.util.List;
import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
    Optional<Individual> findByPassport(String passport);
    Optional<Individual> findByIdentCode(String identCode);
    List<Individual> findByAddress(String address);
    @Query(
            value = "SELECT i from Individual i WHERE i.clientId IN (SELECT c.id FROM Client c WHERE c.inspectorId=?1)"
    )
    List<Individual> findByInspector(Long inspectorId);
    Boolean existsByPassport(String passport);


}
