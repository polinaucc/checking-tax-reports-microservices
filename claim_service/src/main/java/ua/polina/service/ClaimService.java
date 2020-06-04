package ua.polina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.dto.ClaimDto;
import ua.polina.entity.Claim;
import ua.polina.entity.Status;
import ua.polina.repository.ClaimRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    ClaimRepository claimRepository;

    @Autowired
    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public Claim saveNewClaim(ClaimDto claimDto, Long clientId, Long inspectorId) {
        Claim claim = Claim.builder()
                .clientId(clientId)
                .inspectorId(inspectorId)
                .reason(claimDto.getReason())
                .status(Status.NOT_CHECKED)
                .build();
        return claimRepository.save(claim);
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public List<Claim> getClaimsByClient(Long clientId) {
        return claimRepository.findByClientId(clientId);
    }
//
//    public List<Claim> getClaimsByInspector(Inspector inspector) {
//        return claimRepository.findByInspector(inspector);
//    }
//
    @Transactional
    public Claim update(Claim claim, Status status) {
        Long id = claim.getId();

        return claimRepository.findById(id)
                .map(claimFromDB -> {
                    claimFromDB.setStatus(status);
                    return claimRepository.save(claimFromDB);
                }).orElseGet(() -> claimRepository.save(claim));
    }

    public List<Claim> getByStatus(Status status){
        return claimRepository.findByStatus(status);
    }
}
