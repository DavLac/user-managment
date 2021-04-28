package io.davlac.user.managment.controller;

import io.davlac.user.managment.controller.error.NotFoundException;
import io.davlac.user.managment.domain.User;
import io.davlac.user.managment.repository.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    public User modifyUserById(@PathVariable long id, @RequestBody User userToModify) {
        User user = getUserById(id);
        user.setName(userToModify.getName());
        user.setPassword(userToModify.getPassword());
        return userRepository.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

}
