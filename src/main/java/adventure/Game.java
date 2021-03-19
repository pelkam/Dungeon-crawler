package adventure;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.Serializable;
public class Game implements Serializable{
    private static final long serialVersionUID = -6767565978600083145L;
    private boolean quit = false;
    private Player p1;
    private Adventure adv;
    private JSONObject adventure;
    /**
     * Main method to run the game
     * @param args the arguments from the command line
     */
    public static void main(String[] args){
        String command;
        String subject;
        String userIn;
        Game theGame = new Game();
        Scanner scnr = new Scanner(System.in);
        if(args.length > 0){
            theGame.runGame(args,theGame,scnr);
        }else{
            theGame.noSaveRunGame(theGame,scnr,args);
        }
    }
    /**
     * Gets the current player
     * @return the current player
     */
    public Player getP1(){
        return this.p1;
    }
    /**
     * Generates the intro
     * @param guiGame the current game
     * @return the intro as a string
     */
    public String getIntro(Game guiGame){
        return guiGame.printIntroText(p1.getName());
    }
    /**
     * sets the items in the rooms
     * @param rooms the rooms
     */
    public void setItemsInRoom(ArrayList<Room> rooms){
        for(Room room: rooms){
            room.setStoredItems();
        }
    }
    /**
     * Gets the text
     * @param guiGame the current game
     * @param com the user command
     * @return the text as a string
     */
    public String getText(Game guiGame,Command com){
        String str;
        Scanner scnr = new Scanner(System.in);
        Room room = this.p1.getCurrentRoom();
        this.exeCommand(com,room.listItems(),scnr,this.adv,this.p1);
        room = this.p1.getCurrentRoom();
        return room.getRoomText();
    }
    /**
     * Gets the first text show to the user
     * @param guiGame the current game
     * @param player the current player
     * @return the first text as a string
     */
    public String getFirstText(Game guiGame,Player player){
        String str=null;
        this.p1 = player;
        guiGame.setAdventureRoom(this.adv.listAllRooms(),this.adv);
        guiGame.setAdventureItem(this.adv.listAllItems(),this.adv);
        guiGame.setItemsInRoom(this.adv.listAllRooms());
        Room room = guiGame.getStartRoom(this.adv.listAllRooms());
        this.p1.setCurrentRoom(guiGame.getStartRoom(this.adv.listAllRooms()));
        str = room.getRoomText();
        return str;
    }
    /**
     * Gets the first text show to the user if a save was loaded
     * @param guiGame the current game
     * @return the first text as a string
     */
    public String getFirstTextSave(Game guiGame){
        String str=null;
        this.p1 = guiGame.getP1();
        Room room = this.p1.getCurrentRoom();
        this.p1.setCurrentRoom(room);
        str = room.getRoomText();
        return str;
    }
    /**
     * This method starts to run the game
     * @param args the arguements from the command line
     * @param theGame the game
     * @param scnr the scanner
    */
    public void runGame(String[] args,Game theGame,Scanner scnr){
        switch(args[0]){
            case "-l":
                theGame.saveRunGame(theGame,scnr,args);
                break;
            default:
                theGame.noSaveRunGame(theGame,scnr,args);
                break;
        }
    }
    /**
     * This method loads the adventure from the file given by the user
     * @param filename the name of the file
     * @return JSONObject Returns the parsed adventure
     */
    public JSONObject loadAdventureJson(String filename){
        JSONParser parser = new JSONParser();
        try(Reader reader = new FileReader(filename)){
            JSONObject obj = (JSONObject) parser.parse(reader);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This method loads the adventure from the file given by the user via command line
     * @param inputStream the name of the file
     * @return JSONObject Returns the parsed adventure
     */
    public JSONObject loadAdventureJson(InputStream inputStream){
        JSONParser parser = new JSONParser();
        try(InputStreamReader reader = new InputStreamReader(inputStream)){
            JSONObject obj = (JSONObject) parser.parse(reader);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This method loads the defualt adventure
     * @return JSONObject Returns the parsed adventure
     */
    public JSONObject loadAdventureJson(){
        JSONParser parser = new JSONParser();
        try(Reader reader = new FileReader("src/main/resources/defaultAdven.json")){
            JSONObject obj = (JSONObject) parser.parse(reader);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This method generates the adventure from the parsed object
     * @param obj The parsed JSON object
     * @return Adventure returns the completed adventure
     */
    public Adventure generateAdventure(JSONObject obj){
        Adventure adven = new Adventure();
        this.adventure = (JSONObject) obj.get("adventure");
        JSONArray rooms = (JSONArray) adventure.get("room");
        this.roomGen(rooms,adven);
        JSONArray items = (JSONArray) adventure.get("item");
        this.itemGen(items,adven);
        return adven;
    }
    /**
     * Checks the Json file for errors
     */
    public void checkJsonErrors()throws InvalidJsonException{
        this.checkJsonFakeRoom();
        this.checkJsonNoExits();
        this.checkJsonItems();
    }
    /**
     * Checks the json items
     */
    public void checkJsonItems()throws InvalidJsonException{
        JSONArray items = (JSONArray) this.adventure.get("item");
        long[] itemIds = this.getCheckJsonFakeItemIds(items);
        JSONArray rooms = (JSONArray) this.adventure.get("room");
        this.checkJsonFakeRoomItems(rooms,itemIds);  
    }
    /**
     * Loads the room items to be checked
     * @param rooms the rooms
     * @param itemIds the item ids
     */
    public void checkJsonFakeRoomItems(JSONArray rooms,long[] itemIds)throws InvalidJsonException{
        for(int i=0;i<rooms.size();++i){
            JSONObject room = (JSONObject)rooms.get(i);
            JSONArray loots = (JSONArray) room.get("loot");
            for(int j=0;j<loots.size();++j){
                JSONObject loot = (JSONObject) loots.get(j);
                if(!this.checkJsonFakeItems(loot,itemIds)){
                    throw new InvalidJsonException("Incorrect Item ids found");
                }
            }
        }
    }
    /**
     * Checks if the item id exists
     * @param loot the item
     * @param itemIds the item ids
     * @return Wether or not the item exists
     */
    public boolean checkJsonFakeItems(JSONObject loot,long[] itemIds){
        long itemId = (long) loot.get("id");
        for(int i=0;i<itemIds.length;++i){
            if(itemId == itemIds[i]){
                return true;
            }
        }
        return false;
    }
    /**
     * Gets the item ids
     * @param items the items
     * @return the item ids
     */
    public long[] getCheckJsonFakeItemIds(JSONArray items){
        long[] itemIds = new long[items.size()];
        for(int i=0;i<items.size();++i){
            JSONObject item = (JSONObject) items.get(i);
            itemIds[i] = (long) item.get("id");
        }
        return itemIds;
    }
    /**
     * Gets the room ids
     * @param rooms the room
     * @return the room ids
     */
    public long[] getCheckJsonFakeRoomIds(JSONArray rooms)throws InvalidJsonException{
        long[] roomIds = new long[rooms.size()];
        for(int i=0;i<rooms.size();++i){
            JSONObject room = (JSONObject) rooms.get(i);
            roomIds[i] = (long) room.get("id");
        }
        return roomIds;
    }
    /**
     * Loads the rooms to be checked
     * @param rooms the rooms
     * @param roomIds the room ids
     */
    public void getCheckJsonFakeRoomDirIds(JSONArray rooms,long[] roomIds)throws InvalidJsonException{
        for(int i=0;i<rooms.size();++i){
            JSONObject room = (JSONObject) rooms.get(i);
            JSONArray tempRoomId = (JSONArray) room.get("entrance");
            for(int j=0;j<tempRoomId.size();++j){
                JSONObject tempRoom = (JSONObject) tempRoomId.get(j);
                long roomId = (long) tempRoom.get("id");
                if(!checkJsonFakeRoomId(roomIds,roomId)){
                    throw new InvalidJsonException("Incorrect room ids founds");
                }
            }
        }
    }
    /**
     * Checks the json file rooms
     */
    public void checkJsonFakeRoom()throws InvalidJsonException{
        JSONArray rooms = (JSONArray) this.adventure.get("room");
        long[] roomIds = this.getCheckJsonFakeRoomIds(rooms);
        this.getCheckJsonFakeRoomDirIds(rooms,roomIds);
    }
    /**
     * Checks the room ids
     * @param roomIds the rooms ids
     * @param roomId the room id to check
     * @return Wethere or not the room id exists
     */
    public boolean checkJsonFakeRoomId(long[]roomIds,long roomId){
        for(int i=0;i<roomIds.length;++i){
            if(roomId == roomIds[i]){
                return true;
            }
        }
        return false;
    }
    /**
     * Checks the exits for the json
     */
    public void checkJsonNoExits()throws InvalidJsonException{
        for(Room room: this.adv.listAllRooms()){
            room.setConnectedRoomsNE();
            room.setConnectedRoomsSW();
            room.setConnectedRoomsVer();
            if(room.getConnectedRooms().isEmpty()){
                throw new InvalidJsonException("There are rooms that have no exits");
            }else{
                this.checkJsonInvalidExitsN(room);
            }
        }
    }
    /**
     * Checks if the exit is valid to the North
     * @param room the room to check
     */
    public void checkJsonInvalidExitsN(Room room)throws InvalidJsonException{
        if(room.getConnectedRooms().containsKey("N")){
            Room tempRoom = room.getConnectedRoom("N");
            tempRoom.setConnectedRoomsNE();
            tempRoom.setConnectedRoomsSW();
            tempRoom.setConnectedRoomsVer();
            if(!tempRoom.getConnectedRooms().containsKey("S")){
                throw new InvalidJsonException("Rooms do not properly connect to one another");
            }
        }
        this.checkJsonInvalidExitsS(room);
    }
    /**
     * Checks if the exit is valid to the South
     * @param room the room to check
     */
    public void checkJsonInvalidExitsS(Room room)throws InvalidJsonException{
        if(room.getConnectedRooms().containsKey("S")){
            Room tempRoom = room.getConnectedRoom("S");
            tempRoom.setConnectedRoomsNE();
            tempRoom.setConnectedRoomsSW();
            tempRoom.setConnectedRoomsVer();
            if(!tempRoom.getConnectedRooms().containsKey("N")){
                throw new InvalidJsonException("Rooms do not properly connect to one another");
            }
        }
        this.checkJsonInvalidExitsE(room);
    }
    /**
     * Checks if the exit is valid to the East
     * @param room the room to check
     */
    public void checkJsonInvalidExitsE(Room room)throws InvalidJsonException{
        if(room.getConnectedRooms().containsKey("E")){
            Room tempRoom = room.getConnectedRoom("E");
            tempRoom.setConnectedRoomsNE();
            tempRoom.setConnectedRoomsSW();
            tempRoom.setConnectedRoomsVer();
            if(!tempRoom.getConnectedRooms().containsKey("W")){
                throw new InvalidJsonException("Rooms do not properly connect to one another");
            }
        }
        this.checkJsonInvalidExitsW(room);
    }
    /**
     * Checks if the exit is valid to the West
     * @param room the room to check
     */
    public void checkJsonInvalidExitsW(Room room)throws InvalidJsonException{
        if(room.getConnectedRooms().containsKey("W")){
            Room tempRoom = room.getConnectedRoom("W");
            tempRoom.setConnectedRoomsNE();
            tempRoom.setConnectedRoomsSW();
            tempRoom.setConnectedRoomsVer();
            if(!tempRoom.getConnectedRooms().containsKey("E")){
                throw new InvalidJsonException("Rooms do not properly connect to one another");
            }
        }
        this.checkJsonInvalidExitsUp(room);
    }
    /**
     * Checks if the exit is valid to the Up
     * @param room the room to check
     */
    public void checkJsonInvalidExitsUp(Room room)throws InvalidJsonException{
        if(room.getConnectedRooms().containsKey("up")){
            Room tempRoom = room.getConnectedRoom("up");
            tempRoom.setConnectedRoomsNE();
            tempRoom.setConnectedRoomsSW();
            tempRoom.setConnectedRoomsVer();
            if(!tempRoom.getConnectedRooms().containsKey("down")){
                throw new InvalidJsonException("Rooms do not properly connect to one another");
            }
        }
        this.checkJsonInvalidExitsDown(room);
    }
    /**
     * Checks if the exit is valid to the Down
     * @param room the room to check
     */
    public void checkJsonInvalidExitsDown(Room room)throws InvalidJsonException{
        if(room.getConnectedRooms().containsKey("down")){
            Room tempRoom = room.getConnectedRoom("down");
            tempRoom.setConnectedRoomsNE();
            tempRoom.setConnectedRoomsSW();
            tempRoom.setConnectedRoomsVer();
            if(!tempRoom.getConnectedRooms().containsKey("up")){
                throw new InvalidJsonException("Rooms do not properly connect to one another");
            }
        }
    }
    /**
     * This method generates all the items
     * @param items The items from the Json file
     * @param adven The adventure
     */
    public void itemGen(JSONArray items,Adventure adven){
        for(int i=0;i<items.size();++i){
            JSONObject item = (JSONObject) items.get(i);
            long itemId = (long) item.get("id");
            String name = (String) item.get("name");
            String sDesc = (String) item.get("desc");
            Item newItem = new Item(itemId,name,sDesc);
            this.setItemTypeEdibleToss(newItem,item,adven);
        }
    }
    /**
     * This method sets if the item is edible or tossable
     * @param newItem the item
     * @param item The item from the Json file
     * @param adven The adventure
     */
    public void setItemTypeEdibleToss(Item newItem,JSONObject item,Adventure adven){
        boolean toss = false;
        boolean edible = false;
        try{
            edible = (boolean) item.get("edible");
        }catch(Exception e){}
        try{
            toss = (boolean) item.get("tossable");
        }catch(Exception e){}
        this.setItemTypeReadWear(newItem,item,edible,toss,adven);
    }
    /**
     * This method sets if the item is readable or wearable
     * @param newItem the item
     * @param item The item from the Json file
     * @param edible if the item is edible
     * @param toss if the item is tossable
     * @param adven The adventure
     */
    public void setItemTypeReadWear(Item newItem,JSONObject item,boolean edible,boolean toss,Adventure adven){
        boolean wear = false;
        boolean read = false;
        try{
            wear = (boolean) item.get("wearable");
        }catch(Exception e){}
        try{
            read = (boolean) item.get("readable");
        }catch(Exception e){}
        if(this.addItemEdibleToss(newItem,edible,toss,adven)){
            this.addItemReadWear(newItem,wear,read,adven);
        }
    }
    /**
     * Adds the item with with the correct sub type
     * @param newItem the item
     * @param edible whether the item is edible
     * @param toss whether the item is tossable
     * @param adven the current adventure
     * @return whether the item was added or not
     */
    public boolean addItemEdibleToss(Item newItem,boolean edible,boolean toss,Adventure adven){
        if(edible&& toss){
            adven.addItem(new SmallFood(newItem.getId(),newItem.getName(),newItem.getLongDescription()));
        }else if(edible&& !toss){
            adven.addItem(new Food(newItem.getId(),newItem.getName(),newItem.getLongDescription()));
        }else if(toss){
            adven.addItem(new Weapon(newItem.getId(),newItem.getName(),newItem.getLongDescription()));
        }else{
            return true;
        }
        return false;
    }
     /**
     * Adds the item with with the correct sub type
     * @param newItem the item
     * @param wear whether the item is wearable
     * @param read whether the item is readable
     * @param adven the current adventure
     */
    public void addItemReadWear(Item newItem,boolean wear,boolean read,Adventure adven){
        if(wear&&read){
        BrandedClothing bClothing=new BrandedClothing(newItem.getId(),newItem.getName(),newItem.getLongDescription());
            adven.addItem(bClothing);
        }else if(wear && !read){
            adven.addItem(new Clothing(newItem.getId(),newItem.getName(),newItem.getLongDescription()));
        }else if(read){
            adven.addItem(new Spell(newItem.getId(),newItem.getName(),newItem.getLongDescription()));
        }else{
            adven.addItem(newItem);
        }
    }
    /**
     * This method sets a point to adventure in every room
     * @param rooms An arraylist of rooms
     * @param adven The adventure
     */
    public void setAdventureRoom(ArrayList<Room> rooms,Adventure adven){
        Room room1;
        int i;
        for(i=0;i<adven.listAllRooms().size();++i){
            room1 = rooms.get(i);
            room1.setAdventure(adven);
        }
    }
    /**
     * This method sets a point to adventure in every item
     * @param items An arraylist of items
     * @param adven The adventure
     */
    public void setAdventureItem(ArrayList<Item> items,Adventure adven){
        Item item;
        int i;
        for(i=0;i<adven.listAllItems().size();++i){
            item = items.get(i);
            item.setAdventure(adven);
        }
    }
    /**
     * This method gets the starting room
     * @param rooms An arraylist of items
     * @return Room Returns the starting room
     */
    public Room getStartRoom(ArrayList<Room> rooms){
        int count = 0;
        for(Room room1: rooms){
            if(room1.getStart().equals("true")){
                return room1;
            }else if(count==rooms.size()-1){
                return rooms.get(0);
            }
        ++count;
        }
        return rooms.get(0);
    }
    /**
     * This method generates the rooms from the JSON file
     * @param rooms An JSON array of rooms
     * @param adven The adventure
     */
    public void roomGen(JSONArray rooms,Adventure adven){
        for(int i=0;i<rooms.size();++i){
            ArrayList<String> roomDesc = new ArrayList<String>();
            long[] conRoomIds = {-1,-1,-1,-1,-1,-1};
            JSONObject room = (JSONObject) rooms.get(i);
            long roomId = (long) room.get("id");
            roomDesc.add((String) room.get("name"));
            roomDesc.add((String) room.get("short_description"));
            roomDesc.add((String) room.get("long_description"));
            String start = "false";
            try{
                start = (String) room.get("start");
            }catch(Exception e){}
            roomDesc.add(start);
            this.setRoom(roomDesc,roomId,this.getConRoom(conRoomIds,(JSONArray) room.get("entrance")),room,adven);
        }
    }
    /**
     * This method sets the room
     * @param roomDesc The info about the room
     * @param roomId The id of the room
     * @param conRoomIds The ids of the connected room
     * @param room The room
     * @param adven The adventure
     */
    public void setRoom(ArrayList<String> roomDesc,long roomId,long[] conRoomIds,JSONObject room,Adventure adven){
        try{
            JSONArray loots = (JSONArray) room.get("loot");
            if(!loots.isEmpty()){
                long[] lootRoom = new long[loots.size()];
                for(int j=0;j<loots.size();++j){
                    JSONObject loot = (JSONObject) loots.get(j);
                    lootRoom[j] = (long) loot.get("id");
                }
                Room newRoom = new Room(roomId,roomDesc,conRoomIds,lootRoom);
                adven.addRoom(newRoom);
            }else{
                Room newRoom = new Room(roomId,roomDesc,conRoomIds);
                adven.addRoom(newRoom);
            }
            }catch(NullPointerException e){
                Room newRoom = new Room(roomId,roomDesc,conRoomIds);
                adven.addRoom(newRoom);

            }
    }
    /**
     * This method gets the connected room ids
     * @param conRoomIds A array with all the ids of the connecting rooms
     * @param conRooms JSON array of the rooms
     * @return long[] Returns a array of connected room ids
     */
    public long[] getConRoom(long[] conRoomIds,JSONArray conRooms){
        for(int j=0;j<conRooms.size();++j){
            JSONObject conRoom = (JSONObject) conRooms.get(j);
            long conRoomId = (long) conRoom.get("id");
            String dir = (String) conRoom.get("dir");
            conRoomIds = this.setConRoomNE(dir,conRoomId,conRoomIds);
            conRoomIds = this.setConRoomSW(dir,conRoomId,conRoomIds);
            conRoomIds = this.setConRoomVer(dir,conRoomId,conRoomIds);
        }
        return conRoomIds;
    }
    /**
     * This method sets the connected N and E room ids
     * @param dir The direction of the room
     * @param conRoomId The id of the room
     * @param conRoomIds A array with all the ids of the connecting rooms
     * @return long[] Returns a array of connected room ids
     */
    public long[] setConRoomNE(String dir,long conRoomId,long[]conRoomIds){
        if(dir.equals("N")){
            conRoomIds[0] = conRoomId;
        }
        if(dir.equals("E")){
            conRoomIds[1] = conRoomId;
        }
        return conRoomIds;
    }
    /**
     * This method sets the connected S and W room ids
     * @param dir The direction of the room
     * @param conRoomId The id of the room
     * @param conRoomIds A array with all the ids of the connecting rooms
     * @return long[] Returns a array of connected room ids
     */
    public long[] setConRoomSW(String dir,long conRoomId,long[]conRoomIds){
        if(dir.equals("S")){
            conRoomIds[2] = conRoomId;
        }
        if(dir.equals("W")){
            conRoomIds[2+1] = conRoomId;
        }
        return conRoomIds;
    }
    /**
     * This method sets the connected vertical room ids
     * @param dir The direction of the room
     * @param conRoomId The id of the room
     * @param conRoomIds A array with all the ids of the connecting rooms
     * @return long[] Returns a array of connected room ids
     */
    public long[] setConRoomVer(String dir,long conRoomId,long[]conRoomIds){
        if(dir.equals("up")){
            conRoomIds[2+2] = conRoomId;
        }
        if(dir.equals("down")){
            conRoomIds[2+2+1] = conRoomId;
        }
        return conRoomIds;
    }
    public void printIntro(String name){
        System.out.println("Hello "+name+" and welcome to my game.");
        System.out.println("Before you start please read the following instructions,");
        System.out.println("you can either type go followed by the direction (go N),");
        System.out.println("or can enter look to get a longer description of the room.");
        System.out.println("You can also enter look followed by a\nitems name to get a description of the item");
        System.out.println("Enter quit to quit the game");
    }
    /**
     * This method prints the intro to the user
     * @param name the name of the player
     * @return the intro as a string
     */
    public String printIntroText(String name){
        String str = "Hello "+name+" and welcome to my game.\n";
        str = str +"Before you start please read the following instructions,\n";
        str = str +"you can either type go followed by the direction (go N),\n";
        str = str +"or can enter look to get a longer description of the room.\n";
        str = str +"You can also enter look followed by a\nitems name to get a description of the item\n";
        str = str +"Enter quit to quit the game\n";
        str = str +"Use the menu bar to load a custom/default Json or Save and set your name\n";
        str = str +"Remember to load the correct json before loading your save\n";
        return str;
    }
    /**
     * This method gets the input to be read based on command line arguments or lack there off
     * @param args The arguments from the command line
     * @return InputStream Returns the input stream to be passed to the parser
     */
    public InputStream loadFile(String[] args){
        if(args.length > 0){
            switch(args[0]){
                case "-a":
                    return Game.class.getClassLoader().getResourceAsStream(args[1]);
                default:
                    return Game.class.getClassLoader().getResourceAsStream("defaultAdven.json");
            }
        }else{
            return Game.class.getClassLoader().getResourceAsStream("defaultAdven.json");
        }
    }
    /**
     * This method loads the file based on the name
     * @param name The name of the file
     * @return InputStream Returns the input stream to be passed to the parser
     */
    public InputStream loadFile(String name){
        return Game.class.getClassLoader().getResourceAsStream(name);
    }
    /**
     * This method gets a command from the user
     * @param parse The parser to read the user input
     * @param room The current room
     * @param scnr The scanner
     * @return Command Returns the command the user want to use
     */
    public Command getUserCommand(Parser parse,Room room,Scanner scnr){
        while(true){
            try{
                System.out.println("What would you like to do next?");
                Command com = parse.parseUserCommand(scnr.nextLine());
                if(com.getNoun() != null){
                    com.validNoun(com.getNoun(),room,this.p1);
                }
                return com;
            }catch(InvalidCommandException e){}
        }
    }
    /**
     * This method gets the game to string
     * @return String Returns the game in a string
     */
    public String toString(){
        return null;
    }
    /**
     * This method sets a new player
     * @param p the player
     */
    public void setPlayer(Player p){
        this.p1 = p;
    }
    /**
     * This method quits the game and asks if the user want to save
     * @param scnr The scanner
     * @param player The player
     * @param adven The adventure
     * @return boolean Returns wether or not the user succesfully quits
     */
    public boolean quitGame(Scanner scnr,Player player,Adventure adven){
        System.out.print("Would you like to save your game?(Y/N)\n");
        if(scnr.nextLine().equals("Y")){
            System.out.print("What would you like to name your save:\n");
            player.setPlayerSave(scnr.nextLine());
            this.quit = this.saveGame(adven,player);
            return true;
        }else{
            this.quit = true;
            return true;
        }
    }
    /**
     * This method save the game to a file
     * @param adven The adventure
     * @param player The player
     * @return boolean Returns whether or not the game quit properly
     */
    public boolean saveGame(Adventure adven, Player player){
        try{
        ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(new File((player.getSaveGameName()))));
        o.writeObject(player);
        o.writeObject(adven);
        o.close();
        return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * This method save the game to a file
     * @param theGame The game
     * @param player The player
     * @return boolean Returns whether or not the game quit properly
     */
    public boolean saveGame(Game theGame,Player player){
        try{
        File file = new File((theGame.getP1().getSaveGameName()));
        ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(file));
        o.writeObject(theGame);
        o.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
     /**
     * This method save the game to a file with a custom name
     * @param theGame The game
     * @param saveName The name of the save
     * @return boolean Returns whether or not the game quit properly
     */
    public boolean saveAsGame(Game theGame,String saveName){
        try{
        ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(new File((saveName))));
        o.writeObject(theGame);
        o.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * This method takes an item in the room
     * @param items The items in the room
     * @param com The command
     * @param player The player
     */
    public void takeItem(ArrayList<Item> items,Command com,Player player){
        int i;
        Item item;
        for(i=0;i<items.size();++i){
            item = items.get(i);
            if(com.getNoun().equals(item.getName())){
                player.addInventory(item);
                item.setTaken(true);
                player.getCurrentRoom().getStoredItems().remove(item);
            }
        }
    }
    /**
     * This method looks at a item/room
     * @param items the items in the room
     * @param com the command
     * @param room1 the current room
     */
    public void lookAt(ArrayList<Item> items,Command com,Room room1){
        if(com.getNoun()==null){
            System.out.println(room1.getLongDescription());
        }else{
        for(int i=0;i<items.size();++i){
            Item item = items.get(i);
            if(com.getNoun().equals(item.getName())){
                System.out.println(item.getLongDescription());
            }
        }
        }
    }
    /**
     * This method executes the behaviour for wear
     * @param items the items
     * @param com the command
     * @param p the player
     */
    public void wearItem(ArrayList<Item> items,Command com,Player p){
        for(int i=0;i<items.size();++i){
            Item it = items.get(i);
            if(it.getName().equals(com.getNoun())){
                p.addInventory(it);
                items.remove(i);
                return;
            }
        }
    }
    /**
     * This method executes the behaviour for toss
     * @param items the items
     * @param com the command
     * @param p the player
     */
    public void tossItem(ArrayList<Item> items,Command com,Player p){
        for(int i=0;i<items.size();++i){
            Item it = items.get(i);
            if(it.getName().equals(com.getNoun())){
                it.setTaken(false);
                p.getCurrentRoom().addStoredItem(it);
                p.getInventory().remove(it);
                return;
            }
        }
    }
    /**
     * This method executes the behaviour for eat
     * @param items the items
     * @param com the command
     * @param p the player
     */
    public void eatItem(ArrayList<Item> items,Command com,Player p){
        for(int i=0;i<p.getInventory().size();++i){
            Item it = p.getInventory().get(i);
            if(it.getName().equals(com.getNoun())){
                p.getInventory().remove(i);
            }
        }
    }
    /**
     * This method executes the command the user wanted
     * @param com the command
     * @param items the items in the room
     * @param scnr the scanner
     * @param adven the current adventure
     * @param player the current player
     */
    public void exeCommand(Command com,ArrayList<Item> items,Scanner scnr,Adventure adven,Player player){
        switch(com.getActionWord()){
            case "go":
                player.setCurrentRoom(player.getCurrentRoom().getConnectedRoom(com.getNoun()));
                break;
            default:
                this.exeCommandLookTake(com,items,scnr,adven,player);
        }
    }
    /**
     * This method executes the command the user wanted
     * @param com the command
     * @param items the items in the room
     * @param scnr the scanner
     * @param adven the current adventure
     * @param player the current player
     */
    public void exeCommandLookTake(Command com,ArrayList<Item> items,Scanner scnr,Adventure adven,Player player){
        switch(com.getActionWord()){
            case "look":
                this.lookAt(items,com,player.getCurrentRoom());
                break;
            case "take":
                this.takeItem(items,com,player);
                break;
            default:
                this.exeCommandQuitInven(com,items,scnr,adven,player);
        }
    }
    /**
     * This method executes the command the user wanted
     * @param com the command
     * @param items the items in the room
     * @param scnr the scanner
     * @param adven the current adventure
     * @param player the current player
     */
    public void exeCommandQuitInven(Command com,ArrayList<Item> items,Scanner scnr,Adventure adven,Player player){
        switch(com.getActionWord()){
            case "quit":
                this.quitGame(scnr,player,adven);
                break;
            case "inventory":
                player.printInventory();
                break;
            default:
                this.exeCommandWearToss(com,items,scnr,adven,player);
                break;}
    }
    /**
     * This method executes the command for wear and toss
     * @param com the command
     * @param items the items in the room
     * @param scnr the scanner
     * @param adven the current adventure
     * @param player the current player
     */
    public void exeCommandWearToss(Command com,ArrayList<Item> items,Scanner scnr,Adventure adven,Player player){
        switch(com.getActionWord()){
            case "wear":
                this.wearItem(player.getCurrentRoom().listItems(),com,player);
                break;
            case "toss":
                this.tossItem(player.getInventory(),com,player);
                break;
            default:
                this.exeCommandReadEat(com,items,scnr,adven,player);
                break;}
    }
    /**
     * This method executes the command for read and eat
     * @param com the command
     * @param items the items in the room
     * @param scnr the scanner
     * @param adven the current adventure
     * @param player the current player
     */
    public void exeCommandReadEat(Command com,ArrayList<Item> items,Scanner scnr,Adventure adven,Player player){
        switch(com.getActionWord()){
            case "read":
                break;
            case "eat":
                this.eatItem(player.getCurrentRoom().listItems(),com,player);
                break;
            default:
                break;
        }
    }
    /**
     * This method runs the game algorithm when no save file is given
     * @param theGame the current game instance
     * @param scnr the scanner
     * @param args the arguments from the command line
     */
    public void noSaveRunGame(Game theGame,Scanner scnr,String[] args){
        Parser parse = new Parser();
        Player player = this.p1;
        this.adv = theGame.generateAdventure(theGame.loadAdventureJson(theGame.loadFile(args)));
        this.generateIntro(player,theGame,this.adv);
        player.setCurrentRoom(theGame.getStartRoom(this.adv.listAllRooms()));
        while(!theGame.getQuit()){
            Room room1 = player.getCurrentRoom();
            room1.printRoom();
            theGame.exeCommand(theGame.getUserCommand(parse,room1,scnr),room1.listItems(),scnr,this.adv,player);
            }
    }
    /**
     * This method sets the adventure based on the file name given
     * @param theGame the current game instance
     * @param name the name of the file
     */
    public void setAdventure(Game theGame,String name){
        this.adv = theGame.generateAdventure(theGame.loadAdventureJson(theGame.loadFile(name)));
    }
    /**
     * This method generates and prints the intro
     * @param player The player
     * @param theGame The game
     * @param adven The adventure
     */
    public void generateIntro(Player player,Game theGame,Adventure adven){
        theGame.printIntro(player.getName());
        theGame.setAdventureRoom(adven.listAllRooms(),adv);
        theGame.setAdventureItem(adven.listAllItems(),adv);
    }
    /**
     * This method loads the file
     * @param args the arguements from the command line
     */
    public void loadSave(String[] args){
        try{
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File(args[1])));
        this.p1 = (Player) oi.readObject();
        this.adv= (Adventure) oi.readObject();
        }catch(Exception e){
            System.out.println("Something went wrong when finding or reading the file");
        }
    }
    /**
     * This method runs the game algorithm when a save file is given
     * @param theGame the current game instance
     * @param scnr the scanner
     * @param args the arguments from the command line
     */
    public void saveRunGame(Game theGame,Scanner scnr,String[] args){
            this.loadSave(args);
            Parser parse = new Parser();
            while(!theGame.quit){
                Room room1 = this.p1.getCurrentRoom();
                room1.printRoom();
                theGame.exeCommand(theGame.getUserCommand(parse,room1,scnr),room1.listItems(),scnr,this.adv,this.p1);
            }
    }
    /**
     * This method returns the quit variable
     * @return boolean Returns the quit variable
     */
    private boolean getQuit(){
        return this.quit;
    }
    /**
     * This method gets the text for wear
     * @param p the player
     * @return the text as a string
     */
    public String getWearText(Player p){
        for(Item it:p.getCurrentRoom().listItems()){
            if(it instanceof Clothing){
                Clothing cloth = (Clothing) it;
                return cloth.wear();
            }else if(it instanceof BrandedClothing){
                BrandedClothing cloth = (BrandedClothing) it;
                return cloth.wear();
            }
        }
        return "error";
    }
    /**
     * This method gets the text for eat
     * @param p the player
     * @return the text as a string
     */
    public String getEatText(Player p){
        for(Item it:p.getInventory()){
            if(it instanceof Food){
                Food food = (Food) it;
                return food.eat();
            }else if(it instanceof SmallFood){
                SmallFood food = (SmallFood) it;
                return food.eat();
            }
        }
        return "error";
    }
    /**
     * This method gets the text for toss
     * @param p the player
     * @return the text as a string
     */
    public String getTossText(Player p){
        for(Item it:p.getInventory()){
            if(it instanceof SmallFood){
                SmallFood food = (SmallFood) it;
                return food.toss();
            }else if(it instanceof Weapon){
                Weapon weapon = (Weapon) it;
                return weapon.toss();
            }
        }
        return "error";
    }
    /**
     * This method gets the text for read from the inventory
     * @param p the player
     * @return the text as a string
     */
    public String getReadTextInven(Player p){
        for(Item it:p.getInventory()){
            if(it instanceof BrandedClothing){
                BrandedClothing cloth = (BrandedClothing) it;
                return cloth.read();
            }else if(it instanceof Spell){
                Spell spell = (Spell) it;
                return spell.read();
            }
        }
        return "error";
    }
    /**
     * This method gets the text for read
     * @param p the player
     * @return the text as a string
     */
    public String getReadText(Player p){
        for(Item it:p.getCurrentRoom().listItems()){
            if(it instanceof BrandedClothing){
                BrandedClothing cloth = (BrandedClothing) it;
                return cloth.read();
            }else if(it instanceof Spell){
                Spell spell = (Spell) it;
                return spell.read();
            }
        }
        return this.getReadTextInven(p);
    }
    /**
     * Gets the adventure
     * @return the adventure
     */
    public Adventure getAdv(){
        return this.adv;
    }
    /**
     * Gets the adventure JSONObject
     * @return the adventure
     */
    public JSONObject getAdeventure(){
        return this.adventure;
    }
}
