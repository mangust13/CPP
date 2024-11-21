package com.cppteam.cppteamproject.Repository.Devices;

import com.cppteam.cppteamproject.Domain.Devices.CoolingDevice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolingDeviceRepository extends MongoRepository<CoolingDevice, String>
{
}
