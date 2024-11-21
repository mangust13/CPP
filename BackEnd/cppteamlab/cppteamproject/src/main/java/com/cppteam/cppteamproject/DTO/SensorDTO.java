package com.cppteam.cppteamproject.DTO;

import com.cppteam.cppteamproject.Domain.Devices.Device;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class SensorDTO {
    @Id
    private String id;
    private byte sensorType;
    private Boolean active;
    private String location;
    private List<DeviceDTO> devices;

    private Double threshold;
}
