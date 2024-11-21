package com.cppteam.cppteamproject.Domain.Sensors;


import com.cppteam.cppteamproject.DTO.*;
import com.cppteam.cppteamproject.Domain.Building.Room;
import com.cppteam.cppteamproject.Domain.Devices.CallDevice;
import com.cppteam.cppteamproject.Domain.Devices.Device;
import com.cppteam.cppteamproject.Domain.Shared.ProcessingSystem;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

@Getter
@Setter
@Document
public abstract class Sensor {
    @Id
    private String id;
    private Boolean active;
    private String location;
    private List<Device> devices;

    public static byte MotionSensor = 1;
    public static byte TemperatureSensor = 2;

    public Sensor(String location) {
        this.id = new ObjectId().toString();

        this.active = false;
        this.location = location;
        this.devices = new ArrayList<>();
    }

    public static Sensor fromAddRequestDto(AddSensorRequestDTO sensorDto) {
        if (sensorDto.getSensorType() == Sensor.MotionSensor) {
            return new MotionSensor(sensorDto.getLocation(), sensorDto.getThreshold());
        } else if (sensorDto.getSensorType() == Sensor.TemperatureSensor) {
            return new TemperatureSensor(sensorDto.getLocation(), sensorDto.getThreshold());
        }
        return null;
    }

    public void updateFromDto(UpdateSensorRequestDTO sensorDto) {
        if (sensorDto.getLocation() != null) {
            this.location = sensorDto.getLocation();
        }
        if (sensorDto.getActive() != null) {
            this.active = sensorDto.getActive();
        }
        if (this instanceof TemperatureSensor) {
            if (sensorDto.getThreshold() != null) {
                ((TemperatureSensor) this).setThreshold(sensorDto.getThreshold());
            }
        } else if (this instanceof MotionSensor) {
            if (sensorDto.getThreshold() != null) {
                ((MotionSensor) this).setThreshold(sensorDto.getThreshold());
            }
        }
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.removeIf(d -> d.getId().equals(device.getId()));
    }

    public Boolean isActive() {
        return active;
    }

    public abstract double getReading();

    public abstract boolean isTriggered();

    public List<String> notifyTrigger() {
        List<String> result = new ArrayList<>();
        for (Device device : this.devices) {
            var reaction = device.react(this);
            if (reaction != null) {
                result.add(reaction);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Sensor otherSensor = (Sensor) obj;

        return Objects.equals(id, otherSensor.id);
    }

    public SensorDTO convertToDTO() {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setId(this.getId());
        sensorDTO.setActive(this.getActive());
        sensorDTO.setLocation(this.getLocation());
        sensorDTO.setDevices(convertToDeviceDTOList(this.getDevices()));
        if (this instanceof MotionSensor) {
            sensorDTO.setThreshold(((MotionSensor) this).getThreshold());
            sensorDTO.setSensorType(Sensor.MotionSensor);
        } else if (this instanceof TemperatureSensor) {
            sensorDTO.setThreshold(((TemperatureSensor) this).getThreshold());
            sensorDTO.setSensorType(Sensor.TemperatureSensor);
        }
        return sensorDTO;
    }

    public List<DeviceDTO> convertToDeviceDTOList(List<Device> devices) {
        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (Device device : devices) {
            deviceDTOs.add(device.convertToDTO());
        }
        return deviceDTOs;
    }
}
