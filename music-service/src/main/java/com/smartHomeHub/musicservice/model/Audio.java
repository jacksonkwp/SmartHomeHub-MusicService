package com.smartHomeHub.musicservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audio {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String fileType;
    private String source;

    @ManyToOne
    private Speaker speaker;
}
