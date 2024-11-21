package com.cppteam.cppteamproject.Repository.Sensors;

import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBTemperatureSensorRepository extends MongoRepository<TemperatureSensor, String> {
}
