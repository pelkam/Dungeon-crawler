package adventure;
import java.io.Serializable;
public class Clothing extends Item implements Wearable,Serializable{
    /**
     * Default constructor
     */
    public Clothing(){
        super();
    }
    /**
     * Constructor for clothing
     * @param iId the item id
     * @param iName the item name
     * @param descrip the item description
     */
    public Clothing(long iId,String iName,String descrip){
        super(iId,iName,descrip);
    }
    /**
     * This method wears the current item
     * @return a string with the text
     */
    public String wear(){
        return "You equiped "+this.getName()+"\n";
    }
}
