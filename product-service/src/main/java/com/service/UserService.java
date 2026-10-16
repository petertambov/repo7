package com.service;

import com.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dto.UserDto;
import com.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String getUserNotFoundError(Long id) {
        return String.format("Пользлватель с id = %d не найден",id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(getUserNotFoundError(id)));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsernameStartingWith(String prefix) {
        return userRepository.findByUsernameStartingWith(prefix);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(String username) {
        User user = new User(username);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException(getUserNotFoundError(id));
        }
        userRepository.deleteById(id);
    }
    
    private UserDto userDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }

    public UserDto getUserByIdDto(Long id) {
        return userRepository.findById(id)
                .map(this::userDto).orElseThrow(() -> 
                 new EntityNotFoundException(getUserNotFoundError(id)));
    }
    
    public List<UserDto> getAllUsersDto() {
        return userRepository.findAll().stream()
                .map(this::userDto)
                .collect(Collectors.toList());
    }
          
}