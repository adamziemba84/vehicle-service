package com.azet.vehicle.security.initialize;

import com.azet.vehicle.model.User;
import com.azet.vehicle.repository.UserRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Profile("!production")
@Component
public class UserDataInitializer implements SmartInitializingSingleton {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterSingletonsInstantiated() {

        List<User> users = createUsers("john", "donald", "trevor", "max", "Liam", "Noah", "James", "Lincoln", "Lucas", "Emma", "Olivia", "Sophia", "Evelyn");

        userRepository.saveAll(users);
    }

    public List<User> createUsers(String... users) {
        return Arrays.stream(users)
                .map(user -> User.builder()
                        .firstName(user.concat(" first"))
                        .lastName(user.concat(" last"))
                        .email(user.concat("@test.com"))
                        .enabled(true)
                        .password(passwordEncoder.encode("demo"))
                        .roles(Collections.singletonList("ROLE_ADMIN"))
                        .lastLogin(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
                    .collect(Collectors.toList());
    }
}
