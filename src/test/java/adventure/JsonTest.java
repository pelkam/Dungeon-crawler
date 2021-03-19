package adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class JsonTest{
    private Game workingGame;
    private Game brokenGame;
@Before
public void setup(){
    this.workingGame = new Game();
    this.workingGame.setAdventure(workingGame,"defaultAdven.json");
    this.workingGame.setAdventureRoom(this.workingGame.getAdv().listAllRooms(),this.workingGame.getAdv());
    this.workingGame.setAdventureItem(this.workingGame.getAdv().listAllItems(),this.workingGame.getAdv());
    this.brokenGame = new Game();
    this.brokenGame.setAdventure(brokenGame,"brokenAdven.json");
    this.brokenGame.setAdventureRoom(this.brokenGame.getAdv().listAllRooms(),this.brokenGame.getAdv());
    this.brokenGame.setAdventureItem(this.brokenGame.getAdv().listAllItems(),this.brokenGame.getAdv());

}
@Test
public void testDefaultAdventureNoExits()throws InvalidJsonException{
    this.workingGame.checkJsonNoExits();
    for(Room room:this.workingGame.getAdv().listAllRooms()){
        assertFalse(room.getConnectedRooms().isEmpty());
    }
}
@Test(expected = InvalidJsonException.class)
public void testBrokenAdventureNoExits()throws InvalidJsonException{
    brokenGame.checkJsonNoExits();
}
@Test
public void testDefaultAdventureBrokenExits(){
    try{
        for(Room room:this.workingGame.getAdv().listAllRooms()){
            room.setConnectedRoomsNE();
            room.setConnectedRoomsSW();
            room.setConnectedRoomsVer();
            workingGame.checkJsonInvalidExitsN(room);
        }
        }catch(InvalidJsonException j){
            fail("Should not throw exception");
    }
}
@Test(expected = InvalidJsonException.class)
public void testBrokenAdventureBrokenExits()throws InvalidJsonException{
    for(Room room:this.brokenGame.getAdv().listAllRooms()){
        room.setConnectedRoomsNE();
        room.setConnectedRoomsSW();
        room.setConnectedRoomsVer();
        brokenGame.checkJsonInvalidExitsN(room);
    }
}
@Test
public void testDefaultAdventureRoomIds(){
    try{
        workingGame.checkJsonFakeRoom();
    }catch(InvalidJsonException k){
        fail("Should not throw exception");
    }
}
@Test(expected = InvalidJsonException.class)
public void testBrokenAdventureRoomIds()throws InvalidJsonException{
    brokenGame.checkJsonFakeRoom();
}
@Test
public void testDefaultAdventureItems(){
    try{
        workingGame.checkJsonItems();
    }catch(InvalidJsonException i){
        fail("Should not throw exception");
    }
}
@Test(expected = InvalidJsonException.class)
public void testBrokenAdventureItems()throws InvalidJsonException{
    brokenGame.checkJsonItems();
}
}
