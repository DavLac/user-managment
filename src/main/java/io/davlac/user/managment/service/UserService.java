package io.davlac.user.managment.service;

import io.davlac.user.managment.controller.error.BadRequestException;
import io.davlac.user.managment.controller.error.NotFoundException;
import io.davlac.user.managment.domain.User;
import io.davlac.user.managment.repository.UserRepository;
import io.davlac.user.managment.service.dto.UserCreationDTO;
import io.davlac.user.managment.service.dto.UserDTO;
import io.davlac.user.managment.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserByName(String name) {
        User user = getUserEntityByName(name);
        return userMapper.toDto(user);
    }

    public Page<UserDTO> getUsers(int pageNumber, int size) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, size));

        List<UserDTO> userDtoList = userPage.getContent().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(userDtoList, userPage.getPageable(), userPage.getTotalPages());
    }

    public UserDTO createUser(UserCreationDTO userToCreate) {
        checkIfUserAlreadyExist(userToCreate.getName());

        User user = userMapper.toEntity(userToCreate);
        User userSaved = userRepository.save(user);
        return userMapper.toDto(userSaved);
    }

    public UserDTO modifyUserByName(String name, UserCreationDTO userDtoToModify) {
        User user = getUserEntityByName(name);

        if (!name.equals(userDtoToModify.getName())) {
            checkIfUserAlreadyExist(userDtoToModify.getName());
        }

        User userToModify = userMapper.toEntity(userDtoToModify);
        userToModify.setId(user.getId());
        User userModified = userRepository.save(userToModify);
        return userMapper.toDto(userModified);
    }

    public void deleteUserByName(String name) {
        User user = getUserEntityByName(name);
        userRepository.deleteById(user.getId());
    }

    private User getUserEntityByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private void checkIfUserAlreadyExist(String username) {
        Optional<User> userOptional = userRepository.findByName(username);

        if (userOptional.isPresent()) {
            throw new BadRequestException("User name already exist");
        }
    }
}
