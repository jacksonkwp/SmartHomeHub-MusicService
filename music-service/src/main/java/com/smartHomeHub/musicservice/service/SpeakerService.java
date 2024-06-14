package com.smartHomeHub.musicservice.service;

import com.smartHomeHub.musicservice.exception.SpeakerNotFoundException;
import com.smartHomeHub.musicservice.model.Speaker;
import com.smartHomeHub.musicservice.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpeakerService {

    private final SpeakerRepository speakerRepo;

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

    public Speaker addSpeaker(Speaker speaker){

        Speaker newSpeaker = new Speaker();
        newSpeaker.setName(speaker.getName());
        newSpeaker.setRoom(speaker.getRoom());
        newSpeaker.setVolume(speaker.getVolume());
        newSpeaker.setConnectedDevice(speaker.getConnectedDevice());
        newSpeaker.setState(speaker.getState());

        return speakerRepo.save(newSpeaker);
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

        return speakerToEdit.get();
    }

    public Speaker removeSpeaker(String name){

        Optional<Speaker> speakerToRemove = speakerRepo.findByName(name);
        if(speakerToRemove.isEmpty()){
            log.warn("Speaker not found");
            throw new SpeakerNotFoundException();
        }

        speakerRepo.delete(speakerToRemove.get());

        return speakerToRemove.get();
    }
    
}
