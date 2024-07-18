package com.smartHomeHub.musicservice.controller;

import com.smartHomeHub.musicservice.model.Audio;
import com.smartHomeHub.musicservice.service.AudioService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/details/{filename}", method = RequestMethod.GET)
    public ResponseEntity<Audio> getAudio(@PathVariable("filename") String fileName){

        Audio audio = audioService.getAudio(fileName);
        return ResponseEntity.ok(audio);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/details/all",method = RequestMethod.GET)
    public ResponseEntity<List<Audio>> getAllAudioFiles() {

        List<Audio> audioFiles = audioService.getAllAudioFiles();
        return ResponseEntity.ok(audioFiles);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/edit/{filename}",method = RequestMethod.PATCH)
    public ResponseEntity<Audio> editAudio(@PathVariable("filename") String fileName, @RequestBody Audio audio) {
        return ResponseEntity.ok(audioService.editAudio(fileName, audio));
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/add",method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Audio> addAudio(@RequestBody Audio audio) {
        return new ResponseEntity<Audio>(audioService.addAudio(audio), HttpStatus.CREATED);
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value="/remove/{filename}", method = RequestMethod.DELETE)
    public ResponseEntity<Audio> removeAudio(@PathVariable("filename") String fileName) {
        return ResponseEntity.ok(audioService.removeAudio(fileName));
    }
}

