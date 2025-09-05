package com.example.identitymanagement.application;

import com.example.identitymanagement.infrastructure.UserRepository;
import com.example.identitymanagement.security.SecurityProperties;
import com.example.identitymanagement.ui.UserDetailsRequest;
import example.common.security.AppUser;
import example.common.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@ComponentScan(basePackages = {"example.common.security"})  //needed to locate JwtTokenUtil
@EntityScan(basePackages = {"example.common.security"})     //needed to locate AppUser
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public Optional<String> authenticate(UserDetailsRequest userDetailsRequest) {
        Random random = new SecureRandom();
        Optional<AppUser> userOpt = userRepository.findByUsername(userDetailsRequest.getUsername());

        if (userOpt.isEmpty()) {
            //Dummy password check to prevent timing attacks
            List<String> hashes = securityProperties.getHashes();
            String dummyHash = hashes.get(random.nextInt(hashes.size()));
            passwordEncoder.matches(userDetailsRequest.getPassword(), dummyHash);
            return Optional.empty();
        }

        AppUser user = userOpt.get();
        if (passwordEncoder.matches(userDetailsRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtTokenUtil.generateToken(user);
            return Optional.of(accessToken);
        }

        LOG.info("Password {} does not match with username {}", userDetailsRequest.getPassword(), userDetailsRequest.getUsername());
        return Optional.empty();
    }
}