package com.smartHomeHub.musicservice.exception;

public class SpeakerNotFoundException extends RuntimeException{
    public SpeakerNotFoundException(){
        super("Speaker not found");
    }
}

