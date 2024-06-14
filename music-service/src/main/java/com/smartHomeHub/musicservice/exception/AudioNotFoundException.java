package com.smartHomeHub.musicservice.exception;

public class AudioNotFoundException extends RuntimeException{
    public AudioNotFoundException(){
        super("Audio not found");
    }
}
