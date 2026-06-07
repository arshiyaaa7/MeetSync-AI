package com.hintro.meetingintelligence.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(
            String email
    ) {

        try {

            log.info(
                    "Generating JWT token for email: {}",
                    email
            );

            String token =
                    JWT.create()
                            .withSubject(email)
                            .withIssuedAt(
                                    new Date()
                            )
                            .withExpiresAt(
                                    new Date(
                                            System.currentTimeMillis()
                                                    + 86400000
                                    )
                            )
                            .sign(
                                    Algorithm.HMAC256(secret)
                            );

            log.info(
                    "JWT token generated successfully for email: {}",
                    email
            );

            return token;

        } catch (Exception e) {

            log.error(
                    "Failed to generate JWT token",
                    e
            );

            throw new RuntimeException(
                    "Failed to generate JWT token"
            );
        }
    }
}