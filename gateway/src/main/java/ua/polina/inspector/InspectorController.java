package ua.polina.inspector;

import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.polina.auth.AuthService;
import ua.polina.auth.User;
import ua.polina.auth.UserDto;
import ua.polina.client.Individual;
import ua.polina.client.IndividualService;
import ua.polina.client.LegalEntity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class InspectorController {
    @Autowired
    InspectorService inspectorService;

    @Autowired
    AuthService authService;

    @Autowired
    IndividualService individualService;

    @GetMapping("/save-inspector")
    public String getRegisterInspectorPage(Model model) {
        model.addAttribute("signInspector", new InspectorDto());
        model.addAttribute("standardDate", LocalDate.now());
        return "RegisterInspector";
    }

    @PostMapping("/save-inspector")
    public String saveInspector(@Valid @ModelAttribute("signInspector") InspectorDto reqInspector,
                                BindingResult bindingResult, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            if (bindingResult.hasErrors()) {
                System.out.println("ERRRORRRR");
                return "RegisterInspector";
            }
            System.out.println(reqInspector.getUserEmail());
            User user = authService.registerUserInsp(UserDto.builder()
                    .email(reqInspector.getUserEmail())
                    .password(reqInspector.getPassword())
                    .build());
            System.out.println(user);
            inspectorService.saveInspector(new ApiInspector(reqInspector, user.getId()));
            return "redirect:/inspectors";
        }
        return "redirect:/register-individual";
    }

    @GetMapping("/inspector/individuals")
    public String allIndividuals(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Long userId = authService.getCurrentUser(token);
            Inspector inspector = inspectorService.getInspectorByUserId(userId);
            List<Individual> individuals = individualService.getIndividualsByInspector(inspector.getId());
            model.addAttribute("individuals", individuals);
            return "get-individuals-page-inspector";
        }
        return "redirect:/register-individual";
    }

    @GetMapping("/inspector/legals")
    public String allLegals(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isInspector(token)) {
            Long userId = authService.getCurrentUser(token);
            Inspector inspector = inspectorService.getInspectorByUserId(userId);
            List<LegalEntity> legals = individualService.getLegalsByInspector(inspector.getId());
            model.addAttribute("legals", legals);
            return "get-legals-page-inspector";
        }
        return "redirect:/register-individual";
    }

    @GetMapping("/inspectors")
    public String getAllInspectors(Model model, HttpServletRequest request){
        List<Inspector> inspectors = inspectorService.getInspectors();
        model.addAttribute("inspectors", inspectors);
        return "get-inspectors-page";
    }
}
