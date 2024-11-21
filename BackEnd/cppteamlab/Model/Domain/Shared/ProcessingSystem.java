package Shared;

import Sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// Клас для системи опрацювання, яка використовує паттерн Спостерігач (Observer)
public class ProcessingSystem implements Observer {

    // Поле для збереження посилання на журнал подій
    private EventLog eventLog;
    // Поле для збереження списку датчиків, які спостерігаються системою
    private List<Sensor> sensors;

    // Конструктор для ініціалізації полів
    public ProcessingSystem(EventLog eventLog) {
        this.eventLog = eventLog;
        this.sensors = new ArrayList<>();
    }

    // Метод для додавання датчика до списку спостереження
    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }

    // Метод для видалення датчика зі списку спостереження
    public void removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
    }

    // Метод для логування події, що сталася з датчиком
    public void logEvent(Sensor sensor) {
        this.eventLog.addEntry(sensor);
    }

    // Метод для оновлення стану системи опрацювання при спрацюванні датчика
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Sensor) {
            Sensor sensor = (Sensor) o;
            sensor.notifyTrigger();
        }
    }
}
