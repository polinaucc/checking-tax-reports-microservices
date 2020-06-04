package ua.polina.claim;

import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.polina.auth.AuthService;
import ua.polina.client.Client;
import ua.polina.client.IndividualService;
import ua.polina.inspector.Inspector;
import ua.polina.inspector.InspectorService;
import ua.polina.report_renoucement.FilterObj;
import ua.polina.report_renoucement.Status;
import ua.polina.report_renoucement.StatusApi;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClaimController {
    @Autowired
    ClaimService claimService;

    @Autowired
    AuthService authService;

    @Autowired
    IndividualService individualService;

    @Autowired
    InspectorService inspectorService;

    @GetMapping("/create-claim")
    public String getAddClaimPage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            model.addAttribute("claim", new ClaimDto());
            return "new-claim";
        } else return "access-denied";
    }

    @PostMapping("/create-claim")
    public String saveNewClaim(@ModelAttribute ClaimDto reqClaim, Model model, BindingResult result, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            Long userId = authService.getCurrentUser(token);
            Long clientId = individualService.getCurrentClient(userId);
            Long inspectorId = individualService.getInspectorByClient(clientId);
            claimService.saveNewClaim(new ApiClaim(reqClaim, clientId, inspectorId));
            return "redirect:/my-claims";
        } else return "access-denied";
    }

    @GetMapping("/my-claims")
    public String getClaimsPage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isClient(token)) {
            Long userId = authService.getCurrentUser(token);
            Long clientId = individualService.getCurrentClient(userId);
            List<Claim> claims = claimService.getClaimsPage(clientId);
            model.addAttribute("claims", claims);
            return "get-claims-page";
        } else return "access-denied";
    }

    @GetMapping("/claims")
    public String getAllClaims(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            List<Claim> claims = claimService.getClaims();
            model.addAttribute("claims", claims);
            model.addAttribute("filterObj", new FilterObj());
            return "get-claims-page-admin";
        } else return "access-denied";
    }

    @GetMapping("/reject-claim/{id}")
    public String rejectClaim(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            claimService.rejectClaim(id);
            return "redirect:/claims";
        } else return "access-denied";
    }

    @GetMapping("/accept-claim/{id}")
    public String getAcceptClaimForm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            Claim claim = claimService.getAcceptClaimForm(id);
            Long clientId = claim.getClientId();
            System.out.println(clientId);
            Client client = individualService.getClientById(clientId);
            request.getSession().setAttribute("client", client);
            Long inspectorId = individualService.getInspectorByClient(clientId);
            Inspector inspector = inspectorService.getByInspectorById(inspectorId);
            List<Inspector> inspectorList = inspectorService.getInspectors();
            inspectorList.remove(inspector);
            System.out.println(client);
            model.addAttribute("inspectors", inspectorList);
            model.addAttribute("changeInsp", new ChangeInspectorDto());
            return "process-claim-page";
        } else return "access-denied";
    }

    @PostMapping("/accept-claim")
    public String acceptClaim(@ModelAttribute("changeInsp") ChangeInspectorDto changeInspectorDTO,
                              BindingResult result, Model model, HttpServletRequest request) {
        System.out.println(changeInspectorDTO);
        individualService.acceptClaim(new ApiChangeInspector(changeInspectorDTO, (Client) request.getSession().getAttribute("client")));
        return "redirect:/claims";
    }

    @PostMapping("/claims-filter")
    public String filterClaims(@ModelAttribute("filterObj") FilterObj filterObj, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            List<Claim> claims = new ArrayList<>();
            if (filterObj.accepted) {
                claims.addAll(claimService.getClaimsByStatus(ua.polina.claim.Status.ACCEPTED));
            }
            if (filterObj.notChecked) {
                claims.addAll(claimService.getClaimsByStatus(ua.polina.claim.Status.NOT_CHECKED));
            }
            if (filterObj.rejected) {
                claims.addAll(claimService.getClaimsByStatus(ua.polina.claim.Status.REJECTED));
            }
            if (!filterObj.rejected && !filterObj.notChecked && !filterObj.accepted)
                return "redirect:/claims";
            model.addAttribute("claims", claims);
            return "get-claims-page-admin";
        } else return "access-denied";
    }


}



