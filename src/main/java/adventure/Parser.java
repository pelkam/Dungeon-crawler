package adventure;
public class Parser{
    private String [] userComSplit;
    /**
     * This method parses the users input and returns a generated command
     * @param userCommand the users command
     * @return Command Returns the user command
     */
    public Command parseUserCommand(String userCommand) throws InvalidCommandException{
        if(userCommand != null && !userCommand.isEmpty()){
            userComSplit = userCommand.split(" ");
        }
        if(userComSplit.length<2){
            Command s = new Command(userComSplit[0]);
            return s;
        }else{
            Command s = new Command(userComSplit[0],userComSplit[1]);
            return s;
        }
    }
    /**
     * This method returns all the command the user can enter
     * @return String Returns all the commands the user may enter 
     */
    public String allCommands(){
        return "go, look, take, inventory, quit";
    }
}
