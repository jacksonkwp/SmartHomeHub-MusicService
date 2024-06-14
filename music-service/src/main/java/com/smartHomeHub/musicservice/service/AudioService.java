package com.smartHomeHub.musicservice.service;

import com.smartHomeHub.musicservice.exception.AudioNotFoundException;
import com.smartHomeHub.musicservice.model.Audio;
import com.smartHomeHub.musicservice.repository.AudioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AudioService {

    private final AudioRepository audioRepo;

    public Audio getAudio(String fileName){

        Optional<Audio> foundAudio = audioRepo.findByFileName(fileName);
        if(foundAudio.isEmpty()){
            log.warn("Audio not found");
            throw new AudioNotFoundException();
        }
        return foundAudio.get();
    }

    public List<Audio> getAllAudioFiles(){

        List<Audio> foundAudioFiles = audioRepo.findAll();
        if(foundAudioFiles.isEmpty()){
            log.warn("No uploaded audio");
            throw new AudioNotFoundException();
        }
        return foundAudioFiles;
    }

    public Audio addAudio(Audio audio){

        Audio newAudio = new Audio();
        newAudio.setFileName(audio.getFileName());
        newAudio.setFileType(audio.getFileType());
        newAudio.setSource(audio.getSource());

        return audioRepo.save(newAudio);
    }

    public Audio editAudio(String fileName, Audio audio){

        Optional<Audio> audioToEdit = audioRepo.findByFileName(fileName);
        if(audioToEdit.isEmpty()){
            log.warn("Audio not found");
            throw new AudioNotFoundException();
        }

        audioToEdit.get().setFileName(audio.getFileName());
        audioToEdit.get().setFileType(audio.getFileType());
        audioToEdit.get().setSource(audio.getSource());

        audioRepo.save(audioToEdit.get());

        return audioToEdit.get();
    }

    public Audio removeAudio(String fileName){

        Optional<Audio> audioToRemove = audioRepo.findByFileName(fileName);
        if(audioToRemove.isEmpty()){
            log.warn("Audio not found");
            throw new AudioNotFoundException();
        }

        audioRepo.delete(audioToRemove.get());

        return audioToRemove.get();
    }
}
