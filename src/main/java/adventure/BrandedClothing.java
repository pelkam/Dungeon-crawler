package adventure;
import java.io.Serializable;
public class BrandedClothing extends Clothing implements Readable,Serializable{
    /**
     * Default constructor
     */
    public BrandedClothing(){
        super();
    }
    /**
     * Constructor for branded clothing
     * @param iId the item id
     * @param iName the item name
     * @param descrip the item description
     */
    public BrandedClothing(long iId,String iName,String descrip){
        super(iId,iName,descrip);
    }
    /**
     * This method reads the current item
     * @return a string with the text
     */
    public String read(){
        return this.getLongDescription()+"\n";
    }
}
