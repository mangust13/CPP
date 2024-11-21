package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSensorRequestDTO {
    //1 - MotionSensor
    //2 - TemperatureSensor
    private byte sensorType;

    private String location;
    private Double threshold;
}
