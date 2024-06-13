package com.smartHomeHub.MusicService.controller;

import com.smartHomeHub.MusicService.model.Speaker;
import com.smartHomeHub.MusicService.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @RequestMapping(value="/details/{room}", method = RequestMethod.GET)
    public ResponseEntity<Speaker> getSpeaker(@PathVariable("room") String room){

        Speaker speaker = speakerService.getSpeaker(room);
        return ResponseEntity.ok(speaker);
    }

    @RequestMapping(value="/details/all",method = RequestMethod.GET)
    public ResponseEntity<List<Speaker>> getAllSpeakers() {

        List<Speaker> speakers = speakerService.getAllSpeakers();
        return ResponseEntity.ok(speakers);
    }

    @RequestMapping(value="/edit/{name}",method = RequestMethod.PATCH)
    public ResponseEntity<Speaker> editSpeaker(@PathVariable("name") String name, @RequestBody Speaker speaker) {
        return ResponseEntity.ok(speakerService.editSpeaker(name, speaker));
    }

    @RequestMapping(value="/add",method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Speaker> addSpeaker(@RequestBody Speaker speaker) {
        return new ResponseEntity<Speaker>(speakerService.addSpeaker(speaker), HttpStatus.CREATED);
    }

    @RequestMapping(value="/remove/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Speaker> removeSpeaker(@PathVariable("name") String name) {
        return ResponseEntity.ok(speakerService.removeSpeaker(name));
    }
}
