package com.azet.vehicle.controller;

import com.azet.vehicle.model.User;
import com.azet.vehicle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping({"","v1"})
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/users")
    User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable("id") long id) {
        return userRepository.findById(id)
            .map(user -> {
                user.setRoles(newUser.getRoles());
                user.setFirstName(newUser.getFirstName());
                user.setLastName(newUser.getLastName());
                user.setEmail(newUser.getEmail());
                return userRepository.save(user);
            })
            .orElseGet(() -> {
                newUser.setId(id);
                return userRepository.save(newUser);
            });
    }
}
