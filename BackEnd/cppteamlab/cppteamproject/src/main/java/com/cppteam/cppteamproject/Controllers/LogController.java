package com.cppteam.cppteamproject.Controllers;

import com.cppteam.cppteamproject.Domain.Shared.EventLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class LogController {
    @GetMapping("/Log")
    public ResponseEntity<?> getLog() {
        var log = EventLog.readLogEntries();

        return new ResponseEntity<>(log, HttpStatus.OK);
    }
}