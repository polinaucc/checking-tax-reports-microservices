package ua.polina.report_renoucement;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "report-renouncement")
public interface ReportRenouncementService {
    @PostMapping("/create-new-report")
    @ResponseBody
    Report addNewReport(@RequestBody ApiReport apiReport);

    @PostMapping("/get-by-status")
    @ResponseBody
    List<Report> getByStatus(@RequestBody StatusApi status);

    @PostMapping("/update/{id}")
    @ResponseBody
    void updateReport(@PathVariable("id") Long id, @RequestBody Report report);

    @PostMapping("/current-reports")
    @ResponseBody
    List<Report> getCurrentReports(@RequestBody Long clientId);

    @PostMapping("/get-by-id")
    @ResponseBody
    Report getReportById(@RequestBody Long reportId);

    @PostMapping("/get-by-client-id")
    @ResponseBody
    List<Report> getReportByClientId(@RequestBody Long clientId);

    @PostMapping("/create-new-renouncement")
    @ResponseBody
    void addNewRenouncement(@RequestBody Renouncement renouncement);

    @GetMapping("/delete-report/{id}")
    @ResponseBody
    void deleteReport(@PathVariable("id") Long id);

    @PostMapping
    @ResponseBody
    List<Report> getAllReports();

    @PostMapping("/renouncement-by-report")
    @ResponseBody
    List<Renouncement> getRenouncementByReport(@RequestBody Long reportId);
}
