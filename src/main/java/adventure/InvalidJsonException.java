package adventure;
public class InvalidJsonException extends Exception{
    /**
     * This method prints out the error message to the user
     * @param error the error message
     */
    public InvalidJsonException(String error){
        System.out.println(error);
    }
}
