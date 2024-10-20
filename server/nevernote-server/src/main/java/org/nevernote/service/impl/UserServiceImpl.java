package org.nevernote.service.impl;

import org.nevernote.dto.UserDTO;
import org.nevernote.entity.Users;
import org.nevernote.exception.ResourceNotFoundException;
import org.nevernote.mapper.UserMapper;
import org.nevernote.repository.UserRepository;
import org.nevernote.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {this.userRepository=userRepository;}

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Users users = UserMapper.toUser(userDTO);
        users.setPasswordHash(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        Users savedUsers = userRepository.save(users);
        return UserMapper.toUserDTO(savedUsers);
    }

    @Override
    public UserDTO getUser(Long id) {
        Users user = getUserFromDb(id);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        Users actualUsers = getUserFromDb(id);
        actualUsers.setUsername(user.getUsername());
        Users updatedUsers = userRepository.save(actualUsers);
        return UserMapper.toUserDTO(updatedUsers);
    }

    @Override
    public void deleteUser(Long id) {
        Users users = getUserFromDb(id);
        userRepository.delete(users);
    }
    private Users getUserFromDb(long id) {
        return userRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("User with given id does not exist."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .build();
    }
}
