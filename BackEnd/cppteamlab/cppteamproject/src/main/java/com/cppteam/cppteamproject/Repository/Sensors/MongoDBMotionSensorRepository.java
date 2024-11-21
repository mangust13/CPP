package com.cppteam.cppteamproject.Repository.Sensors;

import com.cppteam.cppteamproject.Domain.Sensors.MotionSensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBMotionSensorRepository extends MongoRepository<MotionSensor, String> {
}
