package org.nevernote.user_controller;

import org.nevernote.dto.UserDTO;
import org.nevernote.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Build add note REST API
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") long id) {
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }
}
