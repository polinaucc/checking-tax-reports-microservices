package ua.polina.controller;

import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.polina.dto.UserDto;
import ua.polina.entity.RoleType;
import ua.polina.entity.User;
import ua.polina.security.CurrentUser;
import ua.polina.security.JwtTokenProvider;
import ua.polina.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public User registerUser(@RequestBody UserDto user){
        return userService.register(user);
    }

    @PostMapping("/sign-up-insp")
    public User registerUserInsp(@RequestBody UserDto user){
        System.out.println(user);
        User u = userService.registerInsp(user);
        System.out.println(u);
        return u;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.login(user);
    }

    @PreAuthorize("hasAuthority(T(ua.polina.entity.RoleType).ADMIN.getAuthority())")
    @RequestMapping(path="/isAdmin", method = RequestMethod.GET)
    public Boolean isAdmin() {
        return true;
    }

    @PreAuthorize("hasAuthority(T(ua.polina.entity.RoleType).CLIENT.getAuthority())")
    @RequestMapping(path="/isClient", method = RequestMethod.GET)
    public Boolean isClient() {
        return true;
    }

    @PreAuthorize("hasAuthority(T(ua.polina.entity.RoleType).INSPECTOR.getAuthority())")
    @RequestMapping(path="/isInspector", method = RequestMethod.GET)
    public Boolean isInspector() {
        return true;
    }

    @PostMapping("/current-user")
    @ResponseBody
    public Long getCurrentUser(@RequestBody String token){
        return tokenProvider.getUserIdFromToken(token.substring(7, token.length()));
        }

}
