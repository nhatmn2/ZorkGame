package src.com.company;
//package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

//============================================================================
// Player Class
//============================================================================
class Player {
    private int health = 100;
    private String[] sack = new String[10];
    private String position;



}

//============================================================================================================
//create an abstract super class MapSite
//============================================================================================================

abstract class MapSite{
    abstract void enter();
}

//============================================================================================================
//create a class Room
//============================================================================================================

class Room extends MapSite {
    //this is for the word parsing for each room
    //keywords for directions
    String[] directions = {
            "NORTH", "SOUTH", "EAST", "WEST", "FORWARD", "BACKWARDS",
            "LEFT", "RIGHT", "UPSTAIRS", "DOWNSTAIRS", "CELLAR", "WINDOW", //the window is basically go forward or go north
            "DOOR", "KITCHEN"
    };

    //keywords for actions
    String[] actions = {
            "ATTACK", "SEARCH", "UNLOCK", "JUMP", "TAKE", "LOOK", "GO", "OPEN",
            "INVENTORY", "OPEN", "SAVE"
    };

    //Might need an array of items
    String[] items = {
            "LEAFLET", "MAILBOX", "WINDOW", "TROPHY", "SWORD"
    };

    private String direction;
    private String action;
    private String item;


    private boolean bDirection; //the 'b' just means boolean
    private boolean bAction;
    boolean bItem;

    //accessors
    public String getDirection() { return direction; }
    public String getAction() { return action; }
    public String getItem() { return item; }

    public boolean getBDirection() { return bDirection; }
    public boolean getBAction() { return bAction; }
    public boolean getBItem() { return bItem; }

    //mutators
    public void setBDirection(boolean bDirection) { this.bDirection = bDirection; }
    public void setBAction(boolean bAction) { this.bAction = bAction; }
    public void setBItem(boolean bItem) { this.bItem = bItem; }

    public void setDirection(String direction) { this.direction = direction; }
    public void setAction(String action) { this.action = action; }
    public void setItem(String item) { this.item = item; }

    //this is for the room itself
    private int roomNumber;
    //this field is for the const starting room
    private static int roomCount = 1;
    private MapSite northSide;
    private MapSite southSide;
    private MapSite eastSide;
    private MapSite westSide;

    //constructor for room
    public Room() {
        roomNumber = roomCount++;
        System.out.println("creating room number " + roomNumber);
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
        System.out.println("setting " + d.toString() + " side of " + this.toString() + " to " + site.toString());
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
    public String toString(){
        return "Room number" + Integer.toString(roomNumber);
    }

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
    private Room room1;
    private Room room2;

    //constructor for door
    public Door(Room r1, Room r2){
        doorNumber = doorCount++;
        System.out.println("creating a door number " + doorNumber + " between " + r1 + " and " + r2);
        this.room1 = r1;
        this.room2 = r2;
    }

    //method toString() inside Door
    public String toString(){
        return "Door number " + Integer.toString(doorNumber);
    }

    void enter(){
        System.out.println("Enter through " + doorNumber + " from " + room1 + " to " + room2);
    }

}
//=============================================================================================================
//create class Maze
//=============================================================================================================

class Maze{
    //this class has and Arraylist to keep track of the number of maze
    private List<Room> roomList = new ArrayList<Room>();

    public Maze(){
        System.out.println("creating a Maze ");
    }

