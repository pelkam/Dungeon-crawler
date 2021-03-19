## Class Item
| method sig                                        | responsibility                       | instance variable used | other class methods called         | object used with method calls | lines of code |
|---------------------------------------------------|--------------------------------------|------------------------|------------------------------------|-------------------------------|---------------|
| public Item()                                     | Default item constructor             | name,id,desc,taken     | n/a                                | n/a                           | 6             |
| public Item(long iID,String iName,String descrip) | Item constructor                     | name,id,desc,taken     | n/a                                | n/a                           | 6             |
| public String getName()                           | Gets the name                        | name                   | n/a                                | Item                          | 3             |
| public void setName(String n)                     | Sets the name                        | name                   | n/a                                | Item                          | 3             |
| public String getLongDescription()                | Gets the description                 | desc                   | n/a                                | Item                          | 3             |
| public void setDescription(String d)              | Sets the description                 | desc                   | n/a                                | Item                          | 3             |
| public long getId()                               | Gets the description                 | id                     | n/a                                | Item                          | 3             |
| public Room getContainingRoom()                   | Gets the containing room of the item | adv,id                 | listAllRooms(),listItems(),getId() | Item                          | 10            |
| public void setAdventure(Adventure adven)         | Sets the adventure                   | adv                    | n/a                                | Item                          | 3             |
| public void setTaken(boolean x)                   | Sets taken                           | taken                  | n/a                                | Item                          | 3             |
| public void setId(long iD)                        | Sets the id                          | id                     | n/a                                | Item                          | 3             |
| public String toString()                          | Gets the name and description        | n/a                    | getName(),getLongDescription()     | Item                          | 3             |
| public boolean getTaken()                         | Gets taken                           | taken                  | n/a                                | Item                          | 3             |
| public setRooms(ArrayList<Room> r)                | Sets the rooms                       | rooms                  | n/a                                | Item                          | 3             |
| public setItems(ArrayList<Item> its)              | Sets the items                       | item                   | n/a                                | Item                          | 3             |
