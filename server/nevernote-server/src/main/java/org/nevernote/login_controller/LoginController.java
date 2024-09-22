package org.nevernote.login_controller;

import org.springframework.web.bind.annotation.*;
import org.nevernote.login_controller.LoginRequest;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            return "Login successful";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}