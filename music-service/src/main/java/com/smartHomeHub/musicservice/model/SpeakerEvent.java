package com.smartHomeHub.musicservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SpeakerEvent {
    private String eventName;
    private String action;
    private Long speakerId;
}
