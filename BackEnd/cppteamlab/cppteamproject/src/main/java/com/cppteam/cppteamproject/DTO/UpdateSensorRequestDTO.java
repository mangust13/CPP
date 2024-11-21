package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSensorRequestDTO {
    private String location;
    private Double threshold;
    private Boolean active;
}
