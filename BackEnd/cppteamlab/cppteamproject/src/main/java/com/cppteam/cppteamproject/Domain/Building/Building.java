package com.cppteam.cppteamproject.Domain.Building;


import com.cppteam.cppteamproject.DTO.BuildingDTO;
import com.cppteam.cppteamproject.DTO.FloorDTO;
import com.cppteam.cppteamproject.DTO.RoomDTO;
import com.cppteam.cppteamproject.Domain.Shared.EventLog;
import com.cppteam.cppteamproject.Domain.Shared.ProcessingSystem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Buildings")
@Getter
@Setter
public class Building {
    @Id
    private String id;
    private byte buildingType;
    private List<Floor> floors;
    private ProcessingSystem processingSystem;

    public static final byte BigBuilding = 1;
    public static final byte SmallBuilding = 2;

    public Building() {
        this.floors = new ArrayList<>();
        processingSystem = new ProcessingSystem();
    }

    public List<Floor> getFloors() {
        return new ArrayList<>(floors);
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void removeFloor(Floor floor) {
        floors.removeIf(f -> f.getId().equals(floor.getId()));
    }

    public ProcessingSystem getProcessingSystem() {
        return processingSystem;
    }

    public BuildingDTO convertToDTO() {
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setId(this.id);
        buildingDTO.setFloors(convertToFloorDTOList(this.floors));
        buildingDTO.setBuildingType(this.buildingType);
        return buildingDTO;
    }

    public List<FloorDTO> convertToFloorDTOList(List<Floor> floors) {
        List<FloorDTO> floorDTOs = new ArrayList<>();
        for (var floor : floors) {
            floorDTOs.add(floor.convertToDTO());
        }
        return floorDTOs;
    }

}
