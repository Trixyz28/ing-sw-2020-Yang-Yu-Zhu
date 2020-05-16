package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;

import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.io.PrintStream;
import java.util.Scanner;


public abstract class View extends Observable implements Observer{

    protected Player player;
    private Scanner scanner;
    private PrintStream outputStream;
    private boolean endGame = false;
    private int currentID;



    public View(Player player){
        this.player = player;
        endGame = false;
    }


    public void setGame() {
        notify("setup");
    }


    @Override
    public void update(Object message) {
    }


    private void chooseWorker() {
        outputStream.println("Which worker do you want to choose?");


    }



    //Reserved to challenger - pick a god card
    private String defineGodList() {
        System.out.println("Select a god card:");
        return scanner.next();

    }

    public abstract void showMessage(String message);



    private void operation() {





    }
    /*
    public void move() {

        int row = -1;
        int column = -1;
        boolean done = false;

        outputStream.println("Where do you want to move? (row,column)");

        while(!done) {
            outputStream.println("Choose row and column from 1 to 5");
            String str = scanner.next();
            try {
                String[] inputs = str.split(",");
                row = Integer.parseInt(inputs[0]);
                column = Integer.parseInt(inputs[1]);
            } catch(NumberFormatException e) {
                outputStream.println("Command error!");
            }

            if(row>=1 && row<=5 && column>=1 && column<=5) {
                done=true;
            }
        }

        notify(new Operation(1,row-1,column-1));
    }


    public void build() {

        int row = -1;
        int column = -1;
        boolean done = false;

        outputStream.println("Where do you want to build? (row,column)");

        while(!done) {
            outputStream.println("Choose row and column from 1 to 5");
            String str = scanner.next();
            try {
                String[] inputs = str.split(",");
                row = Integer.parseInt(inputs[0]);
                column = Integer.parseInt(inputs[1]);
            } catch(NumberFormatException e) {
                outputStream.println("Command error!");
            }

            if(row>=1 && row<=5 && column>=1 && column<=5) {
                done=true;
            }
        }

        notify(new Operation(2,row-1,column-1));
    }
    */



    private void printMap(Map map) {

        for(int i=0; i<5; i++){
            System.out.print("|");

            for(int j=0; j<5; j++){

                Tile t = map.getTile(i,j);

                if(t.isDomePresence()) {
                    outputStream.print("  D");
                } else if (t.isOccupiedByWorker()) {
                    outputStream.print("  W");
                } else {
                    outputStream.format("%3d",t.getBlockLevel());
                }

                outputStream.print("|");
            }
            outputStream.println();
        }
        outputStream.println();
    }

    /*
    public void placeWorker(Player player){
        int row = -1;
        int column = -1;
        boolean done = false;

        outputStream.println("Where do you want to place? (row,column)");

        while(!done) {
            outputStream.println("Choose row and column from 1 to 5");
            String str = scanner.next();
            try {
                String[] inputs = str.split(",");
                row = Integer.parseInt(inputs[0]);
                column = Integer.parseInt(inputs[1]);
            } catch(NumberFormatException e) {
                outputStream.println("Command error!");
            }

            if(row>=1 && row<=5 && column>=1 && column<=5) {
                done=true;
            }
        }

        notify(new Operation(0,row-1,column-1));

    }
     */
}
