package com.cppteam.cppteamproject.Domain.Devices;
import com.cppteam.cppteamproject.DTO.ViolationEventDTO;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "CoolingDevices")
@Getter
@Setter
public class CoolingDevice extends Device
{
    public CoolingDevice()
    {
        super();
    }
    @Override
    public String react(Sensor sensor) {
        if (this.isActive()) {
            return ("Turning on cooling system due to sensor " + sensor.getId());
        }
        return null;
    }
}
