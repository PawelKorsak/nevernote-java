package org.nevernote.login_controller;

import org.nevernote.dto.UserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                System.out.println("Login Successful");
                return "Login successful!";
            } else {
                System.out.println(authentication.getPrincipal());
                System.out.println("Login failed!");
                return "Invalid credentials!";
            }
        } catch (AuthenticationException e) {
            System.out.println(e);
            return "Invalid username or password!";
        }
    }
}