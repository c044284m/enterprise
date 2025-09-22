package com.example.common;

import com.example.common.domain.FullName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FullNameTests {
    private FullName generateFullName(){
        //new object - with same data each call
        return new FullName("first1","surname1");
    }

    private String generateValueOfLength(int length){
        char[] chars = new char[length];
        for (int i = 0;i<length;i++){
            chars[i]='a';
        }
        return new String(chars);
    }

    @Test
    @DisplayName("Two names with the same values are considered the same")
    void test01(){
        FullName fullName1 = generateFullName();

        FullName fullName2 = generateFullName();

        assertEquals(fullName1,fullName2);
    }

    @Test
    @DisplayName("A full name cannot contain a blank first name")
    void test02(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName("","surname1");
        });
    }

    @Test
    @DisplayName("A first name can be up to 20 characters in length")
    void test03(){
        assertDoesNotThrow(() -> {
            new FullName(generateValueOfLength(20),"surname1");
        });
    }

    @Test
    @DisplayName("A firstname exceeding 20 characters is considered invalid")
    void test04(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName(generateValueOfLength(21),"surname1");
        });
    }

    @Test
    @DisplayName("A full name cannot contain a blank surname")
    void test05() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName("first1", "");
        });
    }

    @Test
    @DisplayName("A surname can be up to 20 characters in length")
    void test06() {
        assertDoesNotThrow(() -> {
            new FullName("first1", generateValueOfLength(20));
        });
    }

    @Test
    @DisplayName("A surname exceeding 20 characters is considered invalid")
    void test07() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName("first1", generateValueOfLength(21));
        });
    }

    @Test
    @DisplayName("Two names with different values are not equal")
    void test08() {
        FullName fullName1 = new FullName("first1", "surname1");
        FullName fullName2 = new FullName("first2", "surname1");
        assertThrows(AssertionError.class, () -> assertEquals(fullName1, fullName2));
    }

    @Test
    @DisplayName("Copy constructor creates an equal object")
    void test09() {
        FullName original = generateFullName();
        FullName copy = new FullName(original);
        assertEquals(original, copy);
    }
}
