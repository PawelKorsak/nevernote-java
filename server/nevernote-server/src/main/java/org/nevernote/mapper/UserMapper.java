package org.nevernote.mapper;

import org.nevernote.dto.UserDTO;
import org.nevernote.entity.Users;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDTO(Users users) {
        UserDTO dto = new UserDTO();
        dto.setId(users.getId());
        dto.setUsername(users.getUsername());
        dto.setPassword(users.getPasswordHash());
        dto.setNotes(
                Optional.ofNullable(users.getNotes())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(NoteMapper::toNoteDTO)
                        .collect(Collectors.toList())
        );

        return dto;

    }

    public static Users toUser(UserDTO dto) {
        Users users = new Users();
        users.setId(dto.getId());
        users.setUsername(dto.getUsername());
        users.setNotes(
                Optional.ofNullable(dto.getNotes())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(NoteMapper::toNote)
                        .collect(Collectors.toList())
        );
        return users;
    }
}
