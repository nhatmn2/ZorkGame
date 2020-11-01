package src.com.company;
//package com.company;

import java.util.*;

final class Direction {
    private String directionName;

    private Direction(String n){
        this.directionName = n;
    }
    public final static Direction North = new Direction("NORTH");
    public final static Direction South = new Direction("SOUTH");
    public final static Direction East = new Direction("EAST");
    public final static Direction West = new Direction("WEST");

    public String toString(){
        return directionName;
    }
}

//==================================================================
// Mapsite
//==================================================================
abstract class MapSite{
    private String name;
    private String description;

    public MapSite(String newName, String newDescription) {
        this.name = newName;
        this.description = newDescription;
    }
    public String getName() {return  name;}
    public void setName(String newName){ this.name = newName;}

    public String getDescription() {return description; }
    public void setDescription(String newDescription){this.description = newDescription; }

    abstract void enter();
}

//========================================================================
// Abstract item
//========================================================================
abstract class Item {
    protected int damageValue;


    abstract int getDamageValue();
}

//========================================================================
// Abstract Monster
//========================================================================
abstract class Monster {
    protected int health;
    protected int damage;
    protected String monsterDescription;
    protected String monsterName;

    public int getDamage() { return damage; }
    public int getHealth() { return health; }
    public String getName() { return monsterName; }
    public String getMonsterDescription() { return monsterDescription; }

    public void setHealth(int health) { this.health = health; }
}

//=========================================================================
// Monsters
//=========================================================================
class Slime extends Monster {
    public Slime() {
       this.health = 10;
       this.damage = 1;
       this.monsterDescription = "A slimy but easy to deal with monster.";
       this.monsterName = "Slime";
    }
}

//=========================================================================
// Items
//=========================================================================
class Sword extends Item {
    public Sword() {
        this.damageValue = 25;
    }

    public int getDamageValue() { return damageValue; }
}


//==================================================================
// Player Class
//==================================================================
class Player extends MapSite {
    private int health;
    private String[] sack;
    private Room roomPosition;  //the room at which the player present
    private Stack<Room> roomLog = new Stack<>(); //this is to keep track of the room player has visited

    //private String name; //I leave it hear if you want to have the player's name


    //constructor for creating the player
    public Player(Room startRoom){
        super("player 1", "this is player 1");
        this.health = 100;
        this.sack = new String[10];
        this.roomPosition = startRoom;
        this.roomLog.add(startRoom);
        //System.out.println(roomPosition);
    }

    //get health
    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }

    //this function is used to get the player position (i.e in what room)
    public Room getPosition() {
        return roomPosition;
    }

    //searches the sack if that item exists
    public boolean searchSack(String name) {
        for(int i = 0; i < sack.length; ++i) {
            if(sack.equals(name)) {
                return true; //returns true if the item does exist
            }
        }
        //returns false if the item inside the sack does not exist
        return false;
    }

    //this function is used to set the player position
    public void setRoom (Room destinationRoom){
        this.roomPosition = destinationRoom;
    }

    //check for this function
    public void makePlayerMove(Door doorBetweenRooms){
        this.roomPosition = doorBetweenRooms.getDestinationRoom() ;
        if(!roomLog.contains(doorBetweenRooms.getDestinationRoom()))
            roomLog.pop();
        else
            roomLog.push(doorBetweenRooms.getDestinationRoom());

    }

    public void enter() {}

    //this function to check for the valid more

}

//============================================================================================================
//create a class Room
//============================================================================================================

class Room extends MapSite {
    //this is for the room itself
    private int roomNumber;
    //this field is for the const starting room
    private static int roomCount = 1;
    private MapSite northSide;
    private MapSite southSide;
    private MapSite eastSide;
    private MapSite westSide;

    //constructor for room
    public Room(String roomName, String roomDescription) {
        super(roomName, roomDescription);
        roomNumber = roomCount++;
        //System.out.println("creating room number " + roomNumber);

    }


    //method setSide() inside Room
    public void setSide(Direction d, MapSite site){
        if (d == Direction.North){
            northSide = site;
        }
        else if (d == Direction.South){
            southSide = site;
        }
        else if (d == Direction.East){
            eastSide = site;
        }
        else if (d == Direction.West){
            westSide = site;
        }
        //System.out.println("setting " + d.toString() + " side of " + this.toString() + " to " + site.toString());
    }

