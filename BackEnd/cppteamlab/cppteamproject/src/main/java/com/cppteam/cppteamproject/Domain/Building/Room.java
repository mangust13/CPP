package com.cppteam.cppteamproject.Domain.Building;


import com.cppteam.cppteamproject.DTO.*;
import com.cppteam.cppteamproject.Domain.Devices.Device;
import com.cppteam.cppteamproject.Domain.Sensors.MotionSensor;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "Rooms")
public class Room {
    @Id
    private String id;
    private double area;
    private int windows;
    private int doors;
    private List<Sensor> sensors;

    public Room(double area, int windows, int doors) {
        this.area = area;
        this.windows = windows;
        this.doors = doors;
        this.sensors = new ArrayList<>();

        this.id = new ObjectId().toString();
    }

    public static Room fromAddRequestDto(AddRoomRequestDTO roomDto) {
        return new Room(roomDto.getArea(), roomDto.getWindows(), roomDto.getDoors());
    }

    public void updateFromDto(UpdateRoomRequestDTO roomDto) {
        if (roomDto.getArea() != null) {
            this.area = roomDto.getArea();
        }
        if (roomDto.getDoors() != null) {
            this.doors = roomDto.getDoors();
        }
        if (roomDto.getArea() != null) {
            this.area = roomDto.getArea();
        }
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        sensors.removeIf(s->s.getId().equals(sensor.getId()));
    }

    public List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

    public RoomDTO convertToDTO() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(this.id);
        roomDTO.setArea(this.area);
        roomDTO.setDoors(this.doors);
        roomDTO.setWindows(this.windows);

        roomDTO.setSensors(convertToSensorDTOList(this.getSensors()));

        return roomDTO;
    }

    public List<SensorDTO> convertToSensorDTOList(List<Sensor> sensors) {
        List<SensorDTO> sensorDTOs = new ArrayList<>();
        for (var sensor : sensors) {
            sensorDTOs.add(sensor.convertToDTO());
        }
        return sensorDTOs;
    }
}