package Sensors;

import Shared.ProcessingSystem;

public class TemperatureSensor extends Sensor {
    private double threshold;

    public TemperatureSensor(int id, String location, ProcessingSystem processingSystem, double threshold) {
        super(id, location, processingSystem);
        this.threshold = threshold;
    }

    @Override
    public double getReading() {
        return Math.random() * 100;
    }

    @Override
    public boolean isTriggered() {
        return this.getReading() > this.threshold;
    }
}
