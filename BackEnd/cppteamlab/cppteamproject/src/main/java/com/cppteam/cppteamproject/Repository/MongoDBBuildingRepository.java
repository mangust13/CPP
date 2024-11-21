package com.cppteam.cppteamproject.Repository;

import com.cppteam.cppteamproject.Domain.Building.Building;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBBuildingRepository extends MongoRepository<Building, String> {
}
