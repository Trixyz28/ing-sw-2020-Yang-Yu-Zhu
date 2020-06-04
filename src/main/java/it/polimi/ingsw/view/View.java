package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.observers.Observer;

import java.io.PrintStream;
import java.util.Scanner;


public abstract class View extends Observable implements Observer{

    protected Player player;
    private Scanner scanner;
    private PrintStream outputStream;
    private boolean endGame = false;
    private int currentID;
    protected Operation operation;
    protected GameMessage gameMessage;


    public View(Player player){
        this.player = player;
        endGame = false;
    }

    public void setEndGame(){
        endGame = true;

    }


    @Override
    public void update(Object message) {
    }

    protected void handleOp(String input){  /* modificare l'Op precedentemente salvata con l'input dal Client */
        try {
            String[] inputs = input.split(",");
            int row = Integer.parseInt(inputs[0]);
            int column = Integer.parseInt(inputs[1]);
            operation.setPosition(row, column);
            System.out.println("Received: Operation type " + operation.getType() + " ("
                    + operation.getRow() + ", " + operation.getColumn() + ")");
            notify(operation);
            /* ripristinare */
            //operation = null;
        }catch (IllegalArgumentException e){
            System.out.println("Inserimento invalido");
        }
    }


    protected void handleGm(String input){  /* modificare Gm precedentemente salvata con l'input dal Client */
        if(gameMessage.getMessage().equals(Messages.Worker)){
            try {
                int index = Integer.parseInt(input);
                System.out.println("Received: WorkerIndex : " + index);
                notify(index);  /* Integer */
                //gameMessage = null;
            }catch(IllegalArgumentException e){
                System.out.println("Inserimento invalido");
            }
        }else{
            gameMessage.setAnswer(input);
            System.out.println("Received: Answer : " + gameMessage.getAnswer());
            notify(gameMessage);  /* ripristinare */
        }

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

    void messageString(String input) {
        GameMessage gm = new GameMessage(player, null, false);
        gm.setAnswer(input);
        notify(gm);
    }



    private void printMap(Board board) {

        for(int i=0; i<5; i++){
            System.out.print("|");

            for(int j=0; j<5; j++){

                Tile t = board.getTile(i,j);

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
