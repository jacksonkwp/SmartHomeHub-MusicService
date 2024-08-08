package com.smartHomeHub.musicservice.controller;

import com.smartHomeHub.musicservice.model.Speaker;
import com.smartHomeHub.musicservice.service.SpeakerService;
import com.smartHomeHub.musicservice.event.KafkaProducer;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/details/{room}", method = RequestMethod.GET)
    public ResponseEntity<Speaker> getSpeaker(@PathVariable("room") String room){

        Speaker speaker = speakerService.getSpeaker(room);
        return ResponseEntity.ok(speaker);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/details/all",method = RequestMethod.GET)
    public ResponseEntity<List<Speaker>> getAllSpeakers() {

        List<Speaker> speakers = speakerService.getAllSpeakers();
        return ResponseEntity.ok(speakers);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/edit/{name}",method = RequestMethod.PATCH)
    public ResponseEntity<Speaker> editSpeaker(@PathVariable("name") String name, @RequestBody Speaker speaker) {
        return ResponseEntity.ok(speakerService.editSpeaker(name, speaker));
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value="/add",method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Speaker> addSpeaker(@RequestBody Speaker speaker,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        //ResponseEntity<Speaker> responseEntity = new ResponseEntity<Speaker>(speakerService.addSpeaker(speaker, authToken), HttpStatus.CREATED);
        //kafkaProducer.sendMessage("music-service", "speaker", "speaker added to music service");
        return new ResponseEntity<Speaker>(speakerService.addSpeaker(speaker, authToken), HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value="/remove/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Speaker> removeSpeaker(@PathVariable("name") String name) {
        return ResponseEntity.ok(speakerService.removeSpeaker(name));
    }
}
