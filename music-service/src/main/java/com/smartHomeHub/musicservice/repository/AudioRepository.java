package com.smartHomeHub.musicservice.repository;

import com.smartHomeHub.musicservice.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {

    //Optional<Speaker> findByRoom(String room);
    Optional<Audio> findByFileName(String fileName);

}
