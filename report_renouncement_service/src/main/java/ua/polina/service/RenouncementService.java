package ua.polina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.entity.Renouncement;
import ua.polina.repository.RenouncementRepository;

import java.util.List;

@Service
public class RenouncementService {
    @Autowired
    RenouncementRepository renouncementRepository;

    public Renouncement save(Renouncement renouncement) {
        return renouncementRepository.save(renouncement);
    }

    public List<Renouncement> getByReportId(Long reportId) {
        return renouncementRepository.findByReportId(reportId);
    }
}
