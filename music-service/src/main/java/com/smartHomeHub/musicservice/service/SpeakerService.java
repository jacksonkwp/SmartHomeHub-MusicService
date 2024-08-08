package com.smartHomeHub.musicservice.service;

import com.smartHomeHub.musicservice.exception.SpeakerNotFoundException;
import com.smartHomeHub.musicservice.model.Speaker;
import com.smartHomeHub.musicservice.model.SpeakerEvent;
import com.smartHomeHub.musicservice.repository.SpeakerRepository;
import com.smartHomeHub.musicservice.client.NotificationServiceDiscoveryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpeakerService {

    private final SpeakerRepository speakerRepo;
    private final NotificationServiceDiscoveryClient notificationServiceDiscoveryClient;
    private final StreamBridge streamBridge;

    public Speaker getSpeaker(String room){

        Optional<Speaker> foundSpeaker = speakerRepo.findByRoom(room);
        if(foundSpeaker.isEmpty()){
            log.warn("Speaker not found");
            throw new SpeakerNotFoundException();
        }
        return foundSpeaker.get();
    }

    public List<Speaker> getAllSpeakers(){

        List<Speaker> foundSpeakers = speakerRepo.findAll();
        if(foundSpeakers.isEmpty()){
            log.warn("No registered speakers");
            throw new SpeakerNotFoundException();
        }
        return foundSpeakers;
    }

    @CircuitBreaker(name = "musicService", fallbackMethod = "failedAddSpeakerNotif")
    public Speaker addSpeaker(Speaker speaker, String authToken){

        Speaker newSpeaker = new Speaker();
        newSpeaker.setName(speaker.getName());
        newSpeaker.setRoom(speaker.getRoom());
        newSpeaker.setVolume(speaker.getVolume());
        newSpeaker.setConnectedDevice(speaker.getConnectedDevice());
        newSpeaker.setState(speaker.getState());

        speakerRepo.save(newSpeaker);

        log.info(notificationServiceDiscoveryClient
                .sendNotification(authToken, "New Speaker Added: " + newSpeaker.getName()));

        return newSpeaker;
    }

    @SuppressWarnings("unused")
    private Speaker failedAddSpeakerNotif(Speaker speaker, String authToken, Throwable throwable) {
        log.info("Triggered failedAddSpeakerNotif fallback when attempting to add a speaker");
        return getSpeaker(speaker.getRoom());
    }

    public Speaker editSpeaker(String name, Speaker speaker){

        Optional<Speaker> speakerToEdit = speakerRepo.findByName(name);
        if(speakerToEdit.isEmpty()){
            log.warn("Speaker not found");
            throw new SpeakerNotFoundException();
        }

        speakerToEdit.get().setName(speaker.getName());
        speakerToEdit.get().setRoom(speaker.getRoom());
        speakerToEdit.get().setVolume(speaker.getVolume());
        speakerToEdit.get().setConnectedDevice(speaker.getConnectedDevice());
        speakerToEdit.get().setState(speaker.getState());

        speakerRepo.save(speakerToEdit.get());

        //JMS
        Long deviceId = speakerToEdit.get().getId();
        log.info("Updating Speaker with id: {}", deviceId);
        streamBridge.send("speakerEventSupplier-out-0",
                SpeakerEvent.builder()
                        .eventName(SpeakerEvent.class.getTypeName())
                        .action("Speaker Updated")
                        .speakerId(deviceId)
                        .build());
        log.info("Produced Speaker:{} Updated event to speaker-event-topic!", deviceId);

        return speakerToEdit.get();
    }

    public Speaker removeSpeaker(String name){

        Optional<Speaker> speakerToRemove = speakerRepo.findByName(name);
        if(speakerToRemove.isEmpty()){
            log.warn("Speaker not found");
            throw new SpeakerNotFoundException();
        }

        speakerRepo.delete(speakerToRemove.get());

        // JMS
        Long deviceId = speakerToRemove.get().getId();
        log.info("Deleted Speaker with id: {}", deviceId);
        streamBridge.send("speakerEventSupplier-out-0",
                SpeakerEvent.builder()
                        .eventName(SpeakerEvent.class.getTypeName())
                        .action("Speaker Deleted")
                        .speakerId(deviceId)
                        .build());
        log.info("Produced Speaker:{} Deleted event to speaker-event-topic!", deviceId);

        return speakerToRemove.get();
    }
    
}
