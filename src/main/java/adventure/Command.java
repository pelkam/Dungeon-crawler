package adventure;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
public class Command {
    enum Com{
        GO {
            @Override
            public String toString(){
                return "go";
            }
        },
        LOOK {
            @Override
            public String toString(){
                return "look";
            }
        },
        TAKE {
            @Override
            public String toString(){
                return "take";
            }
        },
        QUIT {
            @Override
            public String toString(){
                return "quit";
            }
        },
        INVEN {
            @Override
            public String toString(){
                return "inventory";
            }
        },
        EAT {
            @Override
            public String toString(){
                return "eat";
            }
        },
        WEAR {
            @Override
            public String toString(){
                return "wear";
            }
        },
        TOSS {
            @Override
            public String toString(){
                return "toss";
            }
        },
        READ {
            @Override
            public String toString(){
                return "read";
            }
        }
    }
    private String action;
    private String noun;
    private Room room;
    private int i;
    private ArrayList<Item> items;
    private Map<String,Room> validDirections;
    /**
     * Create a command object with default values.  
     * both instance variables are set to null
     * 
     */
    public Command() throws InvalidCommandException {
        this.action = null;
        this.noun = null;
    }

  /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command. 
     * 
     */
    public Command(String command) throws InvalidCommandException{
        this.action = command;
        this.validCommand(command);
        if(!command.equals("take")&&!command.equals("go")){
            this.noun = null;
        }else{
            throw new InvalidCommandException("Invalid command");
        }
        //TODO validate the action word here and throw an exception if it isn't
        // a single-word action
    }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command. 
     * @param what      The second word of the command.
     */
    public Command(String command, String what) throws InvalidCommandException{
        /*//validate the command here and ensure that the noun provided
        // is a legitimate second word for the command
        // throw an exception if not*/
        this.action = command;
        this.validCommand(command);
        this.noun = what;
        if(!this.hasSecondWord()){
            throw new InvalidCommandException("Invalid second word");
        }
    }
    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {
        return this.action;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {
        return this.noun;
    }
    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (noun != null);
    }
    /**
     * This method checks if the command is valid and throws an exception if it is not valid
     * @param command The command to be verified
     * @return String Returns the command if valid
     */
    public String validCommand(String command)throws InvalidCommandException{
        for(Com c: EnumSet.allOf(Com.class)) {
            try {
                if(command.equals(c.toString())) {
                    return command;
                }
            }catch (NullPointerException e){
                throw new InvalidCommandException("Invalid command");
            }
        }
        throw new InvalidCommandException("Invalid command");
    }
    /**
     * This method checks if the Noun the user entered is valid
     * @param theNoun the noun to be verified
     * @param r the current room
     * @param p the player
     * @return String Returns the noun if valid
     */
    public String validNoun(String theNoun,Room r,Player p) throws InvalidCommandException{
        if(this.action.equals(Com.GO.toString())){
            this.noun = this.comGo(r.getConnectedRooms(),theNoun);
        }else if((this.action.equals(Com.LOOK.toString())||(this.action.equals(Com.TAKE.toString()))&&theNoun != null)){
            this.noun = this.comLookTake(r.listItems(),theNoun);
        }else{
            this.noun = this.validNounQuitInven(theNoun,r,p);
        }
        return theNoun;
    }
    /**
     * This method checks if the Noun the user entered is valid
     * @param theNoun the noun to be verified
     * @param r the room
     * @param p the player
     * @return String Returns the noun if valid
     */
    public String validNounQuitInven(String theNoun,Room r,Player p) throws InvalidCommandException{
        if(this.action.equals(Com.QUIT.toString())){
            this.noun = this.comQuit(theNoun);
        }else if(this.action.equals(Com.INVEN.toString())){
            this.noun = this.comInven(theNoun);
        }else{
            this.noun = validNounWearTakeEatToss(theNoun,r,p);
        }
        return theNoun;
    }
    /**
     * This method checks if the noun the user entered is valid
     * @param theNoun the noun
     * @param r the room
     * @param p the player
     * @return the noun
     */
    public String validNounWearTakeEatToss(String theNoun,Room r,Player p)throws InvalidCommandException{
        if(this.action.equals(Com.WEAR.toString())||this.action.equals(Com.READ.toString())){
            this.noun = this.comWearReadRoom(theNoun,r.listItems(),p);
        }else if(this.action.equals(Com.TOSS.toString())||this.action.equals(Com.EAT.toString())){
            this.noun = this.comEatToss(theNoun,p);
        }else{
            throw new InvalidCommandException("Invalid Command");
        }
        return theNoun;
    }
    /**
     * This method returns the command as a string
     * @return String Returns the command as a string
     */
    public String toString(){
        return this.action+" "+this.noun;
    }
    /**
     * This method checks if the direction entered by the user is valid
     * @param validDir the valid directions a user may go
     * @param theNoun the noun the user entered
     * @return String Returns the noun if valid
     */
    public String comGo(Map<String,Room> validDir,String theNoun) throws InvalidCommandException{
        if(validDir.containsKey(theNoun)){
            return theNoun;
        }else{
            throw new InvalidCommandException("Cant go that direction");
        }
    }
    /**
     * This method checks if the user can look at or take an item
     * @param theItems the items in the room
     * @param theNoun the noun the user entered
     * @return String Returns the noun if valid
     */
    public String comLookTake(ArrayList<Item> theItems,String theNoun) throws InvalidCommandException{
        try{
            for(Item it: theItems){
                if(theNoun.equals(it.getName())&& !it.getTaken()){
                    return theNoun;
                }else if(this.getActionWord().equals("look")&&theNoun == null){
                    return theNoun;
                }
            }
            }catch(NullPointerException e){}
        throw new InvalidCommandException("Cant look at/take that");
    }
    /**
     * This method checks if the user entered anything after quit
     * @param theNoun the noun the user entered
     * @return String Returns the noun if valid
     */
    public String comQuit(String theNoun) throws InvalidCommandException{
        if(theNoun == null) {
            return null;
        }else{
            throw new InvalidCommandException("To quit only enter quit");
        }
    }
    /**
     * This method checks if the user entered anything after inventory
     * @param theNoun the noun the user entered
     * @return String Returns the noun if valid
     */
    public String comInven(String theNoun) throws InvalidCommandException{
        if(theNoun == null){
            return null;
        }else{
            throw new InvalidCommandException("To look at your inventory only enter inventory");
        }
    }
    /**
     * This methed checks if the noun entered can be worn or read for items in the room
     * @param theNoun the noun
     * @param theItems the items to check
     * @param p the player
     * @return the noun
     */
    public String comWearReadRoom(String theNoun,ArrayList<Item> theItems,Player p)throws InvalidCommandException{
        for(Item it: theItems){
            if(theNoun.equals(it.getName())){
                if((it instanceof Clothing && !this.getActionWord().equals("read")) || it instanceof BrandedClothing){
                    return theNoun;
                }else if(it instanceof Spell && !this.getActionWord().equals("wear")){
                    return theNoun;
                }
            }
        }
        return this.comWearReadInven(theNoun,p.getInventory());
    }
    /**
     * This methed checks if the noun entered can be worn or read for items in the inventory
     * @param theNoun the noun
     * @param theItems the items to check
     * @return the noun
     */
    public String comWearReadInven(String theNoun,ArrayList<Item> theItems)throws InvalidCommandException{
        for(Item it: theItems){
            if(theNoun.equals(it.getName())){
                if((it instanceof Clothing && !this.getActionWord().equals("read")) || it instanceof BrandedClothing){
                    return theNoun;
                }else if(it instanceof Spell && !this.getActionWord().equals("wear")){
                    return theNoun;
                }
            }
        }
        throw new InvalidCommandException("You cant read/wear that");
    }
    /**
     * This methed checks if the noun entered can be ate or tossed
     * @param theNoun the noun
     * @param p the player
     * @return the noun
     */
    public String comEatToss(String theNoun,Player p)throws InvalidCommandException{
        for(Item it: p.getInventory()){
            if(theNoun.equals(it.getName())){
                if((it instanceof Food && !this.getActionWord().equals("toss")) || it instanceof SmallFood){
                    return theNoun;
                }else if(it instanceof Weapon && !this.getActionWord().equals("eat")){
                    return theNoun;
                }
            }
        }
        throw new InvalidCommandException("You cannot eat/toss that");
    }
    /**
     * This method sets the room
     * @param r the new room
     */
    public void setRoom(Room r){
        this.room = r;
    }
    /**
     * This method sets the items
     * @param its the new items
     */
    public void setItems(ArrayList<Item> its){
        this.items = its;
    }
    /**
     * This method sets the valid directions
     * @param m the new directions
     */
    public void setValidDirections(Map<String,Room> m){
        this.validDirections = m;
    }

    /**
     * This method sets the action word
     * @param a the action word
     */
    public void setActionWord(String a){
        this.action = a;
    }
    /**
     * This method sets the noun
     * @param n the noun
     */
    public void setNoun(String n){
        this.noun = n;
    }
}
