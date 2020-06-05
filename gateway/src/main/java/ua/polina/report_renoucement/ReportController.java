package ua.polina.report_renoucement;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.auth.AuthService;
import ua.polina.client.Client;
import ua.polina.client.IndividualService;
import ua.polina.inspector.Inspector;
import ua.polina.inspector.InspectorService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {
    @Autowired
    ReportRenouncementService reportRenouncementService;

    @Autowired
    AuthService authService;

    @Autowired
    IndividualService individualService;

    @Autowired
    InspectorService inspectorService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/create-new-report")
    public String getNewReportPage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            model.addAttribute("report", new ReportDto());
            return "new-report";
        } else {
            return "access-denied";
        }
    }

    @GetMapping("/edit/{id}")
    public String getUpdateForm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            Report report = reportRenouncementService.getReportById(id);
            model.addAttribute("report", report);
            return "update-report";
        }
        return "access-denied";
    }


    @MessageMapping("/message")
    //   @SendTo("/topic/mural")
    // @PostMapping("/create-new-report")
//    public String addNewReport(@RequestBody ReportDto reqReport) {
    public void addNewReport(String reqReport) {
        Gson gson = new Gson();
        GsonReport gsonReport = gson.fromJson(reqReport, GsonReport.class);
        String comment = gsonReport.message;
        String token = gsonReport.token;
        System.out.println(comment);
        System.out.println(token);
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            Long userId = authService.getCurrentUser(token);
            Long clientId = individualService.getCurrentClient(userId);
            Long inspectorId = individualService.getInspectorByClient(clientId);
            Report report = reportRenouncementService.addNewReport(new ApiReport(new ReportDto(comment), clientId));
            this.template.convertAndSend("/topic/mural/"+inspectorId, report);
            //  return reportRenouncementService.getAllReports();
        }
    }

//    @PostMapping("/create-new-report")
//    public String addNewReport(@ModelAttribute("report") ReportDto reqReport, BindingResult bindingResult, Model model, HttpServletRequest request) {
//        String token = (String) request.getSession().getAttribute("token");
//        HttpGet req = new HttpGet();
//        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
//        if (authService.isClient(token)) {
//            Long userId = authService.getCurrentUser(token);
//            Long clientId = individualService.getCurrentClient(userId);
//            reportRenouncementService.addNewReport(new ApiReport(reqReport, clientId));
//            return "redirect:/client/my-reports";
//        } else {
//            return "redirect:/register-individual";
//        }
//    }

    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable("id") Long id, @ModelAttribute("report") Report report,
                               BindingResult result, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            if (result.hasErrors()) {
                return "update-report";
            }
            reportRenouncementService.updateReport(id, report);
            return "redirect:/my-reports";

        } else
            return "access-denied";
    }

    @GetMapping("/delete-report/{id}")
    public String deleteIndividual(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        reportRenouncementService.deleteReport(id);
        return "redirect:/my-reports";
    }

    @GetMapping("/my-reports")
    public String getReportsPage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            Long userId = authService.getCurrentUser(token);
            Long clientId = individualService.getCurrentClient(userId);
            List<Report> reports = reportRenouncementService.getCurrentReports(clientId);
            model.addAttribute("reports", reports);
            return "get-reports-page";
        } else {
            return "access-denied";
        }
    }

    @GetMapping("/inspector-reports")
    public String getReportsPageInspector(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Long userId = authService.getCurrentUser(token);
            Inspector inspector = inspectorService.getInspectorByUserId(userId);
            List<Client> clients = individualService.getClientByInspector(inspector.getId());
            List<Report> reports = new ArrayList<>();
            for (Client c : clients) {
                reports.addAll(reportRenouncementService.getReportByClientId(c.getId()));
            }
            model.addAttribute("filterObj", new FilterObj());
            model.addAttribute("accepted", "NOT");
            model.addAttribute("reports", reports);
            return "get-reports-page-inspector";
        } else {
            return "access-denied";
        }
    }

    @GetMapping("/inspector-reports-filter")
    public String addFilters(@ModelAttribute("filterObj") FilterObj filterObj, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Long userId = authService.getCurrentUser(token);
            Inspector inspector = inspectorService.getInspectorByUserId(userId);
            List<Client> clients = individualService.getClientByInspector(inspector.getId());
            List<Report> reports = new ArrayList<>();
            for (Client c : clients) {
                if (filterObj.accepted) {
                    reports.addAll(reportRenouncementService.getByStatus(new StatusApi(Status.ACCEPTED, c.getId())));
                }
                if (filterObj.notChecked) {
                    reports.addAll(reportRenouncementService.getByStatus(new StatusApi(Status.NOT_CHECKED, c.getId())));
                }
                if (filterObj.rejected) {
                    reports.addAll(reportRenouncementService.getByStatus(new StatusApi(Status.REJECTED, c.getId())));
                }
                if (!filterObj.rejected && !filterObj.notChecked && !filterObj.accepted)
                    return "redirect:/inspector-reports";
            }

            model.addAttribute("reports", reports);
            return "get-reports-page-inspector";
        } else {
            return "access-denied";
        }
    }

    @GetMapping("/accept-report/{id}")
    public String acceptReport(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Report report = reportRenouncementService.getReportById(id);
            report.setStatus(Status.ACCEPTED);
            reportRenouncementService.updateReport(id, report);
            return "redirect:/inspector-reports";
        } else {
            return "access-denied";
        }
    }

    @GetMapping("/reject-report/{id}")
    public String getRejectReportForm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Report report = reportRenouncementService.getReportById(id);
            request.getSession().setAttribute("report", report);
            model.addAttribute("renouncement", new RenouncementDto());
            return "new-reject";
        } else
            return "access-denied";
    }

    @PostMapping("/reject-report")
    public String rejectReport(@ModelAttribute("renouncement") RenouncementDto renouncementDTO,
                               BindingResult bindingResult, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Report report = (Report) request.getSession().getAttribute("report");
            report.setStatus(Status.REJECTED);
            reportRenouncementService.updateReport(report.getId(), report);
            Renouncement renouncement = Renouncement.builder()
                    .reportId(report.getId())
                    .date(LocalDate.now())
                    .reason(renouncementDTO.getReason())
                    .build();
            reportRenouncementService.addNewRenouncement(renouncement);
            return "redirect:/inspector-reports";
        } else
            return "access-denied";
    }

    @GetMapping("/info-report/{id}")
    public String getInfo(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        Report report = reportRenouncementService.getReportById(id);
        model.addAttribute("renouncements", reportRenouncementService.getRenouncementByReport(report.getId()));
        return "get-info";
    }
}

class GsonReport {
    String token;
    String message;
}
