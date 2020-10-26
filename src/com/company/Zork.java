package com.company;

import java.util.ArrayList;
import java.util.List;

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
        else
            return new Room();

    }
    //override the create Maze
    public Maze createMaze(){
        Maze ZorkMaze = makeMaze();

        Room newLivingRoom = makeRoom("LivingRoom");
        Room newKitchen = makeRoom("Kitchen");

        Door newlivingRoomAndKitchenDoor = makeDoor(newLivingRoom, newKitchen);

        ZorkMaze.addRoom(newLivingRoom);
        ZorkMaze.addRoom(newKitchen);

        newLivingRoom.setSide(Direction.North, makeWall());
        newLivingRoom.setSide(Direction.South,makeWall());
        newLivingRoom.setSide(Direction.East, makeWall());
        newLivingRoom.setSide(Direction.West, newlivingRoomAndKitchenDoor);

        newKitchen.setSide(Direction.North, makeWall());
        newKitchen.setSide(Direction.South, makeWall());
        newKitchen.setSide(Direction.West, makeWall());
        newKitchen.setSide(Direction.East, newlivingRoomAndKitchenDoor);

        //we need to add more doors and rooms and the relation between everytime we add rooms to map......

        return ZorkMaze;
    }
}


public class Zork{
    public static void main(String[] args){
        MazeGame game = new ZorkMazeGame();
        Maze maze = game.createMaze();
        maze.showRoomList();
    }
}