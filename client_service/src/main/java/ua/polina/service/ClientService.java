package ua.polina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.polina.entity.Client;
import ua.polina.repository.ClientRepository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientByUser(Long userId) {
        return clientRepository.findByUserId(userId);
    }

    public List<Client> geClientsByInspector(Long inspectorId){
        return clientRepository.findByInspectorId(inspectorId);
    }


    @Transactional
    public Client update(Client client, Long inspectorId) {
        Long id = client.getId();

        return clientRepository.findById(id)
                .map(clientFromDb -> {
                    clientFromDb.setInspectorId(inspectorId);
                    return clientRepository.save(clientFromDb);
                }).orElseGet(() -> clientRepository.save(client));
    }

//    public List<Client> getByClientType(ClientType clientType, Inspector inspector) {
//        return clientRepository.findByClientTypeAndInspector(clientType, inspector);
//    }
}
