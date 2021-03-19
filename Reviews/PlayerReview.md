## Class Player
| method sig                              | responsibility                 | instance variable used          | other class methods called | object used with method calls | lines of code |
|-----------------------------------------|--------------------------------|---------------------------------|----------------------------|-------------------------------|---------------|
| Player()                                | Default player constructor     | name,saveName,curRoom,inventory | n/a                        | n/a                           | 6             |
| Player(String pName)                    | Player constructor             | name,saveName,curRoom,inventory | n/a                        | n/a                           | 6             |
| public void setCurrentRoom(Room room)   | Sets the current room          | curRoom                         | n/a                        | Player                        | 3             |
| public Room getCurrentRoom()            | Gets the current room          | curRoom                         | n/a                        | Player                        | 3             |
| public ArrayList getInventory()         | Gets the inventory             | inventory                       | n/a                        | Player                        | 3             |
| public String getName()                 | Gets the players name          | name                            | n/a                        | Player                        | 3             |
| public void setName(String n)           | Sets the players name          | name                            | n/a                        | Player                        | 3             |
| public String getSaveGameName()         | Gets the name of the save      | saveName                        | n/a                        | Player                        | 3             |
| public void addInventory(Item item)     | Adds item to inventory         | inventory                       | n/a                        | Player                        | 8             |
| public void setInventory(ArrayList i)   | Sets the inventory             | inventory                       | n/a                        | Player                        | 3             |
| public void setPlayerSave(String pName) | Sets the name of the save      | nameSave                        | n/a                        | Player                        | 3             |
| public void printInventory()            | Prints the inventory           | inventory                       | n/a                        | Player                        | 10            |
| public String getInventoryText()        | Gets the inventory as a string | n/a                             | n/a                        | Player                        | 12            |
