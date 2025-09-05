package com.example.managingstaff.infrastructure.events;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStoreRepository extends JpaRepository<EventStoreJpa, Integer> {
}