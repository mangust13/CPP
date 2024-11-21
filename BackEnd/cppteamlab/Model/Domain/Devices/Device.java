package Devices;
import Sensors.Sensor;

public abstract class Device
{
    private int id;
    private boolean active;

    public Device(int id)
    {
        this.id = id;
    }

    public boolean isActive()
    {
        return active;
    }
    public int getId() { return id; }

    public void SetActive(boolean active)
    {
        this.active = active;
    }

    public abstract void react(Sensor sensor);
}