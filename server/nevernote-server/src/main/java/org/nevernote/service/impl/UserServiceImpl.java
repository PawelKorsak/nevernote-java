package org.nevernote.service.impl;

import org.nevernote.dto.UserDTO;
import org.nevernote.entity.Note;
import org.nevernote.entity.User;
import org.nevernote.exception.ResourceNotFoundException;
import org.nevernote.mapper.NoteMapper;
import org.nevernote.mapper.UserMapper;
import org.nevernote.repository.UserRepository;
import org.nevernote.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {this.userRepository=userRepository;}

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toUser(userDTO);
        user.setPasswordHash(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        User savedUser = userRepository.save(user);
        return UserMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = getUserFromDb(id);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        User actualUser = getUserFromDb(id);
        actualUser.setUsername(user.getUsername());
        User updatedUser = userRepository.save(actualUser);
        return UserMapper.toUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserFromDb(id);
        userRepository.delete(user);
    }
    private User getUserFromDb(long id) {
        return userRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("User with given id does not exist."));
    }
}