    //method getSide() inside Room
    public MapSite getSide(Direction d){
        MapSite result = null;
        if (d == Direction.North){
            result = northSide;
        }
        else if (d == Direction.South){
            result = southSide;
        }
        else if (d == Direction.East){
            result = eastSide;
        }
        else if (d == Direction.West){
            result = westSide;
        }
        return result;
    }
    //method toString() inside Room
//    public String toString(){
//        return "Room number" + Integer.toString(roomNumber);
//    }

    void enter(){
        System.out.println("enter to Room " + roomNumber);
    }



}
//=======================================================================================================
//create class Wall
//=======================================================================================================

class Wall extends MapSite{
    private int wallNumber;
    private static int wallCount = 1;

    //constructor for wall
    public Wall(){
        super("","");
        wallNumber = wallCount++;
        System.out.println("creating Wall number" +  Integer.toString(wallNumber));
    }
    //method toString() inside Wall
    public String toString(){
        return "Wall number " + Integer.toString(wallNumber);
    }

    void enter(){
        System.out.println("there is a wall " + wallNumber);
    }
}

//=======================================================================================================
//create class Door
//=======================================================================================================

class Door extends MapSite{
    private int doorNumber;
    private static int doorCount = 1;
    private Room roomStart;
    private Room roomDestination;

    //constructor for door
    public Door(Room r1, Room r2, String doorName){
        super(doorName,"");
        doorNumber = doorCount++;
        //System.out.println("creating a door number " + doorNumber + " between " + r1 + " and " + r2);
        this.roomStart = r1;
        this.roomDestination = r2;
    }

    public Room getDestinationRoom(){
        return roomDestination;
    }
    public Room getStartRoom(){
        return roomStart;
    }


    //method toString() inside Door
    public String toString(){
        return "Door number " + Integer.toString(doorNumber);
    }


    void enter(){
        //System.out.println("Enter through " + doorNumber + " from " + room1 + " to " + room2);
    }

}
//=============================================================================================================
//create class Maze
//=============================================================================================================

class Maze {
    //this class has and Arraylist to keep track of the number of maze
    private List<Room> roomList = new ArrayList<Room>();

    public Maze(){
        //System.out.println("creating a Maze ");
    }

    void addRoom(Room r){
        //check if the room number is not in the arraylist then add
        if(!roomList.contains(r)){
            roomList.add(r);
        }
    }

    public Room getRoom(String room) {
        for(Room roomComp : roomList) {
            if(roomComp.toString().equalsIgnoreCase(room)) {
                return roomComp;
            }
        }

        //in case it cannot find any room that matches the parameter
        return roomList.get(0);
    }


    public void showRoomList() {
        for(Room roomInMaze : roomList){
            System.out.println(roomInMaze);
        }
    }

}


//========================================================================================================
//create class itemList
//========================================================================================================

class ItemList {
    private List<String> itemList = new ArrayList<String>();

    //this method is to add the tool into toolList
    void addItems(String item) {
        if(!itemList.contains(item)){
            itemList.add(item);
        }
    }

    //this method is to print the toolList to the user
    void showItemList(){
        for(String tool : itemList){
            System.out.println(tool);
        }
    }
}

//======================================================================================================
//create livingRoom
//======================================================================================================

class LivingRoom extends Room {
    //create livingRoom with tools
    private MazeGame underground;
    //private final String description = "You are entering the living room. In here you can find all the tool you need for your journey\n";
    //when the livingRoom is created the livingRoomItemList is also created
    LivingRoom() {
        super("living room", "you are entering the living room");
        //I created another underground Maze after you enter the cellar.
        //this.underground = newMaze;
        //System.out.println(description);
        //this is for creating item list inside living room!
        //getDescription();
        ItemList livingRoomItemList = new ItemList();
        livingRoomItemList.addItems("Trophy case");
        livingRoomItemList.addItems("Lamp");
        livingRoomItemList.addItems("Rug");
        livingRoomItemList.addItems("Old Sword");
        livingRoomItemList.showItemList();
    }
//    public String getDescription(){
//        return description;
//    }

    public String toString() {
        return "living room";
    }
}

