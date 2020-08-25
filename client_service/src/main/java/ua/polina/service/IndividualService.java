package ua.polina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.dto.SignUpIndividualDto;
import ua.polina.entity.Client;
import ua.polina.entity.ClientType;
import ua.polina.entity.Individual;
import ua.polina.repository.ClientRepository;
import ua.polina.repository.IndividualRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class IndividualService {
    private final IndividualRepository individualRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public IndividualService(IndividualRepository individualRepository, ClientRepository clientRepository) {
        this.individualRepository = individualRepository;
        this.clientRepository = clientRepository;
    }

    public List<Individual> getAllIndividuals() {
        return individualRepository.findAll();
    }

    public Optional<Individual> getById(Long id) {
        return individualRepository.findById(id);
    }

    @Transactional
    public Individual saveNewIndividual(SignUpIndividualDto individualDto, Long userId) {
        if(individualRepository.existsByPassport(individualDto.getPassport())) return null;
        Client client = clientRepository.save(Client.builder()
                .clientType(ClientType.INDIVIDUAL)
                .inspectorId(individualDto.getInspectorId())
                .userId(userId)
                .build());

        return individualRepository.save(Individual.builder()
                .firstName(individualDto.getFirstName())
                .middleName(individualDto.getSecondName())
                .lastName(individualDto.getSurname())
                .address(individualDto.getAddress())
                .identCode(individualDto.getIdentCode())
                .clientId(client.getId())
                .passport(individualDto.getPassport())
                .build());
    }

    public List<Individual> getIndividualsByInspector(Long inspectorId){
        return individualRepository.findByInspector(inspectorId);
    }

    public Optional<Individual> getByClientId(Long id){
        return individualRepository.findByClientId(id);
    }

    public Optional<Individual> getByPassport(String passport) {
        return individualRepository.findByPassport(passport);
    }

    public List<Individual> getByAddress(String address) {
        return individualRepository.findByAddress(address);
    }

    public Optional<Individual> getByIdentCode(String identCode) {
        return individualRepository.findByIdentCode(identCode);
    }

    public void deleteById(Long id) {
        individualRepository.deleteById(id);
    }

    @Transactional
    public void update(Individual individual) {
        Long id = individual.getId();

        individualRepository.findById(id)
                .map(individualFromDB -> {
                    individualFromDB.setLastName(individual.getLastName());
                    individualFromDB.setFirstName(individual.getFirstName());
                    individualFromDB.setMiddleName(individual.getMiddleName());
                    individualFromDB.setPassport(individual.getPassport());
                    individualFromDB.setIdentCode(individual.getIdentCode());
                    individualFromDB.setAddress(individual.getAddress());
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                    return individualRepository.save(individualFromDB);
                }).orElseGet(() -> individualRepository.save(individual));
    }
}
