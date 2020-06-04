package ua.polina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.dto.InspectorDto;
import ua.polina.entity.Inspector;
import ua.polina.repository.InspectorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InspectorService {
    InspectorRepository inspectorRepository;

    @Autowired
    public InspectorService(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    public Inspector getByUserId(Long userId){
        return inspectorRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("No inspector"));
    }

    public List<Inspector> getAllInspectors() {
        return inspectorRepository.findAll();

    }

    public Optional<Inspector> getById(Long id) {
        return inspectorRepository.findById(id);
    }

    public Inspector saveNewInspector(InspectorDto inspectorDto, Long userId) {

        return inspectorRepository.save(Inspector.builder()
        .firstName(inspectorDto.getFirstName())
        .middleName(inspectorDto.getSecondName())
        .lastName(inspectorDto.getSurname())
        .employmentDate(inspectorDto.getEmploymentDate())
        .userId(userId)
        .build());
    }
}
