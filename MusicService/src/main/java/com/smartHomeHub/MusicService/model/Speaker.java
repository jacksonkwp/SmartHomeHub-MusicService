package com.smartHomeHub.MusicService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Speaker {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String room;
    private int volume;
    private String connectedDevice;
    private State state;
    public enum State{
        ON, PLAY, PAUSE, OFF
    }

}