//=====================================================================================================
//create livingRoom's door
//=====================================================================================================
//
//class LivingRoomDoor extends Door{
//    LivingRoomDoor (Room r1, Room r2){
//        super(r1, r2);
//    }
//    public String toString() {
//        return super.toString();
//    }
//}

//=====================================================================================================
//create Kitchen
//=====================================================================================================
class Kitchen extends Room {
    //create kitchen with tools
    //can add description of the kitchen later
    //private final String description = "You are entering the kitchen.";
    Kitchen() {
        super("kitchen", "You are entering the kitchen");
        //System.out.println(description);
        //getDescription();
        ItemList kitchenItemList = new ItemList();
        kitchenItemList.addItems("Sack");
        kitchenItemList.addItems("Garlic");
        kitchenItemList.addItems("Bottle of water");
        kitchenItemList.showItemList();
    }

//    public String getDescription() {
//        return description;
//    }

    public String toString() {
        return "kitchen";
    }

}

//=======================================================================================================
//create Attic
//=======================================================================================================

class Attic extends Room {
    //don't forget to create items for the attic

    //private final String description = "You are entering the attic.";
    Attic() {
        super("attic", "You are entering the attic");
        //System.out.println(description);
        //getDescription();
        ItemList atticItemList = new ItemList();
        atticItemList.addItems("Rope");
        atticItemList.addItems("Brick");
        atticItemList.addItems("Axe");
        atticItemList.showItemList(); //just to test whether the items are showing up
    }
//    public String getDescription() {
//        return description;
//    }

    public String toString() { return "attic"; }
}

//=====================================================================================================
//Creating Starting Point/Grassy Fields
//==========================================================================
class GrassyFields extends Room{
    //we're gonna have to implement a getCommand and a putWordsTogether function in each room

    //private final String description = "You are in a grassy field.";
    GrassyFields() {
        super("grassy fields", "You are in a grassy field");
        //System.out.println(description);
        //setName("Grassy Fields");
    }
    //public String getDescription() { return description; }

    public String toString() { return "grassy field"; }
}

//=======================================================================================================
//create Cellar
//=======================================================================================================
class Cellar extends Room {
    //private final String description = "You are entering the cellar.";
    Cellar() {
        super("cellar", "You are entering the cellar");
        //getDescription();
    }
//    public String getDescription(){
//        return description;
//    }

    public String toString(){
        return "cellar";
    }


}
//======================================================================================================
//create Lava Room
//======================================================================================================
class LavaRoom extends Room{
    //private final String description  = "You are entering the lava room. ";
    LavaRoom(){
        super("lava room", "you are entering lava room");
        //getDescription();
    }
//    public String getDescription(){
//        return description;
//    }

    public String toString(){
        return "lava room.";
    }
}

//======================================================================================================
//create Regeneration Room
//======================================================================================================
class RegenerationRoom extends Room{
    private final String description = "You are entering the regenerating room.";
    RegenerationRoom(){
        super("regeneration room", "You are entering the regenerating room");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "regenerating room.";
    }
}

//======================================================================================================
//create Glacier Cave
//======================================================================================================
class GlacierCave extends Room{
    //private final String description = "You are entering the glacier cave.";
    GlacierCave(){
        super("glacier cave", "You are entering the glacier cave");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "glacier cave.";
    }
}

//======================================================================================================
//create Egypt Room
//======================================================================================================
class EgyptRoom extends Room{
    private final String description = "You are entering the egypt room.";
    EgyptRoom(){
        super("egypt room","You are entering the egypt room");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "Egypt room. ";
    }
}
//======================================================================================================
//create Coal Mine
//======================================================================================================
class CoalMine extends Room{
    //private final String description = "You are entering the coal mine.";
    CoalMine(){
        super("coal mine", "You are entering the coal mine");
        getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "coal mine. ";
    }
}
//======================================================================================================
//create Blacksmith Workshop
//======================================================================================================
class BlacksmithWorkshop extends Room{
    //private final String description = "You are entering the Blacksmith Workshop.";
    BlacksmithWorkshop(){
        super("blacksmith workshop", "You are entering the Blacksmith Workshop");
        getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "blacksmith workshop. ";
    }
}

