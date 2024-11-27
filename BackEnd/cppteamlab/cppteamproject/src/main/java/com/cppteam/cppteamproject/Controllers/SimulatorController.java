package com.cppteam.cppteamproject.Controllers;

import com.cppteam.cppteamproject.DTO.GenerateViolationDTO;
import com.cppteam.cppteamproject.Domain.Simulator.Simulator;
import com.cppteam.cppteamproject.Repository.MongoDBBuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class SimulatorController {
    @Autowired
    private MongoDBBuildingRepository repository;

    @PostMapping("/Simulator")
    public ResponseEntity<?> simulateViolation(@RequestBody GenerateViolationDTO violationData) {
        String id = violationData.getBuildingId();
        var buildingOptional = repository.findById(id);
        if (buildingOptional.isEmpty()) {
            return new ResponseEntity<>("Building " + id + " is not present",
                    HttpStatus.NOT_FOUND);
        }

        var simulator = new Simulator(buildingOptional.get());
        try {
            var violationFuture = CompletableFuture.supplyAsync(() ->
                    simulator.simulateViolations(violationData.getFloorId())
            );

            var violation = violationFuture.get();

            return new ResponseEntity<>(violation, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error during simulation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

