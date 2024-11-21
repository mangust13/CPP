package com.cppteam.cppteamproject.Domain.Sensors;


import com.cppteam.cppteamproject.Domain.Shared.ProcessingSystem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureSensor extends Sensor {
    private Double threshold;

    public TemperatureSensor(String location, double threshold) {
        super(location);
        this.threshold = threshold;
    }

    @Override
    public double getReading() {
        return Math.random() * 100;
        //return 1000;
    }

    @Override
    public boolean isTriggered() {
        //return this.getReading() > this.threshold;
        return true;
    }
}
