package com.azet.vehicle.security.controller;

import com.azet.vehicle.security.dto.AuthRequest;
import com.azet.vehicle.security.dto.AuthResponse;
import com.azet.vehicle.model.User;
import com.azet.vehicle.security.JwtGenerator;
import com.azet.vehicle.security.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthRestController {
    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/generate-token")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        UserDetails user = userDetailsService.loadUserByUsername(authRequest.getEmail());

        log.info("user found [{}] on user [{}]", authRequest.getEmail(), user);

        if(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            String token = jwtGenerator.generateToken((User) user);
            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }
}
