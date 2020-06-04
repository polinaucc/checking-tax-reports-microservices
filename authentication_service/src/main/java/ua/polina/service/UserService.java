package ua.polina.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ua.polina.dto.JwtAuthenticationDto;
import ua.polina.dto.UserDto;
import ua.polina.entity.RoleType;
import ua.polina.entity.User;
import ua.polina.exception.CustomException;
import ua.polina.repository.UserRepository;
import ua.polina.security.JwtTokenProvider;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    public String login(User u) {
        String username = u.getEmail();
        String password = u.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public User register(UserDto userDto) {
        if (!userRepository.existsUserByEmail(userDto.getEmail())) {
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setAuthorities(new HashSet<>());
            user.getAuthorities().add(RoleType.CLIENT);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            System.out.println("CLIENT");
            return userRepository.save(user);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User registerInsp(UserDto userDto) {
        if (!userRepository.existsUserByEmail(userDto.getEmail())) {
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setAuthorities(new HashSet<>());
            user.getAuthorities().add(RoleType.INSPECTOR);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            System.out.println("INSPECTOR");
            return userRepository.save(user);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
