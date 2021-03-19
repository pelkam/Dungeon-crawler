package adventure;
import java.util.ArrayList;
import java.io.Serializable;

public class Adventure implements Serializable {
    private static final long serialVersionUID = -8321614987968649049L;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private String desc;
    /**
     * Defualt constructor for adventure
     */
    public Adventure(){
        rooms = new ArrayList<Room>();
        items = new ArrayList<Item>();
    }
    /**
     * This method returns the arraylist of rooms
     * @return ArrayList<Room> returns the arraylist of rooms
     */
    public ArrayList<Room> listAllRooms(){
        return this.rooms;
    }
    /**
     * This method returns the arraylist of items
     * @return ArrayList<Item> returns the arraylist of items
     */
    public ArrayList<Item> listAllItems(){
        return this.items;
    }
    /**
     * This method returns the description of the current room
     * @return String returns the description of the current room
     */
    public String getCurrentRoomDescription(){
        return this.desc;
    }
    /**
     * This method adds a room
     * @param room the room to be added
     */
    public void addRoom(Room room){
        this.rooms.add(room);
    }
    /**
     * This method adds a item
     * @param item the item to be added
     */
    public void addItem(Item item){
        this.items.add(item);
    }
    /**
     * This method sets the desription of the current room
     * @param room the current room 
     */
    public void setCurrentRoomDescription(Room room){
        this.desc = room.getLongDescription();
    }
}
