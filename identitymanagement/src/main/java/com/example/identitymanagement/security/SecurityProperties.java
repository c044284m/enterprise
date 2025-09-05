package com.example.identitymanagement.security;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class SecurityProperties {
    private List<String> hashes = new ArrayList<>();
}
