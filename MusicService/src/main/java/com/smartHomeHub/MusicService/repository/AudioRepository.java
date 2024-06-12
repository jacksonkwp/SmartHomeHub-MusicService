package com.smartHomeHub.MusicService.repository;

import com.smartHomeHub.MusicService.model.Audio;
import com.smartHomeHub.MusicService.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AudioRepository extends JpaRepository<Audio, UUID> {

    //Optional<Speaker> findByRoom(String room);
    Optional<Audio> findByFileName(String fileName);

}
