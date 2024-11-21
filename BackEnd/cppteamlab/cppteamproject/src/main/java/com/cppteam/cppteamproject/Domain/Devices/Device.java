package com.cppteam.cppteamproject.Domain.Devices;

import com.cppteam.cppteamproject.DTO.*;
import com.cppteam.cppteamproject.Domain.Sensors.MotionSensor;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class Device {
    @Id
    private String id;
    private Boolean active;

    public static byte CallDevice = 1;
    public static byte CoolingDevice = 2;

    public Device() {
        this.id = new ObjectId().toString();
        this.active = false;
    }

    public static Device fromAddRequestDto(AddDeviceRequestDTO deviceDto) {
        if (deviceDto.getDeviceType() == Device.CallDevice) {
            return new CallDevice(deviceDto.getPhoneNumber());
        } else if (deviceDto.getDeviceType() == Device.CoolingDevice) {
            return new CoolingDevice();
        }
        return null;
    }

    public void updateFromDto(UpdateDeviceRequestDTO deviceDto) {
        if (deviceDto.getActive() != null) {
            this.active = deviceDto.getActive();
        }
        if (this instanceof CallDevice) {
            if (deviceDto.getPhoneNumber() != null) {
                ((CallDevice) this).setPhoneNumber(deviceDto.getPhoneNumber());
            }
        }
    }

    public Boolean isActive() {
        return active;
    }

    public String getId() {
        return id;
    }

    public void SetActive(Boolean active) {
        this.active = active;
    }

    public abstract String react(Sensor sensor);


    public DeviceDTO convertToDTO() {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(this.getId());
        deviceDTO.setActive(this.getActive());
        if (this instanceof CallDevice) {
            deviceDTO.setPhoneNumber(((CallDevice) this).getPhoneNumber());
            deviceDTO.setDeviceType(Device.CallDevice);
        }
        else {
            deviceDTO.setDeviceType(Device.CoolingDevice);
        }
        return deviceDTO;
    }
}