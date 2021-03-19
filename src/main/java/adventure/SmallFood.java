package adventure;
import java.io.Serializable;
public class SmallFood extends Food implements Tossable,Serializable{
    /**
     * Default Constructor
     */
    public SmallFood(){
        super();
    }
    /**
     * Constructor for small food
     * @param iId the id of the item
     * @param iName the name of the item
     * @param descrip the description of the item
     */
    public SmallFood(long iId,String iName,String descrip){
        super(iId,iName,descrip);
    }
    /**
     *  Returns a string with the tossed items name
     * @return String telling the user the item was tossed
     */ 
    public String toss(){
        return "You tossed the "+this.getName()+"\n";
    }
}
