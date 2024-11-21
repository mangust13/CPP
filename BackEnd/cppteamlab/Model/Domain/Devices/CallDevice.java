package Devices;
import Devices.Device;
import Sensors.Sensor;

public class CallDevice extends Device
{
    private final String phoneNumber;

    public CallDevice(int id, String phoneNumber)
    {
        super(id);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    @Override
    public void react(Sensor sensor) {
        if (this.isActive()) {
            // TO-DO change later
            System.out.println("Calling " + this.phoneNumber + " due to sensor " + sensor.getId());
        }

    }
}
