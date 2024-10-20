package org.nevernote.mapper;

import org.nevernote.dto.UserDTO;
import org.nevernote.entity.Users;

public class UserMapper {

    public static UserDTO toUserDTO(Users users) {
        UserDTO dto = new UserDTO();
        dto.setId(users.getId());
        dto.setUsername(users.getUsername());
        dto.setPassword(users.getPasswordHash());
        return dto;

    }

    public static Users toUser(UserDTO dto) {
        Users users = new Users();
        users.setId(dto.getId());
        users.setUsername(dto.getUsername());
        return users;
    }
}
