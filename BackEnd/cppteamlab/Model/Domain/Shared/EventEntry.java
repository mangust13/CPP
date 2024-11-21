package Shared;

import Devices.Device;
import Sensors.Sensor;

import java.time.LocalDateTime;

// Клас для запису про подію, який містить інформацію про датчик, час та реакцію системи
class EventEntry {
    // Поле для збереження ідентифікатора датчика
    private int sensorId;
    // Поле для збереження часу події
    private LocalDateTime time;
    // Поле для збереження реакції системи на подію
    private String reaction;

    // Конструктор для ініціалізації полів
    public EventEntry(Sensor sensor) {
        this.sensorId = sensor.getId();
        this.time = LocalDateTime.now();
        this.reaction = "The system reacted to sensor " + sensor.getId() + " by ";
        for (Device device : sensor.getDevices()) {
            this.reaction += device.getClass().getSimpleName() + " " + device.getId() + ", ";
        }
        this.reaction = this.reaction.substring(0, this.reaction.length() - 2); // Видалити останню кому та пробіл
    }

    // Методи для отримання полів
    public int getSensorId() {
        return this.sensorId;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public String getReaction() {
        return this.reaction;
    }
}

