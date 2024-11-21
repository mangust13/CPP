package com.cppteam.cppteamproject.Domain.Shared;

import java.time.LocalDateTime;

public class EventEntry {
    String eventMessage;
    Integer buildingId;
    Integer floorId;
    Integer roomId;
    LocalDateTime timestamp;
    String sourceType;
    String sourceId;

    public EventEntry(String eventMessage, Integer buildingId, Integer floorId, Integer roomId, LocalDateTime timestamp, String sourceType, String sourceId) {
        this.eventMessage = eventMessage;
        this.buildingId = buildingId;
        this.floorId = floorId;
        this.roomId = roomId;
        this.timestamp = timestamp;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
    }

    public String getEventMessage() {
        return this.eventMessage;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

}
