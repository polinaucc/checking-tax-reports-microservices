package ua.polina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.polina.dto.ApiReport;
import ua.polina.dto.StatusApi;
import ua.polina.entity.Renouncement;
import ua.polina.entity.Report;
import ua.polina.entity.Status;
import ua.polina.service.RenouncementService;
import ua.polina.service.ReportService;

import java.util.List;

@Controller
public class ReportController {
    @Autowired
    ReportService reportService;

    @Autowired
    RenouncementService renouncementService;

    //    @MessageMapping("/message")
//    @SendTo("/topic/mural")
    @PostMapping("/create-new-report")
    @ResponseBody
    public Report addNewReport(@RequestBody ApiReport apiReport) {
        return reportService.saveNewReport(apiReport.getReportDto(), apiReport.getClientId());
    }

    @PostMapping("/get-by-status")
    @ResponseBody
    public List<Report> getByStatus(@RequestBody StatusApi statusApi){
        return reportService.getByStatus(statusApi.getStatus(), statusApi.getClientId());
    }

    @GetMapping("/delete-report/{id}")
    @ResponseBody
    public void deleteReport(@PathVariable("id") Long id){
        reportService.deleteById(id);


    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public void updateReport(@PathVariable("id") Long id, @RequestBody Report report) {
        if (report.getStatus() != Status.ACCEPTED) {
            reportService.update(report, Status.REJECTED);
        } else reportService.update(report, Status.ACCEPTED);
    }

    @PostMapping("/current-reports")
    @ResponseBody
    public List<Report> getCurrentReports(@RequestBody Long clientId) {
        return reportService.getByClient(clientId);
    }

    @PostMapping("/create-new-renouncement")
    @ResponseBody
    public void addNewRenouncement(@RequestBody Renouncement renouncement) {
        renouncementService.save(renouncement);
    }

    @PostMapping("/renouncement-by-report")
    @ResponseBody
    public List<Renouncement> getRenouncementByReport(@RequestBody Long reportId) {
        List<Renouncement> ren = renouncementService.getByReportId(reportId);
        for (Renouncement r : ren) {
            System.out.println(r);
        }
        return renouncementService.getByReportId(reportId);
    }


    @PostMapping("/get-by-id")
    @ResponseBody
    public Report getReportById(@RequestBody Long reportId) {
        return reportService.getById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("No report"));
    }

    @PostMapping("/get-by-client-id")
    @ResponseBody
    public List<Report> getReportByClientId(@RequestBody Long clientId) {
        return reportService.getByClient(clientId);
    }

}
