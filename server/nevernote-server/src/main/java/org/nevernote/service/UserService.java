package org.nevernote.service;

import org.nevernote.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO user);
    void deleteUser(Long id);
}
