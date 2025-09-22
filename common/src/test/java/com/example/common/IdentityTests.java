package com.example.common;

import com.example.common.domain.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdentityTests {

    @Test
    @DisplayName("An identity must not be blank")
    void test01(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Identity("");
        });
    }

    @Test
    @DisplayName("A valid identity will return its own id if it holds a valid UUID")
    void test02(){
        //Generate controlled UUID rather than rely on UniqueIDFactory dependency
        final String VALID_UUID = "0000-00-00-00-000001";
        Identity identity = new Identity(VALID_UUID);

        assertDoesNotThrow(() -> {
            UUID.fromString(identity.id());
        });
    }
}

