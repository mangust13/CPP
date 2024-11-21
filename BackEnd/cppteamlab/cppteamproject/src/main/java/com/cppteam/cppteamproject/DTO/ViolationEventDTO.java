package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViolationEventDTO {
    private String floorId;
    private String roomId;
    private String sensorId;
    private List<String> eventMessages;
}
