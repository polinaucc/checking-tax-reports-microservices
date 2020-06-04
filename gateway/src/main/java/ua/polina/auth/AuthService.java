package ua.polina.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "auth")
public interface AuthService {
    @PostMapping("/sign-up")
    @ResponseBody
    User registerUser(@RequestBody UserDto user);

    @PostMapping("/sign-up-insp")
    @ResponseBody
    User registerUserInsp(@RequestBody UserDto user);

    @PostMapping("/login")
    @ResponseBody
    String login(@RequestBody User user);

    @RequestMapping(path="/isAdmin", method = RequestMethod.GET)
    @ResponseBody
    Boolean isAdmin(@RequestHeader("Authorization") String token);

    @RequestMapping(path="/isClient", method = RequestMethod.GET)
    @ResponseBody
    Boolean isClient(@RequestHeader("Authorization") String token);

    @RequestMapping(path="/isInspector", method = RequestMethod.GET)
    @ResponseBody
    Boolean isInspector(@RequestHeader("Authorization") String token);

    @PostMapping("/current-user")
    @ResponseBody
    Long getCurrentUser(@RequestBody String token);
}
