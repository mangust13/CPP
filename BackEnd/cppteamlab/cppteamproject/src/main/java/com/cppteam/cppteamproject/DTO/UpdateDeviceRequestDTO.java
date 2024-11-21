package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDeviceRequestDTO {
    private Boolean active;
    private String phoneNumber;
}
