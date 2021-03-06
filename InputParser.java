package com.company;

import java.io.PrintStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.company.InputParser.so;

public class InputParser {
    public static PrintStream so = System.out;

    public static void runTests() {

        KeyWords key = new KeyWords();

        Scanner scan = new Scanner(System.in);

        String input;
        String words[];

        boolean x; //might use this now

        so.println("Welcome to hell! Where do you want to go?");
        so.print('>');
        input = scan.nextLine().toUpperCase();
        words = input.split(" ");

        //had to turn array to a list
        List<String> list = Arrays.asList(words);

        //a class that combines all of these
        key.getCommand(list);
    }

    public static void main(String[] args) {
        //use a while loop to continue the game running
        //when the player gains the mcguffin, then the game ends 

        runTests();

    }
}

class KeyWords
{
    //keywords for directions
    private String[] directions = {
            "NORTH", "SOUTH", "EAST", "WEST", "FORWARD", "BACKWARDS",
            "LEFT", "RIGHT", "UPSTAIRS", "DOWNSTAIRS"
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

    //variables for user specific input
    String direction;
    String action;
    String item;


    boolean bDirection; //the 'b' just means boolean
    boolean bAction;
    boolean bItem;

    //constructors
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

    //checks if the list is null, then passes the list to another function
    public void getCommand(List<String> words) {
        //check if the incoming list of strings is empty
        if(words.isEmpty())
        {
            so.println("Please input a command.");
        }
          
        //list is then passed to a function to determine the action of the user
        checkLegitCommand(words);
    }

    //function checks for the specifics in user input
    private void checkLegitCommand(List<String> words) {
        //normally, in game you don't need to specify too much of your action other than a quick 2 to 3 word input

        List<String> keywords = new ArrayList<String>();

        //check for actions(verbs)
        for(int i = 0; i < actions.length; ++i) {
            if(words.contains(actions[i])) {
                so.println("Found action! " + actions[i]);
                setBAction(true);
                //this.bAction = true;

                setAction(actions[i]);

                keywords.add(getAction());
            }
        }

        //check for directions
        for(int i = 0; i < directions.length; ++i) {
            //this shit works
            if(words.contains(directions[i])) {
                so.println("Found direction! " + directions[i]);
                setBDirection(true); //you have to access the actual variable

                setDirection(directions[i]);

                keywords.add(getDirection());
            }
        }

        //check for items
        for(int i = 0; i < items.length; ++i) {
            if(words.contains(items[i])) {
                so.println("Found item! " + items[i]);
                setBItem(true);
                setItem(items[i]);

                keywords.add(getItem());
            }
        }

        //pass the keywords to the final class to determine the final action
        putWordsTogether(keywords);
    }

    public void putWordsTogether(List<String> words) {
        //this checks if they had used an action combined with a direction
        //if they did, it would then check the specific words and determine the action
        if(getBAction() == true && getBDirection() == true) {
            if(words.contains("GO") && words.contains("NORTH")) {
                so.println("You went north.");
            }

            if(words.contains("GO") && words.contains("UPSTAIRS")) {
                so.println("You went upstairs.");
            }
        }

        if(getBAction() == true && getBItem() == true) {
            if(words.contains("TAKE") && words.contains("LEAFLET")) {
                so.println("You took the leaflet");
            }

            if(words.contains("TAKE") && words.contains("SWORD")) {
                so.println("You took the sword.");
            }
        }
    }
}