//======================================================================================================
//create Troll Room
//======================================================================================================
class TrollRoom extends Room{
    //private final String description = "You are entering the Troll Room.";
    TrollRoom(){
        super("troll room", "You are entering the Troll Room");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "troll room. ";
    }
}

//======================================================================================================
//create Riddle Room
//======================================================================================================
class RiddleRoom extends Room{
    private final String description = "You are entering the Riddle Room.";
    RiddleRoom(){
        super("riddle room", "You are entering the Riddle Room");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "riddle room. ";
    }
}

//======================================================================================================
//create Dragon's Lair
//======================================================================================================
class DragonLair extends Room{
    //private final String description = "You are entering the Dragon's Lair.";
    DragonLair(){
        super("dragon lair", "You are entering the dragon's lair");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "dragon's lair. ";
    }
}
//======================================================================================================
//create Treasure Room
//======================================================================================================
class TreasureRoom extends Room{
    //private final String description = "You are entering the Treasure Room.";
    TreasureRoom(){
        super("treasure room", "You are entering the treasure room");
        //getDescription();
    }
    //    public String getDescription(){
//        return description;
//    }
    public String toString(){
        return "treasure room. ";
    }
}
//======================================================================================================
//create class MazeGame
//======================================================================================================
class MazeGame { //this is where the factory for the zork game happens
    public Maze makeMaze(){return new Maze();}

    public Room makeRoom(String newName){
        return new Room(newName,"");
    }


    public Wall makeWall(){return new Wall();}

    public Door makeDoor(Room r1, Room r2, String newDoorName){ return new Door(r1,r2, newDoorName);}

