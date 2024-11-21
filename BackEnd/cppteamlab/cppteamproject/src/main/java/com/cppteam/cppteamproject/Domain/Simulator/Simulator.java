package com.cppteam.cppteamproject.Domain.Simulator;

import com.cppteam.cppteamproject.DTO.ViolationEventDTO;
import com.cppteam.cppteamproject.Domain.Building.Building;
import com.cppteam.cppteamproject.Domain.Building.Room;
import com.cppteam.cppteamproject.Domain.Devices.CallDevice;
import com.cppteam.cppteamproject.Domain.Devices.CoolingDevice;
import com.cppteam.cppteamproject.Domain.Devices.Device;
import com.cppteam.cppteamproject.Domain.Sensors.MotionSensor;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;
import com.cppteam.cppteamproject.Domain.Shared.ProcessingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulator {
    private Building building;
    private String state;

    public Simulator(Building building) {
        this.building = building;
    }

    public List<ViolationEventDTO> simulateViolations(String floorId) {
        List<ViolationEventDTO> violationEvents = new ArrayList<>();
        Random random = new Random();

        var floorOptional = building.getFloors().stream().
                filter(f -> f.getId().equals(floorId)).findFirst();
        if (floorOptional.isEmpty()) {
            return null;
        }

        var floor = floorOptional.get();
        var activeTriggeredSensors = floor.getRooms().stream()
                .flatMap(room -> room.getSensors().stream())
                .filter(sensor -> sensor.isActive() && sensor.isTriggered()).toList();

        if (activeTriggeredSensors.isEmpty()) return null;

        int randViolCount;
        if (activeTriggeredSensors.size() > 1) {
            randViolCount = random.nextInt(1, activeTriggeredSensors.size());
        } else {
            randViolCount = 1;
        }
        building.getProcessingSystem().removeSensors();

        for (int i = 0; i < randViolCount; i++) {
            int sensorIndex;
            Sensor sensor;

            do {
                sensorIndex = random.nextInt(0, activeTriggeredSensors.size());
                sensor = activeTriggeredSensors.get(sensorIndex);

            } while (!sensor.isActive() && !sensor.isTriggered());

            Sensor finalSensor = sensor;
            var room = floor.getRooms().stream().filter
                    (r -> r.getSensors().stream().anyMatch(s -> s.equals(finalSensor))).findFirst();

            if (room.isEmpty()) continue;

            for (Sensor s : room.get().getSensors()) {
                building.getProcessingSystem().addSensor(s);
            }
            ViolationEventDTO violation = building.getProcessingSystem().update(sensor);
            violation.setRoomId(room.get().getId());
            violation.setFloorId(floorId);
            violationEvents.add(violation);
        }

        return violationEvents;
    }
}