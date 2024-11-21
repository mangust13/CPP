package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Setter
@Getter
public class BuildingDTO {
    @Id
    private String id;
    private byte buildingType;
    private List<FloorDTO> floors;
}
