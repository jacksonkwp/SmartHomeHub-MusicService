package com.smartHomeHub.MusicService.exception;

public class SpeakerNotFoundException extends RuntimeException{
    public SpeakerNotFoundException(){
        super("Speaker not found");
    }
}

