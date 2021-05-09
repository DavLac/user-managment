package io.davlac.user.managment.controller;

import io.davlac.user.managment.service.UserService;
import io.davlac.user.managment.service.dto.UserCreationDTO;
import io.davlac.user.managment.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "50", required = false) int size) {
        return ResponseEntity.ok(userService.getUsers(pageNumber, size));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreationDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("{name}")
    public ResponseEntity<UserDTO> modifyUserByName(@PathVariable String name, @RequestBody UserCreationDTO userToModify) {
        return ResponseEntity.ok(userService.modifyUserByName(name, userToModify));
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteUserByName(@PathVariable String name) {
        userService.deleteUserByName(name);
        return ResponseEntity.accepted().build();
    }

}
