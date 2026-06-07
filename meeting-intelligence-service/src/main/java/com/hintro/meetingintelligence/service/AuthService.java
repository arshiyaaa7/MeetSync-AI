package com.hintro.meetingintelligence.service;

import com.hintro.meetingintelligence.dtos.auth.AuthResponse;
import com.hintro.meetingintelligence.dtos.auth.LoginRequest;
import com.hintro.meetingintelligence.dtos.auth.RegisterRequest;
import com.hintro.meetingintelligence.entity.User;
import com.hintro.meetingintelligence.exception.ResourceNotFoundException;
import com.hintro.meetingintelligence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public void register(
            RegisterRequest request
    ) {

        log.info(
                "Registering user with email: {}",
                request.getEmail()
        );

        boolean exists =
                userRepository.existsByEmail(
                        request.getEmail()
                );

        if (exists) {

            log.error(
                    "User already exists with email: {}",
                    request.getEmail()
            );

            throw new IllegalArgumentException(
                    "Email already registered"
            );
        }

        User user = User.builder()
                .email(
                        request.getEmail()
                )
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .build();

        userRepository.save(user);

        log.info(
                "User registered successfully with email: {}",
                request.getEmail()
        );
    }

    public AuthResponse login(
            LoginRequest request
    ) {

        log.info(
                "Login attempt for email: {}",
                request.getEmail()
        );

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(() -> {

                    log.error(
                            "User not found with email: {}",
                            request.getEmail()
                    );

                    return new ResourceNotFoundException(
                            "User not found"
                    );
                });

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            log.error(
                    "Invalid credentials for email: {}",
                    request.getEmail()
            );

            throw new IllegalArgumentException(
                    "Invalid credentials"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        log.info(
                "User logged in successfully with email: {}",
                request.getEmail()
        );

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}