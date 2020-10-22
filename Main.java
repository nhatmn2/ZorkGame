package com.company;

import java.io.PrintStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//import static com.company.Main.print_fixed;
import static com.company.Main.so;

enum Directions {
    NORTH, SOUTH, EAST, WEST
}


public class Main {
    public static PrintStream so = System.out;

    //this is just a test class
    public static void print_fixed(boolean stuff) {
        if(stuff == true) {
            so.println("Found!");
        }
        else {
           so.println("Not Found.");
        }
    }

    public static void runTests() {
        //keywords for directions
        //String[] directions = { "NORTH", "SOUTH", "EAST", "WEST" };

        //keywords for actions
        //String[] actions = {"ATTACK", "SEARCH", "UNLOCK", "JUMP"};

        KeyWords key = new KeyWords();

        //String[] tempArray;
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

        //checkDirections(words);

        //this is perfect for parser
        //for(String keywords : directions) {
        //if(input.contains(keywords)) {
        //so.println("Found word! " + keywords);
        //}
        //}

        //must use .contains or else it will not read it
        //for(int i = 0; i < directions.length; ++i) {
            //if(input.contains(directions[i])) {
                //so.println("Found direction! " + directions[i]);
            //}
        //}

        //perfect for identifying words
        //for(int i = 0; i < actions.length; ++i) {
            //if(input.contains(actions[i])) {
                //so.println("Found action! " + actions[i]);
           //}
        //}



        //this is just to print out the words the user has typed in
        //for(String token : words) {
            //so.println(token);
        //}



        //I can maybe use a array list to print to combine
        //for(int i = 0; i < directions.length; ++i) {
        //if(input.contains(directions[i])) {
        //so.println("You went west");
        //}
        //}

        //this is reading the string but only
        //if(input.equals(Directions.NORTH.name())) {
        //so.println("You went north");
        //}
    }

    public static void checkDirections(String words[]) {
        for(int i = 0; i < words.length; ++i) {
            so.println(words[i]);
        }
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
            so.println("Please input a command.");
        }

        //definitely combine all of these functions into one
/*        checkDirections(words);
        checkActions(words);
        checkItems(words);*/

        checkLegitCommand(words);
    }

    //function checks for the specifics in user input
    private void checkLegitCommand(List<String> words) {
        //String keywords[] = { };

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
                //this.bDirection = true;

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

    //this can take a boolean and a String
    public void putWordsTogether(List<String> words) {
/*        for(int i = 0; i < words.size(); ++i) {
            so.println(words.get(i));
        }*/


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

/*        if(words.equals("GO NORTH")) {
            so.print("You went north");
        }*/

    }

    //check directions
/*    private void checkDirections(List<String> words) {
        for(int i = 0; i < directions.length; ++i) {
            //this shit works
            if(words.contains(directions[i])) {
                so.println("Found direction! " + directions[i]);
                returnDirection(true); //you have to access the actual variable
            }
            else {
                returnDirection(false);
            }
        }
    }

    //check for actions
    private void checkActions(List<String> words) {
        for(int i = 0; i < actions.length; ++i) {
            if(words.contains(actions[i])) {
                so.println("Found action! " + actions[i]);
                returnAction(true);
            }
            else {
                returnAction(false);
            }
        }
    }

    //check for items
    private void checkItems(List<String> words) {
        for(int i = 0; i < items.length; ++i) {
            if(words.contains(items[i])) {
                so.println("Found item! " + items[i]);
                returnItem(true);
            }
            else {
                returnItem(false);
            }
        }
    }*/

    //create a combiner of words function so it can be passed to a
    //function to determine the final action
}
