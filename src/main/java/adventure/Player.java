package adventure;
import java.util.ArrayList;
import java.io.Serializable;
public class Player implements Serializable{
    private static final long serialVersionUID = 4223159078033948661L;
    private String name;
    private String saveName;
    private Room curRoom;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    /**
     * The default player constructor
     */
    public Player(){
        this.name = "None";
        this.saveName = "NoneSave";
        this.curRoom = null;
        this.inventory = null;
    }
    /**
     * The player constructor with a name given
     * @param pName the name given
     */
    public Player(String pName){
        this.name = pName;
        this.saveName = pName +"Save";
        this.curRoom = null;
        this.inventory = null;
    }
    /**
     * This method sets the current room
     * @param room the current room
     */
    public void setCurrentRoom(Room room){
        this.curRoom = room;
    }
    /**
     * This method gets the current room
     * @return Room Returns the current room
     */
    public Room getCurrentRoom(){
        return this.curRoom;
    }
    /**
     * This method gets the user inventory
     * @return ArrayList<Item> Returns the items in the players inventory
     */
    public ArrayList<Item> getInventory(){
        return this.inventory;
    }
    /**
     * This method gets the name of the player
     * @return String Returns the name
     */
    public String getName(){
        return this.name;
    }
    /**
     * This method sets the name of the player
     * @param n the new name
     */
    public void setName(String n){
        this.name = n;
        this.saveName = n+"Save";
    }
    /**
     * This method gets the name of the save of the player
     * @return String Returns the saves name
     */
    public String getSaveGameName(){
        return this.saveName;
    }
    /**
     * This method adds a item to the players inventory
     * @param item the item to be added
     */
    public void addInventory(Item item){
        try{
        this.inventory.add(item);
        }catch(NullPointerException e){
            inventory = new ArrayList<Item>();
            inventory.add(item);
        }
    }
    /**
     * This method sets the players inventory
     * @param i the new inventory
     */
    public void setInventory(ArrayList<Item> i){
        this.inventory = i;
    }
     /**
     * This method sets the name of the save file
     * @param pName the name of the save file
     */
    public void setPlayerSave(String pName){
        this.saveName = pName;
    }
    /**
     * This method prints the users inventory
     */
    public void printInventory(){
        try{
        System.out.println("The following items are in your inventory\n");
        for(Item it: this.getInventory()){
            System.out.println(it.getName());
        }
        }catch(NullPointerException e){
            System.out.println("There are no items in your inventory");
        }  
    }
    /**
     * This method gets the inventory as text
     * @param its the items
     * @return the inventory as a string
     */
    public String getInventoryText(ArrayList<Item> its){
        String str ="";
        try{
        for(Item i:its){
            str = str+i.getName()+"\n";
        }
        return str;
        }catch(NullPointerException e){
            return "Empty";
        }
    }
}

