package com.cppteam.cppteamproject.Repository.Devices;

import com.cppteam.cppteamproject.Domain.Devices.CallDevice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallDeviceRepository extends MongoRepository<CallDevice, String>
{
}
