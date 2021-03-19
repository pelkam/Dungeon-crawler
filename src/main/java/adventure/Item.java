package adventure;
import java.util.ArrayList;
import java.io.Serializable;
public class Item implements Serializable{
    private static final long serialVersionUID = -933719460929787826L;
    private String name;
    private long id;
    private boolean taken;
    private int i;
    private int j;
    private String desc;
    private Adventure adv;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items = new ArrayList<Item>();
    /**
     * This constructs the item with everything it needs
     * @param iId the id of the item
     * @param iName the name of the item
     * @param descrip the description of the item
     */
    public Item(long iId,String iName,String descrip){
        this.name = iName;
        this.id = iId;
        this.desc = descrip;
        this.taken = false;
    }
    /**
     * Default Item constructor
     */
    public Item(){
        this.name = "None";
        this.id = -1;
        this.desc = "None";
        this.taken = false;
    }
    /**
     * This method gets the name of the item
     * @return String returns the name of the item
     */
    public String getName(){
        return this.name;
    }
    /**
     * This method sets the name of the item
     * @param n the new name
     */
    public void setName(String n){
        this.name = n;
    }
    /**
     * This method gets the description of the item
     * @return String returns the description of the item
     */
    public String getLongDescription(){
        return this.desc;
    }
    /**
     * This method sets the descripton of the item
     * @param d the new descripton
     */
    public void setDescription(String d){
        this.desc = d;
    }
    /**
     * This method gets the id of the item
     * @return String returns the id of the item
     */
    public long getId(){
        return this.id;
    }
    /**
     * This method gets the room containing the item
     * @return Room returns the room containing the item
     */
    public Room getContainingRoom(){
        for(Room room :adv.listAllRooms()){
            for(Item item: room.listItems()){
                if(this.id == item.getId()){
                    return room;
                }
            }
        }
        System.out.println("This item was not found in any room");
        return null;
    }
    /**
     * This method sets the adventure the item is part off
     * @param adven The adventure to be set
     */
    public void setAdventure(Adventure adven){
        this.adv = adven;
    }
    /**
     * This method sets the taken status of the item
     * @param x what taken should be set to
     */
    public void setTaken(boolean x){
        this.taken = x;
    }
    /**
     * This method sets the id of the item
     * @param iD the new id
     */
    public void setId(long iD){
        this.id = iD;
    }
    /**
     * This method sets the taken status of the item
     * @return String the item converted to a string
     */
    public String toString(){
        return (this.getName()+" "+this.getLongDescription());
    }
    /**
     * This method gets the taken status of an item
     * @return boolean Returns whether the item is taken or not
     */
    public boolean getTaken(){
        return this.taken;
    }
    /**
     * This method sets the rooms
     * @param r the new rooms
     */
    public void setRooms(ArrayList<Room> r){
        this.rooms = r;
    }
    /**
     * This method sets the items
     * @param its the new items
     */
    public void setItems(ArrayList<Item> its){
        this.items = its;
    }

}
