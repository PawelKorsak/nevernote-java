package org.nevernote.login_controller;

import org.nevernote.dto.UserDTO;
import org.nevernote.nevernote_server.JwtService;
import org.nevernote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public LoginController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                System.out.println("Login Successful");
                UserDTO userDTO = userService.getUserByUsername(user.getUsername());
                LoginResponse loginResponse = new LoginResponse(userDTO, jwtService.generateToken(user.getUsername()));
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            } else {
                System.out.println(authentication.getPrincipal());
                System.out.println("Login failed!");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}