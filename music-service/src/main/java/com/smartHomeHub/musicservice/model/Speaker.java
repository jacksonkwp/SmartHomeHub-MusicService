package com.smartHomeHub.musicservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Audio> audioFiles;

}
