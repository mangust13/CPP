package com.cppteam.cppteamproject.Domain.Shared;

import com.cppteam.cppteamproject.Domain.Sensors.Sensor;
import com.cppteam.cppteamproject.Domain.Sensors.TemperatureSensor;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class EventLog {
    private static EventLog instance;
    private List<EventEntry> entries;
    private static String filePath = "event_log.txt";

    private EventLog() {
        this.entries = new ArrayList<>();
    }

    public static EventLog getInstance() {
        if (instance == null) {
            instance = new EventLog();
        }
        return instance;
    }

    public void addEntry(Sensor sensor, List<String> messages) {
        var eventEntry = generateEventEntry(sensor, messages);
        this.entries.add(eventEntry);
        logEntryToFile(eventEntry);
    }

    private EventEntry generateEventEntry(Sensor sensor, List<String> messages) {
        String sensorType = sensor instanceof TemperatureSensor ? "Temperature sensor" : "Motion sensor";
        String eventMessage = "Generated Event(" + sensorType + "): ";
        for (var message : messages) {
            eventMessage += message + ", ";
        }
        eventMessage = eventMessage.substring(0, eventMessage.length() - 2);
        LocalDateTime timestamp = LocalDateTime.now();
        String sourceType = "Sensor";
        String sourceId = sensor.getId();

        return new EventEntry(eventMessage, null, null, null,
                timestamp, sourceType, sourceId);
    }

    private void logEntryToFile(EventEntry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(entry.getTimestamp() + ": ");
            writer.write(entry.getEventMessage() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLogEntries() {
        List<String> logEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logEntries.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logEntries;
    }
}
