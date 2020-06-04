package ua.polina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    //List<Client> findByClientTypeAndInspector(ClientType type, Inspector inspector);
    Optional<Client> findByUserId(Long userId);
    List<Client> findByInspectorId(Long inspectorId);
}
