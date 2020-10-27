package src.com.company;
//package com.company;


import java.security.Key;
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

class LivingRoom extends Room {
    //create livingRoom with tools
    private final String description = "You are entering the living room. In here you can find all the tool you need for your journey";
    //when the livingRoom is created the livingRoomItemList is also created
    LivingRoom() {
        super();
        System.out.println(description);
        //this is for creating item list inside living room!
        ItemList livingRoomItemList = new ItemList();
        livingRoomItemList.addItems("Trophy case");
        livingRoomItemList.addItems("Lamp");
        livingRoomItemList.addItems("Rug");
        livingRoomItemList.addItems("Old Sword");
        livingRoomItemList.showItemList();
    }
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
//creating Kitchen
//=====================================================================================================
class Kitchen extends Room {
    //create kitchen with tools
    //can add description of the kitchen later
    private final String description = "You are entering the kitchen.";
    Kitchen() {
        super();
        System.out.println(description);
        ItemList kitchenItemList = new ItemList();
        kitchenItemList.addItems("Sack");
        kitchenItemList.addItems("Garlic");
        kitchenItemList.addItems("Bottle of water");
        kitchenItemList.showItemList();
    }
    public String toString() {
        return "kitchen";
    }
}

class Attic extends Room {
    //don't forget to create items for the attic

    private final String description = "You are entering the attic.";
    Attic() {
        super();
        System.out.println(description);
        ItemList atticItemList = new ItemList();
        atticItemList.addItems("Rope");
        atticItemList.addItems("Brick");
        atticItemList.addItems("Axe");
        atticItemList.showItemList(); //just to test whether the items are showing up
    }

    public String toString() { return "attic"; }
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
        else
            return new Room();

    }
    //override the create Maze
    public Maze createMaze(){
        Maze ZorkMaze = makeMaze();

        Room newLivingRoom = makeRoom("LivingRoom");
        Room newKitchen = makeRoom("Kitchen");
        Room newAttic = makeRoom("Attic");

        //gotta add a connection to the kitchen and the attic
        Door newlivingRoomAndKitchenDoor = makeDoor(newLivingRoom, newKitchen);

        //"door" to the attic
        Door newKitchenDoorAndAttic = makeDoor(newKitchen, newAttic);

        ZorkMaze.addRoom(newLivingRoom);
        ZorkMaze.addRoom(newKitchen);

        //new room Attic
        ZorkMaze.addRoom(newAttic);

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

        //we need to add more doors and rooms and the relation between everytime we add rooms to map......

        return ZorkMaze;
    }
}

//=========================================================================================================
// WordParser
//==============================================================================
class KeyWords
{
    //keywords for directions
    private String[] directions = {
            "NORTH", "SOUTH", "EAST", "WEST", "FORWARD", "BACKWARDS",
            "LEFT", "RIGHT", "UPSTAIRS", "DOWNSTAIRS", "CELLAR"
    };

    //keywords for actions
    private String[] actions = {
            "ATTACK", "SEARCH", "UNLOCK", "JUMP", "TAKE", "LOOK", "GO", "OPEN",
            "INVENTORY"
    };

    //Might need an array of items
    private String[] items = {
            "LEAFLET", "MAILBOX", "DOOR", "WINDOW", "TROPHY", "SWORD"
    };

    //String keywords[];

    //I can do a boolean whether its a direction or an action
    //The directions will only need at least one true boolean and check what direction it is to go that specific direction
    //The actions will have to be specific and might need another

    String direction;
    String action;
    String item;


    boolean bDirection; //the 'b' just means boolean
    boolean bAction;
    boolean bItem;

    public KeyWords() { this(" ", " ", " ", false, false, false); }
    public KeyWords(String direction, String action, String item,
                    boolean bDirection, boolean bAction, boolean bItem)
    {
        this.direction = direction;
        this.action = action;
        this.item = item;

        this.bDirection = bDirection;
        this.bAction = bAction;
        this.bItem = bItem;
    }

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

    //I could probably combine all these function commands to 1 function

    //checks if the list is null, then passes the list to another function
    public void getCommand(List<String> words) {
        //check if the incoming list of strings is empty
        if(words.isEmpty())
        {
            System.out.println("Please input a command.");
        }
        checkLegitCommand(words);
    }

    //function checks for the specifics in user input
    private void checkLegitCommand(List<String> words) {
        //String keywords[] = { };

        List<String> keywords = new ArrayList<String>();

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

        //pass the keywords to the final class to determine the final action
        putWordsTogether(keywords);
    }

    //this can take a boolean and a String
    public void putWordsTogether(List<String> words) {
        //this checks if they had used an action combined with a direction
        //if they did, it would then check the specific words and determine the action
        if(getBAction() == true && getBDirection() == true) {
            if(words.contains("GO") && words.contains("NORTH")) {
                System.out.println("You went north.");
            }

            if(words.contains("GO") && words.contains("UPSTAIRS")) {
                System.out.println("You went upstairs.");
            }
        }

        if(getBAction() == true && getBItem() == true) {
            if(words.contains("TAKE") && words.contains("LEAFLET")) {
                System.out.println("You took the leaflet");
            }

            if(words.contains("TAKE") && words.contains("SWORD")) {
                System.out.println("You took the sword.");
            }
        }

        //so.println("This is the new push");
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
        KeyWords key = new KeyWords();
        Scanner scan = new Scanner(System.in);

        String input;
        String words[];

        System.out.println("You wake up in front of a house. Where do you want to go?");
        System.out.print(">");
        input = scan.nextLine().toUpperCase();
        words = input.split(" "); //split by teh spaces read in

        List<String> list = Arrays.asList(words);

        key.getCommand(list);
    }
}