    void addRoom(Room r){
        //check if the room number is not in the arraylist then add
        if(!roomList.contains(r)){
            roomList.add(r);
        }
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

class LivingRoom extends Room implements WordParser {
    //create livingRoom with tools
    private MazeGame underground;
    private final String description = "You are entering the living room. In here you can find all the tool you need for your journey\n";
    //when the livingRoom is created the livingRoomItemList is also created
    LivingRoom(MazeGame newMaze) {
        super();
        //I created another underground Maze after you enter the cellar.
        this.underground = newMaze;
        //System.out.println(description);
        //this is for creating item list inside living room!
        getDescription();
        ItemList livingRoomItemList = new ItemList();
        livingRoomItemList.addItems("Trophy case");
        livingRoomItemList.addItems("Lamp");
        livingRoomItemList.addItems("Rug");
        livingRoomItemList.addItems("Old Sword");
        livingRoomItemList.showItemList();
    }
    public String getDescription(){
        return description;
    }
    public String toString() {
        return "living room";
    }

    public void startingPoint() {
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        String input = "";
        String words[];

        System.out.println("You see a hatch on the ground and another door leading to the kitchen.");
        while(running) {
            System.out.println("Where do you want to go?");
            System.out.print(">");
            input = scan.nextLine().toUpperCase();

            words = input.split(" "); //split by the spaces read in
            List<String> list = Arrays.asList(words);

            if(input.isEmpty()) {
                System.out.println("Please input a command.\n");
            }
            else if(checkLegitCommand(list)){ //this still needs work, most likey rework checkLegitCommand
                putWordsTogether(list);
                //here will update the players location
                //since there are items here, update the sack if the player picks up any
                running = false;
            }

        }
    }

    public void getCommand(List<String> words) {
        //check if the incoming list of strings is empty
        if(words.isEmpty())
        {
            System.out.println("Please input a command.");
        }
        checkLegitCommand(words);
    }

            //void
    public boolean checkLegitCommand(List<String> words) {
        List<String> keywords = new ArrayList<String>();
        boolean legitCommand = false;

        //check for actions(verbs)
        for(int i = 0; i < actions.length; ++i) {
            if(words.contains(actions[i])) {
                System.out.println("Found action! " + actions[i]);
                setBAction(true);

                setAction(actions[i]);

                keywords.add(getAction());
            }
        }

        //check for directions
        for(int i = 0; i < directions.length; ++i) {
            //this shit works
            if(words.contains(directions[i])) {
                System.out.println("Found direction! " + directions[i]);
                setBDirection(true); //you have to access the actual variable
                //this.bDirection = true;

                setDirection(directions[i]);

                keywords.add(getDirection());
            }
        }

        //check for items
        for(int i = 0; i < items.length; ++i) {
            if(words.contains(items[i])) {
                System.out.println("Found item! " + items[i]);
                setBItem(true);
                setItem(items[i]);

                keywords.add(getItem());
            }
        }

        if((getBAction() && getBDirection()) || (getBAction() && getBItem())) {
            legitCommand = true;
        }
        else {
            legitCommand = false;
        }

        //pass the keywords to the final class to determine the final action
        //putWordsTogether(keywords);

        return legitCommand;
    }

    public void putWordsTogether(List<String> words) {
        //here will be actions leading to either the cellar or the kitchen
        if(words.contains("GO") && words.contains("KITCHEN")) {
            //player will end up in the kitchen
            Kitchen kitchen = new Kitchen();
        }
        else {

        }
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
    private final String description = "You are entering the kitchen.";
    Kitchen() {
        super();
        //System.out.println(description);
        getDescription();
        ItemList kitchenItemList = new ItemList();
        kitchenItemList.addItems("Sack");
        kitchenItemList.addItems("Garlic");
        kitchenItemList.addItems("Bottle of water");
        kitchenItemList.showItemList();
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "kitchen";
    }
}
//=======================================================================================================
//create Attic
//=======================================================================================================

class Attic extends Room {
    //don't forget to create items for the attic

    private final String description = "You are entering the attic.";
    Attic() {
        super();
        //System.out.println(description);
        getDescription();
        ItemList atticItemList = new ItemList();
        atticItemList.addItems("Rope");
        atticItemList.addItems("Brick");
        atticItemList.addItems("Axe");
        atticItemList.showItemList(); //just to test whether the items are showing up
    }
    public String getDescription() {
        return description;
    }

    public String toString() { return "attic"; }
}

//=====================================================================================================
//Creating Starting Point/Grassy Fields
//==========================================================================
class GrassyFields extends Room implements WordParser{
    //we're gonna have to implement a getCommand and a putWordsTogether function in each room

    List<String> list = new ArrayList<String>();

    private final String description = "You are in a grassy field.";
    GrassyFields() {
        super();
        System.out.println(description);
    }

    public String toString() { return "grassy field"; }

    //the true starting point
    public void startingPoint() {
        Scanner scan = new Scanner(System.in);

        String input = "";
        String words[];
        boolean running = true;

        //loop to keep asking what the player wants to do
        //changed the code for checkLegitCommand to return a boolean if it found a combination of words
        //if it returns a true, then it will pass it into the putWordsTogether method to determine the action
        System.out.println("You wake up in front of a house.");
        while(running) {
            System.out.println("Where do you want to go?");
            System.out.print(">");
            input = scan.nextLine().toUpperCase();

            words = input.split(" "); //split by the spaces read in
            List<String> list = Arrays.asList(words);

            if(input.isEmpty()) {
                System.out.println("Please input a command.\n");
            }
            else if(checkLegitCommand(list)){ //this still needs work, most likey rework checkLegitCommand
                putWordsTogether(list);
                //also update the players location
                running = false;
            }

        }

        /*words = input.split(" "); //split by the spaces read in
        List<String> list = Arrays.asList(words);*/

        //getCommand(list);
    }

    //checks if the list is null, then passes the list to another function
    /*public void getCommand(List<String> words) {
        //check if the incoming list of strings is empty
        if(words.isEmpty())
        {
            System.out.println("Please input a command.");
        }
        checkLegitCommand(words);
    }*/

            //void
    public boolean checkLegitCommand(List<String> words) {
        List<String> keywords = new ArrayList<String>();
        boolean legitCommand = false;

        //check for actions(verbs)
        for(int i = 0; i < actions.length; ++i) {
            if(words.contains(actions[i])) {
                System.out.println("Found action! " + actions[i]);
                setBAction(true);

                setAction(actions[i]);

                keywords.add(getAction());
            }
        }

        //check for directions
        for(int i = 0; i < directions.length; ++i) {
            //this shit works
            if(words.contains(directions[i])) {
                System.out.println("Found direction! " + directions[i]);
                setBDirection(true); //you have to access the actual variable
                //this.bDirection = true;

                setDirection(directions[i]);

                keywords.add(getDirection());
            }
        }

        //check for items
        for(int i = 0; i < items.length; ++i) {
            if(words.contains(items[i])) {
                System.out.println("Found item! " + items[i]);
                setBItem(true);
                setItem(items[i]);

                keywords.add(getItem());
            }
        }

        if((getBAction() && getBDirection()) || (getBAction() && getBItem())) {
            legitCommand = true;
        }
        else {
            legitCommand = false;
        }

        //pass the keywords to the final class to determine the final action
        //putWordsTogether(keywords);

        return legitCommand;
    }

    public void putWordsTogether(List<String> words) {
        //if the command is "open door" then the player will end up in the living room
        //still trying to figure out how that is going to work
        //player class can be updated in this function to keep track of proces
        if(words.contains("OPEN") && words.contains("DOOR")) {
            ZorkUnderground zorkUnderground = new ZorkUnderground();
            LivingRoom livingRoom = new LivingRoom(zorkUnderground);
            //create another starting point in the living room class
            livingRoom.startingPoint();
        }
        else {

        }
    }

}

//=======================================================================================================
//create Cellar
//=======================================================================================================
class Cellar extends Room {
    private final String description = "You are entering the cellar.";
    Cellar() {
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }

    public String toString(){
        return "cellar";
    }


}
//======================================================================================================
//create Lava Room
//======================================================================================================
class LavaRoom extends Room{
    private final String description  = "You are entering the lava room. ";
    LavaRoom(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }

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
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "regenerating room.";
    }
}

//======================================================================================================
//create Glacier Cave
//======================================================================================================
class GlacierCave extends Room{
    private final String description = "You are entering the glacier cave.";
    GlacierCave(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
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
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "Egypt room. ";
    }
}
//======================================================================================================
//create Coal Mine
//======================================================================================================
class CoalMine extends Room{
    private final String description = "You are entering the coal mine.";
    CoalMine(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "coal mine. ";
    }
}
//======================================================================================================
//create Blacksmith Workshop
//======================================================================================================
class BlacksmithWorkshop extends Room{
    private final String description = "You are entering the Blacksmith Workshop.";
    BlacksmithWorkshop(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "blacksmith workshop. ";
    }
}

//======================================================================================================
//create Troll Room
//======================================================================================================
class TrollRoom extends Room{
    private final String description = "You are entering the Troll Room.";
    TrollRoom(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
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
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "riddle room. ";
    }
}

//======================================================================================================
//create Dragon's Lair
//======================================================================================================
class DragonLair extends Room{
    private final String description = "You are entering the Dragon's Lair.";
    DragonLair(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "dragon's lair. ";
    }
}
//======================================================================================================
//create Treasure Room
//======================================================================================================
class TreasureRoom extends Room{
    private final String description = "You are entering the Treasure Room.";
    TreasureRoom(){
        super();
        getDescription();
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "treasure room. ";
    }
}
//======================================================================================================
//create class MazeGame
//======================================================================================================
class MazeGame { //this is where the factory for the zork game happens
    public Maze makeMaze(){return new Maze();}

    public Room makeRoom(){return new Room();}

    public Wall makeWall(){return new Wall();}

    public Door makeDoor(Room r1, Room r2){ return new Door(r1,r2);}

    public Maze createMaze(){
        Maze aMaze = makeMaze();
        Room r1 = makeRoom();
        Room r2 = makeRoom();
        Door theDoor = makeDoor(r1, r2);
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
            return new Room();
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

        Door newCellarAndLavaRoomDoor = makeDoor(newCellar, newLavaRoom);
        Door newLavaRoomAndEgyptRoomDoor = makeDoor(newLavaRoom, newEgyptRoom);
        Door newEgyptRoomAndRegenerationRoomDoor = makeDoor(newEgyptRoom, newRegenerationRoom);
        Door newRegenerationRoomAndCoalMineDoor = makeDoor(newRegenerationRoom, newCoalMine);
        Door newEgyptRoomAndCoalMineDoor = makeDoor(newEgyptRoom, newCoalMine);
        Door newRegenerationRoomAndGlacierCaveDoor = makeDoor(newRegenerationRoom, newGlacierCave);
        Door newCoalMineAndBlacksmithWorkshopDoor = makeDoor(newCoalMine, newBlacksmithWorkshop);
        Door newGlacierCaveAndBlacksmithWorkshopDoor = makeDoor(newGlacierCave, newBlacksmithWorkshop);
        Door newGlacierCaveAndTrollRoomDoor = makeDoor(newGlacierCave, newTrollRoom);
        Door newTrollRoomAndRiddleRoomDoor = makeDoor(newTrollRoom, newRiddleRoom);
        Door newRiddleRoomAndDragonLairDoor = makeDoor(newTrollRoom, newDragonLair);
        Door newDragonLairAndTreasureRoomDoor = makeDoor(newDragonLair, newTreasureRoom);

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
        newLavaRoom.setSide(Direction.East, newCellarAndLavaRoomDoor);
        newLavaRoom.setSide(Direction.West, makeWall());

        //setting sides for egypt room
        newEgyptRoom.setSide(Direction.North, newEgyptRoomAndRegenerationRoomDoor);
        newEgyptRoom.setSide(Direction.South, newLavaRoomAndEgyptRoomDoor);
        newEgyptRoom.setSide(Direction.East, newEgyptRoomAndCoalMineDoor);
        newEgyptRoom.setSide(Direction.West, makeWall());

        //setting sides for regeneration room
        newRegenerationRoom.setSide(Direction.North, makeWall());
        newRegenerationRoom.setSide(Direction.South, newRegenerationRoomAndCoalMineDoor);
        newRegenerationRoom.setSide(Direction.East, newRegenerationRoomAndGlacierCaveDoor);
        newRegenerationRoom.setSide(Direction.West, newEgyptRoomAndRegenerationRoomDoor);

        //setting sides for coal mine
        newCoalMine.setSide(Direction.North, newRegenerationRoomAndCoalMineDoor);
        newCoalMine.setSide(Direction.South, makeWall());
        newCoalMine.setSide(Direction.East, newCoalMineAndBlacksmithWorkshopDoor);
        newCoalMine.setSide(Direction.West, newEgyptRoomAndCoalMineDoor);

        //setting sides for glacier cave
        newGlacierCave.setSide(Direction.North, makeWall());
        newGlacierCave.setSide(Direction.South, newGlacierCaveAndBlacksmithWorkshopDoor);
        newGlacierCave.setSide(Direction.East, newGlacierCaveAndTrollRoomDoor);
        newGlacierCave.setSide(Direction.West, newRegenerationRoomAndGlacierCaveDoor);

        //setting sides for blacksmithWorkshop
        newBlacksmithWorkshop.setSide(Direction.North, newGlacierCaveAndBlacksmithWorkshopDoor);
        newBlacksmithWorkshop.setSide(Direction.South, makeWall());
        newBlacksmithWorkshop.setSide(Direction.East, newGlacierCaveAndTrollRoomDoor);
        newBlacksmithWorkshop.setSide(Direction.West, newCoalMineAndBlacksmithWorkshopDoor);

        //setting sides for troll room
        newTrollRoom.setSide(Direction.North, makeWall());
        newTrollRoom.setSide(Direction.South, newTrollRoomAndRiddleRoomDoor);
        newTrollRoom.setSide(Direction.East, makeWall());
        newTrollRoom.setSide(Direction.West, newGlacierCaveAndTrollRoomDoor);

        //setting sides for riddle room
        newRiddleRoom.setSide(Direction.North, newTrollRoomAndRiddleRoomDoor);
        newRiddleRoom.setSide(Direction.South, newRiddleRoomAndDragonLairDoor);
        newRiddleRoom.setSide(Direction.East, makeWall());
        newRiddleRoom.setSide(Direction.West, makeWall());

        //setting sides for dragon lair
        newDragonLair.setSide(Direction.North, newRiddleRoomAndDragonLairDoor);
        newDragonLair.setSide(Direction.South, makeWall());
        newDragonLair.setSide(Direction.East, makeWall());
        newDragonLair.setSide(Direction.West, newDragonLairAndTreasureRoomDoor);

        //setting sides for treasure room
        newTreasureRoom.setSide(Direction.North, makeWall());
        newTreasureRoom.setSide(Direction.South, makeWall());
        newTreasureRoom.setSide(Direction.East, newDragonLairAndTreasureRoomDoor);
        newTreasureRoom.setSide(Direction.West, makeWall());


        return ZorkMazeUnderground;
    }
}

//=============================================================================
// Interface WordParser
//=============================================================================
abstract interface WordParser {
    //public void checkLegitCommand(List<String> words);
    public boolean checkLegitCommand(List<String> words);
    abstract void putWordsTogether(List<String> words);
}

//=========================================================================================================
//create a livingRoomAndKitchenMaze
//=========================================================================================================
class ZorkMazeGame extends MazeGame{ //this is where you actually snap all the rooms together
    //overload and override the makeRoom
    public Room makeRoom(String kindOfRoom){
        ZorkUnderground zorkUnderground = new ZorkUnderground();
        if(kindOfRoom.equals("LivingRoom"))
            return new LivingRoom(zorkUnderground);
        else if(kindOfRoom.equals("Kitchen"))
            return new Kitchen();
            //we need to put more else if every time we create the new room...........
        else if(kindOfRoom.equals("Attic"))
            return new Attic(); //new room class
        else if(kindOfRoom.equals("GrassyFields"))
            return new GrassyFields();
        else
            return new Room();

    }
    //override the create Maze
    public Maze createMaze(){
        Maze ZorkMaze = makeMaze();

        Room newLivingRoom = makeRoom("LivingRoom");
        Room newKitchen = makeRoom("Kitchen");
        Room newAttic = makeRoom("Attic");
        Room newGrassyFields = makeRoom("GrassyFields");

        //gotta add a connection to the kitchen and the attic
        Door newlivingRoomAndKitchenDoor = makeDoor(newLivingRoom, newKitchen);

        //"door" to the attic
        Door newKitchenDoorAndAttic = makeDoor(newKitchen, newAttic);

        //connect grassyfields to the living room
        Door newGrassyFieldsAndLivingRoom = makeDoor(newGrassyFields, newLivingRoom);

        ZorkMaze.addRoom(newLivingRoom);
        ZorkMaze.addRoom(newKitchen);

        //new room Attic
        ZorkMaze.addRoom(newAttic);

        //new "room" GrassyFields
        ZorkMaze.addRoom(newGrassyFields);

        //walls for the living room
        newLivingRoom.setSide(Direction.North, makeWall());
        newLivingRoom.setSide(Direction.South,makeWall());
        newLivingRoom.setSide(Direction.East, makeWall());
        newLivingRoom.setSide(Direction.West, newlivingRoomAndKitchenDoor);

        //walls for the kitchen
        newKitchen.setSide(Direction.North, makeWall());
        newKitchen.setSide(Direction.South, makeWall());
        newKitchen.setSide(Direction.West, makeWall());
        newKitchen.setSide(Direction.East, newlivingRoomAndKitchenDoor);

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

public class Zork{
    public static void main(String[] args){
        MazeGame game = new ZorkMazeGame();
        Maze maze = game.createMaze();
        maze.showRoomList();
        System.out.println("Hello");
        System.out.println();

        //most likely needs a loop here to check whether the game is finished or not
        StartGame();

    }

    public static void StartGame() {
        GrassyFields grassyFields = new GrassyFields();

        grassyFields.startingPoint();
    }
}