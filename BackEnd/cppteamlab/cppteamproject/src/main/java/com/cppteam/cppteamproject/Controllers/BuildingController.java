package com.cppteam.cppteamproject.Controllers;

import com.cppteam.cppteamproject.DTO.*;
import com.cppteam.cppteamproject.Domain.Building.Building;
import com.cppteam.cppteamproject.Domain.Building.Floor;
import com.cppteam.cppteamproject.Domain.Building.Room;
import com.cppteam.cppteamproject.Domain.Devices.Device;
import com.cppteam.cppteamproject.Domain.Sensors.MotionSensor;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;
import com.cppteam.cppteamproject.Repository.MongoDBBuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class BuildingController {
    @Autowired
    private MongoDBBuildingRepository repository;

    private Building getBuilding(String buildingId) {
        var buildingOptional = repository.findById(buildingId);
        return buildingOptional.orElse(null);
    }

    private Floor getFloor(Building building, String floorId) {
        var floorOptional = building.getFloors().stream().
                filter(f -> f.getId().equals(floorId)).findFirst();
        return floorOptional.orElse(null);
    }

    private Room getRoom(Floor floor, String roomId) {
        var roomOptional = floor.getRooms().stream().
                filter(r -> r.getId().equals(roomId)).findFirst();
        return roomOptional.orElse(null);
    }

    private Sensor getSensor(Room room, String sensorId) {
        var sensorOptional = room.getSensors().stream().
                filter(s -> s.getId().equals(sensorId)).findFirst();
        return sensorOptional.orElse(null);
    }

    private Device getDevice(Sensor sensor, String deviceId) {
        var sensorOptional = sensor.getDevices().stream().
                filter(s -> s.getId().equals(deviceId)).findFirst();
        return sensorOptional.orElse(null);
    }

    @GetMapping("/Buildings")
    public ResponseEntity<?> getAllBuildings() {
        var buildings = repository.findAll();
        List<BuildingDTO> buildingDTOs = new ArrayList<>();
        for (var building : buildings) {
            buildingDTOs.add(building.convertToDTO());
        }
        return new ResponseEntity<>(buildingDTOs, HttpStatus.OK);
    }

    @GetMapping("/Buildings/{id}")
    public ResponseEntity<?> getBuildingById(@PathVariable("id") String id) {
        var building = getBuilding(id);

        if (building != null) {
            return new ResponseEntity<>(building.convertToDTO(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id + " is not present", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Buildings")
    public ResponseEntity<?> createBuilding(@RequestBody AddBuildingRequestDTO addBuildingRequestDTO) {
        var building = new Building();
        building.setBuildingType(addBuildingRequestDTO.getBuildingType());
        try {
            repository.save(building);
            return new ResponseEntity<>(building.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Buildings/{id}")
    public ResponseEntity<?> deleteBuilding(@PathVariable("id") String id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Buildings/{id}/Floors")
    public ResponseEntity<?> getAllBuildingFloors(@PathVariable("id") String id) {
        var building = getBuilding(id);
        if (building != null) {
            var floors = building.getFloors();
            List<FloorDTO> floorDTOs = new ArrayList<>();
            for (var floor : floors) {
                floorDTOs.add(floor.convertToDTO());
            }
            return new ResponseEntity<>(floorDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id + " is not present", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}")
    public ResponseEntity<?> getFloorById(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(floor.convertToDTO(), HttpStatus.OK);
    }

    @PostMapping("/Buildings/{buildingId}/Floors")
    public ResponseEntity<?> createFloor(@PathVariable("buildingId") String buildingId,
                                         @RequestBody AddFloorRequestDTO addFloorRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        Floor floor = new Floor();
        building.addFloor(floor);
        try {
            repository.save(building);
            return new ResponseEntity<>(floor.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Buildings/{buildingId}/Floors/{floorId}")
    public ResponseEntity<?> deleteFloor(@PathVariable("buildingId") String buildingId,
                                         @PathVariable("floorId") String floorId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        building.removeFloor(floor);
        try {
            repository.save(building);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms")
    public ResponseEntity<?> getAllFloorRooms(@PathVariable("buildingId") String buildingId,
                                              @PathVariable("floorId") String floorId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (var room : floor.getRooms()) {
            roomDTOs.add(room.convertToDTO());
        }
        return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable("buildingId") String buildingId,
                                         @PathVariable("floorId") String floorId,
                                         @PathVariable("roomId") String roomId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(room.convertToDTO(), HttpStatus.OK);
    }

    @PostMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms")
    public ResponseEntity<?> createRoom(@PathVariable("buildingId") String buildingId,
                                        @PathVariable("floorId") String floorId,
                                        @RequestBody AddRoomRequestDTO addRoomRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = Room.fromAddRequestDto(addRoomRequestDTO);
        floor.addRoom(room);
        try {
            repository.save(building);
            return new ResponseEntity<>(room.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}")
    public ResponseEntity<?> updateRoom(@PathVariable("buildingId") String buildingId,
                                        @PathVariable("floorId") String floorId,
                                        @PathVariable("roomId") String roomId,
                                        @RequestBody UpdateRoomRequestDTO updateRoomRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        room.updateFromDto(updateRoomRequestDTO);
        try {
            repository.save(building);
            return new ResponseEntity<>(room.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable("buildingId") String buildingId,
                                        @PathVariable("floorId") String floorId,
                                        @PathVariable("roomId") String roomId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        floor.removeRoom(room);
        try {
            repository.save(building);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors")
    public ResponseEntity<?> getAllRoomSensors(@PathVariable("buildingId") String buildingId,
                                               @PathVariable("floorId") String floorId,
                                               @PathVariable("roomId") String roomId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        List<SensorDTO> sensorDTOs = new ArrayList<>();
        for (var sensor : room.getSensors()) {
            sensorDTOs.add(sensor.convertToDTO());
        }
        return new ResponseEntity<>(sensorDTOs, HttpStatus.OK);
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}")
    public ResponseEntity<?> getSensorById(@PathVariable("buildingId") String buildingId,
                                           @PathVariable("floorId") String floorId,
                                           @PathVariable("roomId") String roomId,
                                           @PathVariable("sensorId") String sensorId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(sensor.convertToDTO(), HttpStatus.OK);
    }

    @PostMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors")
    public ResponseEntity<?> createSensor(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId,
                                          @PathVariable("roomId") String roomId,
                                          @RequestBody AddSensorRequestDTO addSensorRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = Sensor.fromAddRequestDto(addSensorRequestDTO);
        if (sensor == null) {
            return new ResponseEntity<>("Incorrect sensor type",
                    HttpStatus.NOT_FOUND);
        }

        room.addSensor(sensor);
        try {
            repository.save(building);
            return new ResponseEntity<>(sensor.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}")
    public ResponseEntity<?> updateSensor(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId,
                                          @PathVariable("roomId") String roomId,
                                          @PathVariable("sensorId") String sensorId,
                                          @RequestBody UpdateSensorRequestDTO updateSensorRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Incorrect sensor type",
                    HttpStatus.NOT_FOUND);
        }

        sensor.updateFromDto(updateSensorRequestDTO);
        try {
            repository.save(building);
            return new ResponseEntity<>(sensor.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}")
    public ResponseEntity<?> deleteSensor(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId,
                                          @PathVariable("roomId") String roomId,
                                          @PathVariable("sensorId") String sensorId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        room.removeSensor(sensor);

        try {
            repository.save(building);
            return new ResponseEntity<>(building, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}/Devices")
    public ResponseEntity<?> getAllSensorDevices(@PathVariable("buildingId") String buildingId,
                                                 @PathVariable("floorId") String floorId,
                                                 @PathVariable("roomId") String roomId,
                                                 @PathVariable("sensorId") String sensorId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (var device : sensor.getDevices()) {
            deviceDTOs.add(device.convertToDTO());
        }

        return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
    }

    @GetMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}/Devices/{deviceId}")
    public ResponseEntity<?> getDeviceById(@PathVariable("buildingId") String buildingId,
                                           @PathVariable("floorId") String floorId,
                                           @PathVariable("roomId") String roomId,
                                           @PathVariable("sensorId") String sensorId,
                                           @PathVariable("deviceId") String deviceId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var device = getDevice(sensor, deviceId);
        if (device == null) {
            return new ResponseEntity<>("Device " + deviceId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(device.convertToDTO(), HttpStatus.OK);
    }

    @PostMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}/Devices")
    public ResponseEntity<?> createDevice(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId,
                                          @PathVariable("roomId") String roomId,
                                          @PathVariable("sensorId") String sensorId,
                                          @RequestBody AddDeviceRequestDTO addDeviceRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var device = Device.fromAddRequestDto(addDeviceRequestDTO);
        if (device == null) {
            return new ResponseEntity<>("Incorrect device type",
                    HttpStatus.NOT_FOUND);
        }
        sensor.addDevice(device);

        try {
            repository.save(building);
            return new ResponseEntity<>(device.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}/Devices/{deviceId}")
    public ResponseEntity<?> updateDevice(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId,
                                          @PathVariable("roomId") String roomId,
                                          @PathVariable("sensorId") String sensorId,
                                          @PathVariable("deviceId") String deviceId,
                                          @RequestBody UpdateDeviceRequestDTO updateDeviceRequestDTO) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var device = getDevice(sensor, deviceId);
        if (device == null) {
            return new ResponseEntity<>("Device " + deviceId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        device.updateFromDto(updateDeviceRequestDTO);
        try {
            repository.save(building);
            return new ResponseEntity<>(device.convertToDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Buildings/{buildingId}/Floors/{floorId}/Rooms/{roomId}/Sensors/{sensorId}/Devices/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable("buildingId") String buildingId,
                                          @PathVariable("floorId") String floorId,
                                          @PathVariable("roomId") String roomId,
                                          @PathVariable("sensorId") String sensorId,
                                          @PathVariable("deviceId") String deviceId) {
        var building = getBuilding(buildingId);
        if (building == null) {
            return new ResponseEntity<>("Building " + buildingId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var floor = getFloor(building, floorId);
        if (floor == null) {
            return new ResponseEntity<>("Floor " + floorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var room = getRoom(floor, roomId);
        if (room == null) {
            return new ResponseEntity<>("Room " + roomId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var sensor = getSensor(room, sensorId);
        if (sensor == null) {
            return new ResponseEntity<>("Sensor " + sensorId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var device = getDevice(sensor, deviceId);
        if (device == null) {
            return new ResponseEntity<>("Device " + deviceId + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        sensor.removeDevice(device);
        try {
            repository.save(building);
            return new ResponseEntity<>(building, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
