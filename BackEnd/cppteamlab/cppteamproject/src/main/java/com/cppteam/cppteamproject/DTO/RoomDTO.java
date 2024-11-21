package com.cppteam.cppteamproject.DTO;

import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    @Id
    private String id;
    private double area;
    private int windows;
    private int doors;
    private List<SensorDTO> sensors;
}
