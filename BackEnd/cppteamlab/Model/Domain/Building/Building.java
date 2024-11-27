package Building;

import Shared.EventLog;
import Shared.ProcessingSystem;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Floor> floors;
    private ProcessingSystem processingSystem;

    public Building(List<Floor> floors, ProcessingSystem processingSystem){
        this.floors = new ArrayList<>();
        processingSystem = new ProcessingSystem(EventLog.getInstance());
    }
    public List<Floor> getFloors(){
        return new ArrayList<>(floors);
    }
    public ProcessingSystem getProcessingSystem(){
        return processingSystem;
    }
}