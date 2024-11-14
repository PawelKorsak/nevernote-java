package org.nevernote.login_controller;

import org.nevernote.dto.UserDTO;

public class LoginResponse {
    UserDTO user;
    String token;
    public LoginResponse(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }
    public UserDTO getUser() {
        return user;
    }
    public String getToken() {
        return token;
    }
}
