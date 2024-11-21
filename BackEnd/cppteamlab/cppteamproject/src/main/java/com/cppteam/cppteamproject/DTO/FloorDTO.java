package com.cppteam.cppteamproject.DTO;

import com.cppteam.cppteamproject.Domain.Building.Room;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class FloorDTO {
    @Id
    private String id;
    private List<RoomDTO> rooms;
}
