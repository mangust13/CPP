package Shared;

import Sensors.Sensor;

import java.util.ArrayList;
import java.util.List;

// Клас для журналу подій, який використовує паттерн Одиночка (Singleton)
public class EventLog {
    // Поле для збереження єдиного екземпляру класу
    private static EventLog instance;
    // Поле для збереження списку записів про події
    private List<EventEntry> entries;

    // Приватний конструктор для запобігання створення багатьох екземплярів класу
    private EventLog() {
        this.entries = new ArrayList<>();
    }

    // Статичний метод для отримання єдиного екземпляру класу
    public static EventLog getInstance() {
        if (instance == null) {
            instance = new EventLog();
        }
        return instance;
    }

    // Метод для додавання запису про подію до журналу
    public void addEntry(Sensor sensor) {
        EventEntry entry = new EventEntry(sensor);
        this.entries.add(entry);
    }

    // Метод для отримання списку записів про події
    public List<EventEntry> getEntries() {
        return this.entries;
    }
}
