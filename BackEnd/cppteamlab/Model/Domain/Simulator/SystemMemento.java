package Simulator;

import Sensors.*;
import Devices.*;

import java.util.List;

public class SystemMemento {
    private int floors;
    private int roomsPerFloor;
    private double areaPerRoom;
    private int windowsPerRoom;
    private int doorsPerRoom;
    private List<Sensor> sensors;
    private List<Device> devices;

    public SystemMemento(String state) {
    }

    public String getState() {
        return serializeState();
    }

    private String serializeState() {
        return String.format(
                "floors=%d, roomsPerFloor=%d, areaPerRoom=%.2f, windowsPerRoom=%d, doorsPerRoom=%d, sensors=%s, devices=%s",
                floors, roomsPerFloor, areaPerRoom, windowsPerRoom, doorsPerRoom, sensors, devices
        );
    }
}