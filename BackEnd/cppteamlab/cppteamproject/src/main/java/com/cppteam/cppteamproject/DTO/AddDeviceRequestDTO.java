package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDeviceRequestDTO {
    //1 - CallDevice
    //2 - CoolingDevice
    private byte deviceType;

    private boolean isActive;
    private String phoneNumber;
}
