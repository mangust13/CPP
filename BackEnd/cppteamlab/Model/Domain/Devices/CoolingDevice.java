package Devices;
import Devices.Device;
import Sensors.Sensor;

public class CoolingDevice extends Device
{
    public CoolingDevice(int id)
    {
        super(id);
    }
    @Override
    public void react(Sensor sensor) {
        if (this.isActive()) {
            // TO-DO change later
            System.out.println("Turning on cooling system due to sensor " + sensor.getId());
        }

    }
}
