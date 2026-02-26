package com.mauricio.moneyflow.service;


import com.mauricio.moneyflow.dto.UserRequestDTO;
import com.mauricio.moneyflow.dto.UserResponseDTO;
import com.mauricio.moneyflow.entity.User;
import com.mauricio.moneyflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO create(UserRequestDTO userRequestDTO) {

        User user = User.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();

        userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());

    }

    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        return  users.stream()
                .map(user -> new UserResponseDTO(user.getId(),user.getName(),user.getEmail()))
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());

    }


    public UserResponseDTO update(UUID id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());

        userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public void deleteById(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        userRepository.deleteById(id);
    }
}
