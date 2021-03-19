package adventure;
import java.io.Serializable;
public class Weapon extends Item implements Tossable,Serializable{
    /**
     * The default constructor
     */
    public Weapon(){
        super();
    }
    /**
     * The weapon constructor
     * @param iId the item id
     * @param iName the item name
     * @param descrip the item description
     */
    public Weapon(long iId,String iName,String descrip){
        super(iId,iName,descrip);
    }
    /**
     * This method returns a string telling the user the item they tossed
     * @return a string telling the user the item they tossed
     */
    public String toss(){
        return "You tossed the "+this.getName()+"\n";
    }
}
