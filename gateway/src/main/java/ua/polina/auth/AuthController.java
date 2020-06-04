package ua.polina.auth;

import com.netflix.client.ClientException;
import feign.FeignException;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    AuthService authService;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "logIn";
    }


    @RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("role");
            session.removeAttribute("token");
            model.addAttribute("logout", "Logged out");
            model.addAttribute("user", new User());
            return "login";
        }
        catch (FeignException.FeignClientException e){
            return "wrong-page";
        }
    }


    @PostMapping("/login")
    public String login(@RequestBody @ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        try {
            String token = authService.login(user);
            if (token != null) {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("token", "Bearer " + token);
                token = (String) request.getSession().getAttribute("token");
                HttpGet req = new HttpGet();
                req.setHeader("Authorization", String.format("%s%s", "Bearer ", token));
                if (authService.isClient(token)) {
                    httpSession.setAttribute("role", "CLIENT");
                } else if (authService.isInspector(token)) {
                    httpSession.setAttribute("role", "INSPECTOR");
                } else if (authService.isAdmin(token)) {
                    httpSession.setAttribute("role", "ADMIN");
                }
                return "index";
            } else
                return "redirect:/register-individual";
        } catch (FeignException.Unauthorized fe) {
            model.addAttribute("error", "Bad credentials");
            return "login";
        }

    }
}
