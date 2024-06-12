package com.smartHomeHub.MusicService.repository;

import com.smartHomeHub.MusicService.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, UUID> {

    Optional<Speaker> findByRoom(String room);
    Optional<Speaker> findByName(String name);
}
