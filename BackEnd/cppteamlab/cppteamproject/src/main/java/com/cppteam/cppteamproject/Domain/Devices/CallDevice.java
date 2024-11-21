package com.cppteam.cppteamproject.Domain.Devices;


import com.cppteam.cppteamproject.DTO.ViolationEventDTO;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "CallDevices")
@Setter
@Getter
public class CallDevice extends Device
{
    private String phoneNumber;

    public CallDevice(String phoneNumber)
    {
        super();
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    @Override
    public String react(Sensor sensor) {
        if (this.isActive()) {
            // TO-DO change later
            return ("Calling " + this.phoneNumber + " due to sensor " + sensor.getId());
        }
        return null;
    }
}
