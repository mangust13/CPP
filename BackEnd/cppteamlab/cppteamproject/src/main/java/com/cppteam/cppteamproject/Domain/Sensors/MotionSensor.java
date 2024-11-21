package com.cppteam.cppteamproject.Domain.Sensors;


import com.cppteam.cppteamproject.Domain.Shared.ProcessingSystem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MotionSensors")
@Getter
@Setter
public class MotionSensor extends Sensor {
    private Double threshold;

    public MotionSensor(String location, double threshold) {
        super(location);
        this.threshold = threshold;
    }

    @Override
    public double getReading() {
        return Math.random();
    }

    @Override
    public boolean isTriggered() {
        //return this.getReading() > this.threshold;
        return true;
    }
}