    public Maze createMaze(){
        Maze aMaze = makeMaze();
        Room r1 = makeRoom("room1");
        Room r2 = makeRoom("room2");
        Door theDoor = makeDoor(r1, r2, "the door");
        aMaze.addRoom(r1);
        aMaze.addRoom(r2);
        return aMaze;
    }

}
//=========================================================================================================
//create an underground map
//=========================================================================================================
class ZorkUnderground extends MazeGame{
    public Room makeRoom(String kindOfRoomUnderground){
        if (kindOfRoomUnderground.equals("Cellar"))
            return new Cellar();
        else if (kindOfRoomUnderground.equals("LavaRoom"))
            return new LavaRoom();
        else if (kindOfRoomUnderground.equals("EgyptRoom"))
            return new EgyptRoom();
        else if (kindOfRoomUnderground.equals("CoalMine"))
            return new CoalMine();
        else if (kindOfRoomUnderground.equals("RegenerationRoom"))
            return new RegenerationRoom();
        else if (kindOfRoomUnderground.equals("GlacierCave"))
            return new GlacierCave();
        else if (kindOfRoomUnderground.equals("BlacksmithWorkshop"))
            return new BlacksmithWorkshop();
        else if (kindOfRoomUnderground.equals("TrollRoom"))
            return new TrollRoom();
        else if (kindOfRoomUnderground.equals("RiddleRoom"))
            return new RiddleRoom();
        else if (kindOfRoomUnderground.equals("DragonLair"))
            return new DragonLair();
        else if (kindOfRoomUnderground.equals("TreasureRoom"))
            return new TreasureRoom();
        else
            return new Room("","");
    }
    public Maze createMaze(){
        Maze ZorkMazeUnderground = makeMaze();

        Room newCellar = makeRoom("Cellar");
        Room newLavaRoom = makeRoom("LavaRoom");
        Room newEgyptRoom = makeRoom("EgyptRoom");
        Room newRegenerationRoom = makeRoom("RegenerationRoom");
        Room newCoalMine = makeRoom("CoalMine");
        Room newBlacksmithWorkshop = makeRoom("BlacksmithWorkshop");
        Room newGlacierCave = makeRoom("GlacierCave");
        Room newTrollRoom = makeRoom("TrollRoom");
        Room newRiddleRoom = makeRoom("RiddleRoom");
        Room newDragonLair = makeRoom("DragonLair");
        Room newTreasureRoom = makeRoom("TreasureRoom");

        Door newCellarAndLavaRoomDoor = makeDoor(newCellar, newLavaRoom, "door");
        Door newLavaRoomAndCellarDoor = makeDoor(newLavaRoom, newCellar, "door");

        Door newLavaRoomAndEgyptRoomDoor = makeDoor(newLavaRoom, newEgyptRoom,"door");
        Door newEgyptRoomAndLavaRoomDoor = makeDoor(newEgyptRoom, newLavaRoom, "door");

        Door newEgyptRoomAndRegenerationRoomDoor = makeDoor(newEgyptRoom, newRegenerationRoom,"door");
        Door newRegenerationRoomAndEgyptRoomDoor = makeDoor(newRegenerationRoom, newEgyptRoom, "door");

        Door newRegenerationRoomAndCoalMineDoor = makeDoor(newRegenerationRoom, newCoalMine, "door");
        Door newCoalMineAndRegenerationRoomDoor = makeDoor(newCoalMine, newRegenerationRoom, "door");

        Door newEgyptRoomAndCoalMineDoor = makeDoor(newEgyptRoom, newCoalMine,"door");
        Door newCoalMineAndEgyptRoomDoor = makeDoor(newCoalMine, newEgyptRoom, "door");

        Door newRegenerationRoomAndGlacierCaveDoor = makeDoor(newRegenerationRoom, newGlacierCave,"door");
        Door newGlacierCaveAndRegenerationDoor = makeDoor(newGlacierCave, newRegenerationRoom, "door");

        Door newCoalMineAndBlacksmithWorkshopDoor = makeDoor(newCoalMine, newBlacksmithWorkshop,"door");
        Door newBlacksmithWorkShopAndCoalMineDoor = makeDoor(newBlacksmithWorkshop, newCoalMine, "door");

        Door newGlacierCaveAndBlacksmithWorkshopDoor = makeDoor(newGlacierCave, newBlacksmithWorkshop,"door");
        Door newBlacksmithWorkshopAndGlacierCaveDoor = makeDoor(newBlacksmithWorkshop, newGlacierCave, "door");

        Door newGlacierCaveAndTrollRoomDoor = makeDoor(newGlacierCave, newTrollRoom,"door");
        Door newTrollRoomAndGlacierCaveDoor = makeDoor(newTrollRoom, newGlacierCave, "door");

        Door newTrollRoomAndRiddleRoomDoor = makeDoor(newTrollRoom, newRiddleRoom,"door");
        Door newRiddleRoomAndTrollRoomDoor = makeDoor(newRiddleRoom, newTrollRoom, "door");

        Door newRiddleRoomAndDragonLairDoor = makeDoor(newTrollRoom, newDragonLair,"door");
        Door newDragonLairAndRiddleRoomDoor = makeDoor(newDragonLair, newTrollRoom, "door");

        Door newDragonLairAndTreasureRoomDoor = makeDoor(newDragonLair, newTreasureRoom,"door");
        Door newTreasureRoomAndDragonLairDoor = makeDoor(newTreasureRoom, newDragonLair, "door");


        ZorkMazeUnderground.addRoom(newCellar);
        ZorkMazeUnderground.addRoom(newLavaRoom);

        //setting sides for cellar
        newCellar.setSide(Direction.North, makeWall());
        newCellar.setSide(Direction.South, makeWall());
        newCellar.setSide(Direction.East, makeWall());
        newCellar.setSide(Direction.West, newCellarAndLavaRoomDoor);

        //setting sides for lava room
        newLavaRoom.setSide(Direction.North, newLavaRoomAndEgyptRoomDoor);
        newLavaRoom.setSide(Direction.South, makeWall());
        newLavaRoom.setSide(Direction.East, newLavaRoomAndCellarDoor);
        newLavaRoom.setSide(Direction.West, makeWall());

        //setting sides for egypt room
        newEgyptRoom.setSide(Direction.North, newEgyptRoomAndRegenerationRoomDoor);
        newEgyptRoom.setSide(Direction.South, newEgyptRoomAndLavaRoomDoor);
        newEgyptRoom.setSide(Direction.East, newEgyptRoomAndCoalMineDoor);
        newEgyptRoom.setSide(Direction.West, makeWall());

        //setting sides for regeneration room
        newRegenerationRoom.setSide(Direction.North, makeWall());
        newRegenerationRoom.setSide(Direction.South, newRegenerationRoomAndCoalMineDoor);
        newRegenerationRoom.setSide(Direction.East, newRegenerationRoomAndGlacierCaveDoor);
        newRegenerationRoom.setSide(Direction.West, newRegenerationRoomAndEgyptRoomDoor);

        //setting sides for coal mine
        newCoalMine.setSide(Direction.North, newCoalMineAndRegenerationRoomDoor);
        newCoalMine.setSide(Direction.South, makeWall());
        newCoalMine.setSide(Direction.East, newCoalMineAndBlacksmithWorkshopDoor);
        newCoalMine.setSide(Direction.West, newCoalMineAndEgyptRoomDoor);

        //setting sides for glacier cave
        newGlacierCave.setSide(Direction.North, makeWall());
        newGlacierCave.setSide(Direction.South, newGlacierCaveAndBlacksmithWorkshopDoor);
        newGlacierCave.setSide(Direction.East, newGlacierCaveAndTrollRoomDoor);
        newGlacierCave.setSide(Direction.West, newGlacierCaveAndRegenerationDoor);

        //setting sides for blacksmithWorkshop
        newBlacksmithWorkshop.setSide(Direction.North, newBlacksmithWorkshopAndGlacierCaveDoor);
        newBlacksmithWorkshop.setSide(Direction.South, makeWall());
        newBlacksmithWorkshop.setSide(Direction.East, makeWall());
        newBlacksmithWorkshop.setSide(Direction.West, newBlacksmithWorkShopAndCoalMineDoor);

        //setting sides for troll room
        newTrollRoom.setSide(Direction.North, makeWall());
        newTrollRoom.setSide(Direction.South, newTrollRoomAndRiddleRoomDoor);
        newTrollRoom.setSide(Direction.East, makeWall());
        newTrollRoom.setSide(Direction.West, newTrollRoomAndGlacierCaveDoor);

        //setting sides for riddle room
        newRiddleRoom.setSide(Direction.North, newRiddleRoomAndTrollRoomDoor);
        newRiddleRoom.setSide(Direction.South, newRiddleRoomAndDragonLairDoor);
        newRiddleRoom.setSide(Direction.East, makeWall());
        newRiddleRoom.setSide(Direction.West, makeWall());

        //setting sides for dragon lair
        newDragonLair.setSide(Direction.North, newDragonLairAndRiddleRoomDoor);
        newDragonLair.setSide(Direction.South, makeWall());
        newDragonLair.setSide(Direction.East, makeWall());
        newDragonLair.setSide(Direction.West, newDragonLairAndTreasureRoomDoor);

        //setting sides for treasure room
        newTreasureRoom.setSide(Direction.North, makeWall());
        newTreasureRoom.setSide(Direction.South, makeWall());
        newTreasureRoom.setSide(Direction.East, newTreasureRoomAndDragonLairDoor);
        newTreasureRoom.setSide(Direction.West, makeWall());


        return ZorkMazeUnderground;
    }

}

