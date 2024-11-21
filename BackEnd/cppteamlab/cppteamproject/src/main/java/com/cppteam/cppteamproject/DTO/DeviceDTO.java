package com.cppteam.cppteamproject.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class DeviceDTO {
    @Id
    private String id;
    private byte deviceType;
    private Boolean active;

    private String phoneNumber;
}
