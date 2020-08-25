package ua.polina.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.dto.SignUpLegalEntityDto;
import ua.polina.entity.Client;
import ua.polina.entity.ClientType;
import ua.polina.entity.Individual;
import ua.polina.entity.LegalEntity;
import ua.polina.repository.ClientRepository;
import ua.polina.repository.LegalEntityRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public LegalEntityService(LegalEntityRepository legalEntityRepository, ClientRepository clientRepository) {
        this.legalEntityRepository = legalEntityRepository;
        this.clientRepository = clientRepository;
    }

    public List<LegalEntity> getAllLegalEntities() {
        return legalEntityRepository.findAll();
    }

    public Optional<LegalEntity> getById(Long id) {
        return legalEntityRepository.findById(id);
    }

    public LegalEntity saveNewLegalEntity(SignUpLegalEntityDto legalEntityDto, Long userId) {
        if(legalEntityRepository.existsByEdrpou(legalEntityDto.getEdrpou())) return null;
        if(legalEntityRepository.existsByMfo(legalEntityDto.getMfo()))  return null;
        Client client = clientRepository.save(Client.builder()
                .clientType(ClientType.LEGAL_ENTITY)
                .inspectorId(legalEntityDto.getInspectorId())
                .userId(userId)
                .build());
        return legalEntityRepository.save(LegalEntity.builder()
                .address(legalEntityDto.getAddress())
                .clientId(client.getId())
                .edrpou(legalEntityDto.getEdrpou())
                .mfo(legalEntityDto.getMfo())
                .name(legalEntityDto.getName())
                .build());
            }


    public LegalEntity update(LegalEntity legalEntity) {
        Long id = legalEntity.getId();

        return legalEntityRepository.findById(id)
                .map(individualFromDB -> {
                    individualFromDB.setClientId(legalEntity.getClientId());
                    individualFromDB.setName(legalEntity.getName());
                    individualFromDB.setEdrpou(legalEntity.getEdrpou());
                    individualFromDB.setMfo(legalEntity.getMfo());
                    individualFromDB.setAddress(legalEntity.getAddress());

                    return legalEntityRepository.save(individualFromDB);
                }).orElseGet(() -> legalEntityRepository.save(legalEntity));
    }

    public void deleteById(Long id) {
        legalEntityRepository.deleteById(id);
    }

    public List<LegalEntity> getLegalsByInspector(Long inspectorId){
        return legalEntityRepository.findByInspector(inspectorId);
    }

    public Optional<LegalEntity> getByClientId(Long id){
        return legalEntityRepository.findByClientId(id);
    }
}