//=============================================================================
// Interface WordParser
//=============================================================================
/*abstract interface WordParser {
    //public void checkLegitCommand(List<String> words);
    public boolean checkLegitCommand(List<String> words);
    abstract boolean putWordsTogether(List<String> words);
}*/

//=========================================================================================================
//create a livingRoomAndKitchenMaze
//=========================================================================================================
class ZorkMazeGame extends MazeGame{ //this is where you actually snap all the rooms together
    //overload and override the makeRoom
    public Room makeRoom(String kindOfRoom){
        if(kindOfRoom.equals("LivingRoom"))
            return new LivingRoom();
        else if(kindOfRoom.equals("Kitchen"))
            return new Kitchen();
            //we need to put more else if every time we create the new room...........
        else if(kindOfRoom.equals("Attic"))
            return new Attic(); //new room class
        else if(kindOfRoom.equals("GrassyFields"))
            return new GrassyFields();
        else
            return new Room("","");

    }
    //override the create Maze
    public Maze createMaze(){
        Maze ZorkMaze = makeMaze();

        Room newLivingRoom = makeRoom("LivingRoom");
        Room newKitchen = makeRoom("Kitchen");
        Room newAttic = makeRoom("Attic");
        Room newGrassyFields = makeRoom("GrassyFields");

        //gotta add a connection to the kitchen and the attic
        Door newLivingRoomAndKitchenDoor = makeDoor(newLivingRoom, newKitchen,"door");
        Door newKitchenAndLivingRoomDoor = makeDoor(newKitchen, newLivingRoom, "door");

        //"door" to the attic
        Door newKitchenDoorAndAttic = makeDoor(newKitchen, newAttic,"door");
        Door newAtticAndKitchenDoor = makeDoor(newAttic, newKitchen, "door");

        //connect grassyfields to the living room
        Door newGrassyFieldsAndLivingRoom = makeDoor(newGrassyFields, newLivingRoom,"door");
        Door newLivingRoomAndGrassyFieldDoor = makeDoor(newLivingRoom, newGrassyFields, "door");

        ZorkMaze.addRoom(newLivingRoom);
        ZorkMaze.addRoom(newKitchen);

        //new room Attic
        ZorkMaze.addRoom(newAttic);

        //new "room" GrassyFields
        ZorkMaze.addRoom(newGrassyFields);

        //walls for the living room
        newLivingRoom.setSide(Direction.North, makeWall());
        newLivingRoom.setSide(Direction.South,makeWall());
        newLivingRoom.setSide(Direction.East, newLivingRoomAndKitchenDoor);
        newLivingRoom.setSide(Direction.West, newLivingRoomAndGrassyFieldDoor);

        //walls for the kitchen
        newKitchen.setSide(Direction.North, makeWall());
        newKitchen.setSide(Direction.South, makeWall());
        newKitchen.setSide(Direction.West, newKitchenAndLivingRoomDoor);
        newKitchen.setSide(Direction.East, makeWall());

        //walls for the attic
        newAttic.setSide(Direction.North, makeWall());
        newAttic.setSide(Direction.South, makeWall());
        newAttic.setSide(Direction.West, makeWall());
        newAttic.setSide(Direction.East, newKitchenDoorAndAttic);

        //"walls" for the grassy fields
        newGrassyFields.setSide(Direction.North, makeWall());
        newGrassyFields.setSide(Direction.South, makeWall());
        newGrassyFields.setSide(Direction.West, makeWall());
        newGrassyFields.setSide(Direction.East, newGrassyFieldsAndLivingRoom);

        //we need to add more doors and rooms and the relation between everytime we add rooms to map......

        return ZorkMaze;
    }
}

