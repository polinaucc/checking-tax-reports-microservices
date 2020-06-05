package ua.polina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.dto.ReportDto;
import ua.polina.entity.Report;
import ua.polina.entity.Status;
import ua.polina.repository.ReportRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;


    public Optional<Report> getById(Long id) {
        return reportRepository.findById(id);
    }

    public Report saveNewReport(ReportDto reportDto, Long clientId) {
        Report report = Report.builder()
                .clientId(clientId)
                .date(LocalDate.now())
                .comment(reportDto.getComment())
                .status(Status.NOT_CHECKED)
                .build();
        return reportRepository.save(report);
    }

    public List<Report> getByClient(Long clientId) {
        return reportRepository.findByClientId(clientId);
    }

    public List<Report> getByStatus(Status status, Long clientId) {
        return reportRepository.findByStatusAndClientId(status, clientId);
    }

    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }

    public List<Report> getAll(){
        return reportRepository.findAll();
    }

    public Report update(Report report, Status status) {
        Long id = report.getId();

        return reportRepository.findById(id)
                .map(reportFromDB -> {
                    reportFromDB.setDate(LocalDate.now());
                    reportFromDB.setStatus(status);
                    reportFromDB.setComment(report.getComment());

                    return reportRepository.save(reportFromDB);
                }).orElseGet(() -> reportRepository.save(report));
    }

}
