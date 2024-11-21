package Building;

import Sensors.Sensor;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private double area;
    private int windows;
    private int doors;
    private List<Sensor> sensors;

    public Room(double area, int windows, int doors, List<Sensor> sensors){
        this.area = area;
        this.windows = windows;
        this.doors = doors;
        this.sensors = new ArrayList<>();
    }

    public void addSensor(Sensor sensor){

    }
    public void removeSensor(Sensor sensor){

    }

    public double getArea(){
        return area;
    }
    public int getWindows(){
        return windows;
    }
    public int getDoors(){
        return doors;
    }
    public List<Sensor> getSensors(){
        return new ArrayList<>(sensors);
    }


}