public class Zork {
    public static void main(String[] args){
        MazeGame game = new ZorkMazeGame();
        Maze maze = game.createMaze();
        maze.showRoomList();
        //System.out.println("Hello");
        System.out.println();

        Player newPlayer = new Player(maze.getRoom("Grassy Fields"));
        Sword sword = new Sword();

//        System.out.println();
//        System.out.println(newPlayer.getPosition().getSide(Direction.East).getName());

        Scanner scan = new Scanner(System.in);
        String input = "";
        String words[];
        boolean winningCondition = false;

        while(!winningCondition) {
            System.out.println("What do you want to do?");
            System.out.print(">");
            input = scan.nextLine().toUpperCase();
            words = input.split(" "); //split by the spaces read in
            List<String> list = Arrays.asList(words); //this holds the commands

            if(list.isEmpty()) {
                System.out.println("Please input a command.\n");
            }
            else if(checkLegitCommand(list)){ //this function is for checking if the command is correct
                if(isValidMove(newPlayer, list)) { //this is to check if they input any of the keywords

                }
                else if(!isValidMove(newPlayer, list)){
                    System.out.println("Please input a valid command.");
                    continue;
                }
            }
        }

        //most likely needs a loop here to check whether the game is finished or not
        /*        StartGame();*/
    }

//    public static void StartGame() {
//        /*StartingPoints startingPoint = new StartingPoints();
//        startingPoint.grassyFieldsStart();*/
//    }

//    public void checkUserInput() {
//
//    }

