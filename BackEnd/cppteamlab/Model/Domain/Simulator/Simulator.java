package Simulator;

import Shared.*;
import Building.*;
import Sensors.*;
import Devices.*;
public class Simulator {
    private Building building;
    private String state;

    public Simulator(Building building) {
        this.building = building;
    }

    public Sensor createMotionSensor(int id, String location, ProcessingSystem processingSystem, double threshold) {
        // Логіка створення рухового датчика
        return new MotionSensor(id, location, processingSystem, threshold);
    }

    public Sensor createTemperatureSensor(int id, String location, ProcessingSystem processingSystem, double threshold) {
        // Логіка створення датчика температури
        return new TemperatureSensor(id, location, processingSystem, threshold);
    }

    public Device createCallDevice(int id, String phoneNumber) {
        // Логіка створення пристрою для дзвінків
        return new CallDevice(id, phoneNumber);
    }

    public Device createCoolingDevice(int id) {
        // Логіка створення охолоджуючого пристрою
        return new CoolingDevice(id);
    }

    public void configureSystem(int floors, int roomsPerFloor, double areaPerRoom, int windowsPerRoom, int doorsPerRoom) {
        // Логіка конфігурації системи
    }

    public void simulateViolations(int violations, int interval) {
        // Логіка моделювання порушень
    }

    public void viewEventLog() {
        // Логіка перегляду журналу подій
    }

    public void setState(SystemMemento memento) {
        // Логіка встановлення стану збереженого моменту
        this.state = memento.getState();
    }

    public void saveState() {
        // Логіка збереження поточного стану
    }

    public String getState() {
        // Логіка отримання поточного стану
        return state;
    }

    public void saveStateToMemento() {
        // Логіка збереження стану в моменті
    }

    public SystemMemento getStateFromMemento() {
        // Логіка отримання стану з моменту
        return new SystemMemento(state);
    }
}