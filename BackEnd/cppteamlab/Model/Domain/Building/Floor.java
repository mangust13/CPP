package Building;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private List<Room> rooms;

    public Floor(List<Room> rooms) {
        this.rooms = new ArrayList<>(rooms);
    }

    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

}
