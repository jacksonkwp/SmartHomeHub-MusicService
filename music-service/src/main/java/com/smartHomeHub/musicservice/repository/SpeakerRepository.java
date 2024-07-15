package com.smartHomeHub.musicservice.repository;

import com.smartHomeHub.musicservice.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

    Optional<Speaker> findByRoom(String room);
    Optional<Speaker> findByName(String name);
}
