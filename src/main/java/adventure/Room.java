package adventure;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
public class Room implements Serializable{
    private static final long serialVersionUID = -6767565978642083145L;
    private String name;
    private String sDesc;
    private String lDesc;
    private long id;
    private int i;
    private int j;
    private String start = "false";
    private long[] loot;
    private long[] cRoom;
    private long check;
    private Adventure adv;
    private Item item;
    private Room room;
    private ArrayList<Item> storedItems;
    private ArrayList<Item> items;
    private ArrayList<Room> rooms;
    private ArrayList<Room> tempR;
    private ArrayList<Item> tempI;
    private Map<String,Room> conRoom = new HashMap<String,Room>();
    /**
     * The room constructor takes in everything needed to know about a room
     * @param rId The id of the room
     * @param roomDesc All the info about the room
     * @param connectRoom The connected rooms to this room
     * @param loots The items in the room
     */
    public Room(long rId,ArrayList<String> roomDesc,long[] connectRoom,long[] loots){
        this.name = roomDesc.get(0);
        this.sDesc = roomDesc.get(1);
        this.lDesc = roomDesc.get(2);
        this.id = rId;
        this.cRoom = connectRoom;
        this.loot = loots;
        this.start = roomDesc.get(2+1);
    }
    /**
     * The room constructor takes in everything needed to know about a room(used if room has no loot)
     * @param rId The id of the room
     * @param roomDesc All the info about the room
     * @param connectRoom The connected rooms to this room
     */
    public Room(long rId,ArrayList<String> roomDesc,long[] connectRoom){
        this.name = roomDesc.get(0);
        this.sDesc = roomDesc.get(1);
        this.lDesc = roomDesc.get(2);
        this.id = rId;
        this.cRoom = connectRoom;
        this.start = roomDesc.get(2+1);
    }
    /**
     * Default room constructor
     */
    public Room(){
        this.name = "None";
        this.sDesc = "None";
        this.lDesc = "None";
        this.id = -1;
        this.start = "false";
    }
    /**
     * Gets the stored items in the room
     * @return the stored items
     */
    public ArrayList<Item> getStoredItems(){
        return this.storedItems;
    }
    /**
     * Adds a item to the stored items
     * @param it the item to be added
     */
    public void addStoredItem(Item it){
        this.storedItems.add(it);
    }
    /**
     * Sets the stored items in the room
     */
    public void setStoredItems(){
        storedItems = new ArrayList<Item>();
        for(Item it: adv.listAllItems()){
            if(loot!=null){
                for(j=0;j<loot.length;++j){
                    if(it.getId() == loot[j]){
                        storedItems.add(it);
                    }
                }
            }
        }
    }
    /**
     * This method gets the items that are in the room and returns them
     * @return ArrayList<Item> Returns an arraylist of the items in the room
     */
    public ArrayList<Item> listItems(){
        return this.storedItems;
    }
    /**
     * This method gets the name of the room
     * @return String Returns the name of the room
     */
    public String getName(){
        return this.name;
    }
    /**
     * This method gets the long description of the room
     * @return String Returns the long description of the room
     */
    public String getLongDescription(){
        return this.lDesc;
    }
    /**
     * This method gets the rooms connected to this room
     * @param direction The direction to which the room faces
     * @return Room Returns the room connected to this room
     */
    public Room getConnectedRoom(String direction){
            if(conRoom.containsKey(direction)){
                return conRoom.get(direction);
            }
            return null;
    }
    /**
     * This method gets the short description of the room
     * @return String Returns the short description of the room
     */
    public String getShortDescription(){
        return this.sDesc;
    }
    /**
     * This method gets the id of the room
     * @return Long Returns the id of the room
     */
    public long getId(){
        return this.id;
    }
    /**
     * This method sets the adventure to which this room belongs
     * @param adven The adventure that was created
     */
    public void setAdventure(Adventure adven){
        this.adv = adven;
    }
    /**
     * This method gets whether this is the starting room
     * @return String Returns whether this is the starting room
     */
    public String getStart(){
        return this.start;
    }
    /**
     * This method gets all the rooms connected to this one
     * @return ArrayList<Room> Returns an arraylist of rooms
     */
    public Map<String,Room> getConnectedRooms(){
        return this.conRoom;
    }
    /**
     * Sets the N and E rooms connected to the current room
     */
    public void setConnectedRoomsNE(){
        for(Room r: adv.listAllRooms()){
            if(r.getId()==cRoom[0]){
                conRoom.put("N",r);
            }
            if(r.getId()==cRoom[1]){
                conRoom.put("E",r);
            }
        }
    }
    /**
     * Sets the S and W rooms connected to the current room
     */
    public void setConnectedRoomsSW(){
        for(Room r: adv.listAllRooms()){
            if(r.getId()==cRoom[2]){
                conRoom.put("S",r);
            }
            if(r.getId()==cRoom[2+1]){
                conRoom.put("W",r);
            }
        }
    }
    /**
     * Sets the vertical(up and down) rooms connected to the current room
     */
    public void setConnectedRoomsVer(){
        for(Room r: adv.listAllRooms()){
            if(r.getId()==cRoom[2+2]){
                conRoom.put("up",r);
            }
            if(r.getId()==cRoom[2+2+1]){
                conRoom.put("down",r);
            }
        }
    }
    /**
     * This method gets all the rooms connected to this one
     * @return String Returns the room
     */
    public String toString(){
        return (this.getName()+" "+this.getLongDescription());
    }
    /**
     * This method prints out the room
     */
    public void printRoom(){
        System.out.println("You are currently in "+this.name);
        System.out.println(this.sDesc);
        this.printItems(this.listItems());
        this.setConnectedRoomsNE();
        this.setConnectedRoomsSW();
        this.setConnectedRoomsVer();
        this.printConnectedRoomsNE(this.getConnectedRooms());
        this.printConnectedRoomsSW(this.getConnectedRooms());
        this.printConnectedRoomsVer(this.getConnectedRooms());
        System.out.println();
    }
    /**
     * Gets the text of the room
     * @return the text of the room
     */
    public String getRoomText(){
        String str = null;
        str = "You are currently in "+this.name+"\n";
        str = str+this.sDesc+"\n";
        str = this.getItemText(str);
        this.setConnectedRoomsNE();
        this.setConnectedRoomsSW();
        this.setConnectedRoomsVer();
        str = str+this.getConnectedRoomsTextNE(this.getConnectedRooms());
        str = str+this.getConnectedRoomsTextSW(this.getConnectedRooms());
        str = str+this.getConnectedRoomsTextVer(this.getConnectedRooms());
        return str;}
    /**
     * Gets the text for the items in the room
     * @param str the string to add too
     * @return the text for the items
     */
    public String getItemText(String str){
        int count =0;
        try{
        if(this.listItems().isEmpty()){
            return str+"There are no items in the room\n";
        }
        str = str+"The following item(s) are in the room:\n";
        str = this.checkItemText(str,count);
        }catch(NullPointerException s){
            return str+"There are no items in the room\n";
        }
        return str;}
    /**
     * Checks to see what item can be printed
     * @param str the string to be add to
     * @param count the number of items marked taken
     * @return a string with the items to be printed
     */
    public String checkItemText(String str,int count){
            for(Item it: this.listItems()){
                if(!it.getTaken()){
                    str = str+it.getName()+"\n";
                }else{
                    ++count;
                    if(count == this.listItems().size()){
                        str = str+"You have taken all the items in the room\n";
                    }
                }
            }
            return str;}
    /**
     * This method prints the items in the room
     * @param theItems the items in the room
     */
    public void printItems(ArrayList<Item> theItems){
        try{
            if(theItems.isEmpty()){
                System.out.println("There are no items in the room");
                return;
            }else{
            this.checkItems(theItems);
            }
        }catch(NullPointerException e){
            System.out.println("There are no items in the room");
        }
    }
    /**
     * This method checks the items to make sure they are valid and prints them
     * @param theItems the items in the room
     */
    public void checkItems(ArrayList<Item> theItems) throws NullPointerException{
        System.out.println("The following item(s) are in the room: ");
        for(Item it: theItems){
            if(!it.getTaken()){
                System.out.println(it.getName());
            }
        }
    }
    /**
     * This method gets the text of the connected room to the current room
     * @param mapRoom the connected rooms
     * @return a string with the direction of the other rooms
     */
    public String getConnectedRoomsTextNE(Map<String,Room> mapRoom){
        String str ="";
        if(mapRoom.containsKey("N")){
            room = mapRoom.get("N");
            str = str+ "There is "+room.getName()+" to the N\n"; 
        }
        if(mapRoom.containsKey("E")){
            room = mapRoom.get("E"); 
            str = str+"There is "+room.getName()+" to the E\n"; 
        }
        return str;
    }
    /**
     * This method gets the text of the connected room to the current room
     * @param mapRoom the connected rooms
     * @return a string with the direction of the other rooms
     */
    public String getConnectedRoomsTextSW(Map<String,Room> mapRoom){
        String str ="";
        if(mapRoom.containsKey("S")){
            room = mapRoom.get("S");
            str = str+"There is "+room.getName()+" to the S\n"; 
        }
        if(mapRoom.containsKey("W")){
            room = mapRoom.get("W"); 
            str = str+"There is "+room.getName()+" to the W\n"; 
        }
        return str;
    }
    /**
     * This method gets the text of the connected room to the current room
     * @param mapRoom the connected rooms
     * @return a string with the direction of the other rooms
     */
    public String getConnectedRoomsTextVer(Map<String,Room> mapRoom){
        String str ="";
        if(mapRoom.containsKey("up")){
            room = mapRoom.get("up");
            str = str+ "There is "+room.getName()+" upward\n"; 
        }
        if(mapRoom.containsKey("down")){
            room = mapRoom.get("down"); 
            str = str+"There is "+room.getName()+" downward\n"; 
        }
        return str;
    }
    /**
     * This method prints the connected room to the current room
     * @param mapRoom the connected rooms
     */
    public void printConnectedRoomsNE(Map<String,Room> mapRoom){
        if(mapRoom.containsKey("N")){
            room = mapRoom.get("N");
            System.out.println("There is "+room.getName()+" to the N"); 
        }
        if(mapRoom.containsKey("E")){
            room = mapRoom.get("E"); 
            System.out.println("There is "+room.getName()+" to the E"); 
        }
    }
    /**
     * This method prints the connected room to the current room
     * @param mapRoom the connected rooms
     */
    public void printConnectedRoomsSW(Map<String,Room> mapRoom){
        if(mapRoom.containsKey("S")){
            room = mapRoom.get("S");
            System.out.println("There is "+room.getName()+" to the S"); 
        }
        if(mapRoom.containsKey("W")){
            room = mapRoom.get("W");
            System.out.println("There is "+room.getName()+" to the W"); 
        }
    }
    /**
     * This method prints the connected room to the current room
     * @param mapRoom the connected rooms
     */
    public void printConnectedRoomsVer(Map<String,Room> mapRoom){
        if(mapRoom.containsKey("up")){
            room = mapRoom.get("up");
            System.out.println("There is "+room.getName()+" upward"); 
        }
        if(mapRoom.containsKey("down")){
            room = mapRoom.get("down");
            System.out.println("There is "+room.getName()+" downward"); 
        }
    }
    /**
     * This method sets the name of the item
     * @param n the new name
     */
    public void setName(String n){
        this.name = n;
    }
    /**
     * This method sets the id of the item
     * @param iD the new id
     */
    public void setId(long iD){
        this.id = iD;
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
    /**
     * This method sets the room
     * @param r the new room
     */
    public void setRoom(Room r){
        this.room = r;
    }
    /**
     * This method sets the loot
     * @param l the new loot
     */
    public void setLoot(long[] l){
        this.loot = l;
    }
    /**
     * This method sets the connected rooms
     * @param c the new connected rooms
     */
    public void setcRoom(long[] c){
        this.cRoom = c;
    }
    /**
     * This method sets the long descripton
     * @param l the new long description
     */
    public void setLongDesc(String l){
        this.lDesc = l;
    }
    /**
     * This method sets the short descripton
     * @param s the new short description
     */
    public void setShortDesc(String s){
        this.sDesc = s;
    }
    /**
     * This method sets the connected rooms
     * @param m the new connected rooms
     */
    public void setConRoom(Map<String,Room> m){
        this.conRoom = m;
    }
    /**
     * THis method set an temporary array list of rooms
     * @param r the temp rooms
     */
    public void setTempR(ArrayList<Room> r) {
        this.tempR = r;
    }
    /**
     * THis method set an temporary array list of rooms
     * @param its the temp rooms
     */
    public void setTempI(ArrayList<Item> its) {
        this.tempI = its;
    }
}

