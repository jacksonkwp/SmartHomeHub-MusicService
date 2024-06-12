package com.smartHomeHub.MusicService.exception;

public class AudioNotFoundException extends RuntimeException{
    public AudioNotFoundException(){
        super("Audio not found");
    }
}
