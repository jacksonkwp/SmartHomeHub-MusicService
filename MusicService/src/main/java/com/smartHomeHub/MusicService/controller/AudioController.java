package com.smartHomeHub.MusicService.controller;

import com.smartHomeHub.MusicService.model.Audio;
import com.smartHomeHub.MusicService.service.AudioService;
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

    @RequestMapping(value="/details/{filename}", method = RequestMethod.GET)
    public ResponseEntity<Audio> getAudio(@PathVariable("filename") String fileName){

        Audio audio = audioService.getAudio(fileName);
        return ResponseEntity.ok(audio);
    }

    @RequestMapping(value="/details/all",method = RequestMethod.GET)
    public ResponseEntity<List<Audio>> getAllAudioFiles() {

        List<Audio> audioFiles = audioService.getAllAudioFiles();
        return ResponseEntity.ok(audioFiles);
    }

    @RequestMapping(value="/edit/{filename}",method = RequestMethod.PATCH)
    public ResponseEntity<Audio> editAudio(@PathVariable("filename") String fileName, @RequestBody Audio audio) {
        return ResponseEntity.ok(audioService.editAudio(fileName, audio));
    }

    @RequestMapping(value="/add",method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Audio> addAudio(@RequestBody Audio audio) {
        return new ResponseEntity<Audio>(audioService.addAudio(audio), HttpStatus.CREATED);
    }

    @RequestMapping(value="/remove/{filename}", method = RequestMethod.DELETE)
    public ResponseEntity<Audio> removeAudio(@PathVariable("filename") String fileName) {
        return ResponseEntity.ok(audioService.removeAudio(fileName));
    }
}

