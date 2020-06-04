package ua.polina.client;


import com.netflix.ribbon.proxy.annotation.Http;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.auth.ApiRequest;
import ua.polina.auth.AuthService;
import ua.polina.auth.User;
import ua.polina.auth.UserDto;
import ua.polina.inspector.Inspector;
import ua.polina.inspector.InspectorService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class ClientController {
    @Autowired
    IndividualService individualService;

    @Autowired
    AuthService authService;

    @Autowired
    InspectorService inspectorService;


    @GetMapping("/individuals")
    public String getIndividualsPage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            List<Individual> individuals = individualService.getAllIndividuals();
            model.addAttribute("individuals", individuals);
            return "get-individuals-page";
        } else return "access-denied";
    }


    @GetMapping("/delete-individual/{id}")
    public String deleteIndividual(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            individualService.deleteIndividual(id);
            return "redirect:/individuals";
        } else return "access-denied";
    }

    @GetMapping("/edit-individual/{id}")
    public String getUpdateForm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            Individual individual = individualService.getUpdateForm(id);
            model.addAttribute("individual", individual);
            return "update-individual";
        } else return "access-denied";
    }

    @PostMapping("/update-individual/{id}")
    public String updateIndividual(@PathVariable("id") Long id, Individual individual,
                                   BindingResult result, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            System.out.println(individual.getFirstName());
            if (result.hasErrors()) {
                System.out.println("ERRoRRRRRRRRR");
                individual.setId(id);
                return "update-individual";
            }
            individualService.updateIndividual(id, individual);
            return "redirect:/individuals";
        } else return "access-denied";
    }

    @GetMapping("/register-individual")
    public String getRegisterPage(Model model) {
        List<Inspector> inspectors = inspectorService.getInspectors();
        model.addAttribute("inspectors", inspectors);
        model.addAttribute("sign", new SignUpIndividualDto());
        model.addAttribute("error", null);
        model.addAttribute("error2", null);
        return "RegisterIndividual";
    }

    @PostMapping("/register-individual")
    public String registerIndividual(@Valid @ModelAttribute("sign") SignUpIndividualDto individualDto,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "RegisterIndividual";
        }
        User user = authService.registerUser(UserDto.builder()
                .email(individualDto.getEmail())
                .password(individualDto.getPassword())
                .build());
        if (user != null) {
            if (individualService.registerIndividual(new ApiRequest<SignUpIndividualDto>(user.getId(), individualDto)) == null) {
                model.addAttribute("error2", "Passport is already in use");
            }
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Username is already in use");
            return "RegisterIndividual";
        }
    }

    @GetMapping("/legal-entities")
    public String getLegalsPage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            List<LegalEntity> legalEntities = individualService.getAllEntities();
            model.addAttribute("legals", legalEntities);
            return "get-legals-page";
        } else return "access-denied";
    }


    @GetMapping("/delete-legal-entity/{id}")
    public String deleteLegal(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        HttpGet req = new HttpGet();
        req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
        if (authService.isAdmin(token)) {
            individualService.deleteLegalEntity(id);
            return "redirect:/legal-entities";
        } else return "access-denied";
    }

    @GetMapping("/register-legal-entity")
    public String getRegisterPageLegal(Model model, HttpServletRequest request) {
        List<Inspector> inspectors = inspectorService.getInspectors();
        model.addAttribute("inspectors", inspectors);
        model.addAttribute("sign", new SignUpLegalEntityDto());
        model.addAttribute("error", null);
        model.addAttribute("error2", null);
        return "register-legal-entity";
    }

    //
    @PostMapping("/register-legal-entity")
    public String registerLegal(@Valid @ModelAttribute("sign") @RequestBody SignUpLegalEntityDto legalEntityDto,
                                BindingResult bindingResult, Model model) throws URISyntaxException {
        if (bindingResult.hasErrors()) {
            return "register-legal-entity";
        }
        User user = authService.registerUser(UserDto.builder()
                .email(legalEntityDto.getEmail())
                .password(legalEntityDto.getPassword())
                .build());
        if (individualService.registerLegal(new ApiRequest<SignUpLegalEntityDto>(user.getId(), legalEntityDto)) == null) {
            model.addAttribute("errpr2", "Edrpou or MFO is in use");
        }

        return "redirect:/login";

    }
}
