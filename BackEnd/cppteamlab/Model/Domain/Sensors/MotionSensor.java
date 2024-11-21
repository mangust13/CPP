package Sensors;

import Shared.ProcessingSystem;

public class MotionSensor extends Sensor {
    private double threshold;

    public MotionSensor(int id, String location, ProcessingSystem processingSystem, double threshold) {
        super(id, location, processingSystem);
        this.threshold = threshold;
    }

    @Override
    public double getReading() {
        return Math.random();
    }

    @Override
    public boolean isTriggered() {
        return this.getReading() > this.threshold;
    }
}
