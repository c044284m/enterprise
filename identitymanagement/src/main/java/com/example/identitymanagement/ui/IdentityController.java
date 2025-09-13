package com.example.identitymanagement.ui;

import com.example.identitymanagement.application.UserService;
import com.example.common.security.*;
import com.example.common.ui.CommonController;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@ComponentScan(basePackages = {"example.common.security"})  //needed to locate RateLimiterService
@RestController
public class IdentityController extends CommonController {
    private UserService userService;
    private final RateLimiterService rateLimiterService;
    private final HttpServletRequest request;

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody UserDetailsRequest userDetailsRequest) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip);

        if (bucket.tryConsume(1)) {
            Optional<String> token = userService.authenticate(userDetailsRequest);

            return token.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Invalid credentials")));
        }
        //number of attempts has been exceeded
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(Map.of("error", "Too many attempts. Try again later."));//make parsing of responses easier
    }

    //we could add a refresh token and then also add invalidate token following that
}