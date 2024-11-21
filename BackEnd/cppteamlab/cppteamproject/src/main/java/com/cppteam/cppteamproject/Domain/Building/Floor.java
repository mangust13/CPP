package com.cppteam.cppteamproject.Domain.Building;

import com.cppteam.cppteamproject.DTO.FloorDTO;
import com.cppteam.cppteamproject.DTO.RoomDTO;
import com.cppteam.cppteamproject.DTO.SensorDTO;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Floor {
    @Id
    private String id;
    private List<Room> rooms;

    public Floor() {
        this.id = new ObjectId().toString();
        this.rooms = new ArrayList<>();
    }

    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms);
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }
    public void removeRoom(Room room) {
        rooms.removeIf(r->r.getId().equals(room.getId()));
    }

    public FloorDTO convertToDTO() {
        FloorDTO floorDTO = new FloorDTO();
        floorDTO.setId(this.id);
        floorDTO.setRooms(convertToRoomDTOList(this.rooms));

        return floorDTO;
    }

    public List<RoomDTO> convertToRoomDTOList(List<Room> rooms) {
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (var room : rooms) {
            roomDTOs.add(room.convertToDTO());
        }
        return roomDTOs;
    }
}
