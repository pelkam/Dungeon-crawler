package adventure;
import java.io.Serializable;
public class Food extends Item implements Edible,Serializable{
    /**
     * Default constructor
     */
    public Food(){
        super();
    }
    /**
     * The food constructor
     * @param iId the id of the item
     * @param iName the name of the item
     * @param descrip the description of the item
     */
    public Food(long iId,String iName,String descrip){
        super(iId,iName,descrip);  
    }
    /**
     * This method returns a string telling the user they ate the item
     * @return a string telling the user they ate the item
     */
    public String eat(){
        return "You ate the "+this.getName()+"\n";
    }
}
