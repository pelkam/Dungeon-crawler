package adventure;
import java.io.Serializable;
public class Spell extends Item implements Readable,Serializable{
    /**
     * The default constructor
     */
    public Spell(){
        super();
    }
    /**
     * The spell constructor
     * @param iId the id of the item
     * @param iName the name of the item
     * @param descrip the description of the item
     */
    public Spell(long iId,String iName,String descrip){
        super(iId,iName,descrip);
    }
    /**
     * This method returns the description of the item
     * @return the description of the item
     */
    public String read(){
        return this.getLongDescription()+"\n";
    }
}
