package Sensors;

import Devices.Device;
import Shared.ProcessingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class Sensor extends Observable {
    private int id;
    private boolean active;
    private String location;
    private ProcessingSystem processingSystem;
    private List<Device> devices;

    public Sensor(int id, String location, ProcessingSystem processingSystem) {
        this.id = id;
        this.active = false;
        this.location = location;
        this.processingSystem = processingSystem;
        this.devices = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }

    public boolean isActive() {
        return active;
    }

    public abstract double getReading();

    public abstract boolean isTriggered();

    public void notifyTrigger() {
        if (this.active && this.isTriggered()) {
            this.processingSystem.logEvent(this);
            for (Device device : this.devices) {
                device.react(this);
            }
        }
    }
}
