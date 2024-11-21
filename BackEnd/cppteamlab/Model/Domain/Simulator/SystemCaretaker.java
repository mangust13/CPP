package Simulator;

import java.util.ArrayList;
import java.util.List;

public class SystemCaretaker {
    private List<SystemMemento> mementoList;

    public SystemCaretaker() {
        mementoList = new ArrayList<>();
    }

    public void add(SystemMemento memento) {
        // Логіка додавання моменту у список
        mementoList.add(memento);
    }

    public SystemMemento get() {
        // Логіка отримання останнього моменту та його видалення зі списку
        if (!mementoList.isEmpty()) {
            int lastIndex = mementoList.size() - 1;
            SystemMemento lastMemento = mementoList.get(lastIndex);
            mementoList.remove(lastIndex);
            return lastMemento;
        }
        return null;
    }
}