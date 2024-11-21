package com.cppteam.cppteamproject.Domain.Shared;


import com.cppteam.cppteamproject.DTO.ViolationEventDTO;
import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Document
public class ProcessingSystem {
    private EventLog eventLog;
    private List<Sensor> sensors;

    public ProcessingSystem() {
        this.eventLog = EventLog.getInstance();
        this.sensors = new ArrayList<>();
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
    }

    public void removeSensors() {this.sensors.clear();}

    public void setSensors(List<Sensor> sensors) {
        this.sensors = new ArrayList<>(sensors);
    }

    // Метод для логування події, що сталася з датчиком
    public void logEvent(Sensor sensor, List<String> messages) {
        this.eventLog.addEntry(sensor, messages);
    }

    public ViolationEventDTO update(Sensor sensor) {
        ViolationEventDTO violation = new ViolationEventDTO();
        if (sensors.contains(sensor)) {
            if (sensor.isActive() && sensor.isTriggered()) {
                violation.setSensorId(sensor.getId());
                List<String> messages = sensor.notifyTrigger();
                logEvent(sensor, messages);
                violation.setEventMessages(messages);
            }
        }
        return violation;
    }
}