    public static boolean checkLegitCommand(List<String> words) {
        String[] directions = {
                "NORTH", "SOUTH", "EAST", "WEST", "FORWARD", "BACKWARDS",
                "LEFT", "RIGHT", "UPSTAIRS", "DOWNSTAIRS", "CELLAR", "WINDOW", //the window is basically go forward or go north
                "DOOR", "KITCHEN", "ROOM", "LIVINGROOM"
        };

        //keywords for actions
        String[] actions = {
                "ATTACK", "SEARCH", "UNLOCK", "JUMP", "TAKE", "LOOK", "GO", "OPEN",
                "INVENTORY", "OPEN", "SAVE", "LOOK", "AROUND", "EXAMINE", "USE"
        };

        //Might need an array of items
        String[] items = {
                "LEAFLET", "MAILBOX", "WINDOW", "TROPHY", "SWORD", "POTION"
        };

        List<String> keywords = new ArrayList<String>();
        boolean legitCommand = false;
        boolean bAction = false;
        boolean bDirection = false;
        boolean bItem = false;

        String direction;
        String action;
        String item;

        //check for actions(verbs)
        for(int i = 0; i < actions.length; ++i) {
            if(words.contains(actions[i])) {
                System.out.println("Found action! " + actions[i]);
                bAction = true;

                //setAction(actions[i]);
                action = actions[i];

                keywords.add(action);
            }
        }

        //check for directions
        for(int i = 0; i < directions.length; ++i) {
            //this shit works
            if(words.contains(directions[i])) {
                System.out.println("Found direction! " + directions[i]);
                bDirection = true;

                direction = directions[i];

                keywords.add(direction);
            }
        }

        //check for items
        for(int i = 0; i < items.length; ++i) {
            if(words.contains(items[i])) {
                System.out.println("Found item! " + items[i]);
                bItem = true;

                item = items[i];

                keywords.add(item);
            }
        }

        if((bAction && bDirection) || (bAction && bItem)) {
            legitCommand = true;
        }
        else {
            legitCommand = false;
        }

        return legitCommand;
    }

    //this function is for checking Valid Move --> valid move happened when there is a door at a certain direction
    public static boolean isValidMove(Player player, List<String> words) {
        boolean x = false;
        if(words.contains("OPEN") && words.contains("DOOR")) {
            //create another starting point in the living room class
            //use this to check if its a wrong move
            x = true;
        }
        else if(words.contains("GO")) {
            if(words.contains("EAST") && player.getPosition().getSide(Direction.East).getName().equals("door")) {
                //return false if the direction has a wall
                x = true;
            }
            else if(words.contains("WEST") && player.getPosition().getSide(Direction.West).getName().equals("door")){
                x = true;
            }
            else if(words.contains("NORTH") && player.getPosition().getSide(Direction.North).getName().equals("door")){
                x = true;
            }
            else if(words.contains("SOUTH") && player.getPosition().getSide(Direction.South).getName().equals("door")){
                x = true;
            }
            else{
                System.out.println("Valid move. You can go that way.");
                x = false;
            }
            return x;
        }
        return x;
    }

    public static boolean Battle(Player player, Sword sword, Monster monster) {
        Scanner scan = new Scanner(System.in);
        System.out.println("=================================================");
        System.out.println("A " + monster.getName() + " appeared!");

        while(monster.getHealth() > 0) {
            System.out.println("\tYour health: " + player.getHealth());
            System.out.println("\tThe enemies health: " + monster.getHealth());
            System.out.println("\tWhat would you like to do? ");
            System.out.println("\t1. Attack the monster");
            System.out.println("\t2. Use an item");
            System.out.println("\t3. Run [This will put you back to the previous room]");

            String input = scan.nextLine();
            if(input.equals("1")) {
                //check whether they have a sword
                if(player.searchSack("Sword")) {
                    System.out.println("\tYou strike the " + monster.getName() + " for "
                                        + sword.getDamageValue() + " damage!");
                    System.out.println("\tYou take " + monster.getDamage() + " from the monster!");
                    player.setHealth(player.getHealth() - monster.getDamage());

                    if(player.getHealth() < 1) {
                        System.out.println("\t>You've taken way too much damage, you die from you incompetence.");
                        //set the win condition to true and give them a game over
                        break;
                    }
                }
            }
            else if(input.equals("2")) {
                //check how many potions the player has
                if(player.searchSack("Potion")) {

                }
            }
            else if(input.equals("3")) {
                System.out.println("You ran away from the " + monster.getName() + "!");
                //monster will still be in that room
                break;
            }
            else {
                System.out.println("Please input a the given commands.");
                continue;
            }

        }

    }
}