package com.example.identitymanagement.infrastructure;


import example.common.security.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, String> {
    @Query("FROM app_user u WHERE u.userName=:username")
    Optional<AppUser> findByUsername(String username);
}