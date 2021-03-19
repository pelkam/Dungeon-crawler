package adventure;

public class InvalidCommandException extends Exception{
    /**
     * This method prints out the error message to the user
     * @param error the error message
     */
    public InvalidCommandException(String error){
        System.out.println(error);
    }
}
