## Class Adventure
| method sig                                       | responsibility                   | instance variable used | other class methods called | object used with method calls | lines of code |
|--------------------------------------------------|----------------------------------|------------------------|----------------------------|-------------------------------|---------------|
| public Adventure()                               | Default Adventure constructor    | rooms,items,desc       | n/a                        | n/a                           | 4             |
| public ArrayList<Room> listAllRooms()            | Gets all the rooms               | rooms                  | n/a                        | Adventure                     | 3             |
| public ArrayList<Item> listAllItems()            | Gets all the items               | items                  | n/a                        | Adventure                     | 3             |
| public String getCurrentRoomDescription()        | Gets the description of the room | desc                   | n/a                        | Adventure                     | 3             |
| public void addRoom(Room room)                   | Adds a room                      | rooms                  | n/a                        | Adventure                     | 3             |
| public void addItem(Item item)                   | Adds a item                      | items                  | n/a                        | Adventure                     | 3             |
| public void setCurrentRoomDescription(Room room) | Sets the current description     | desc                   | getLongDescription()       | Adventure                     | 3             |
