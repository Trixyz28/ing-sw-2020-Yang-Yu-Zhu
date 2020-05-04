package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;

import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.io.PrintStream;
import java.util.Scanner;


public class View extends Observable implements Observer, Runnable {

    private Scanner scanner;
    private PrintStream outputStream;
    private boolean endGame = false;
    private int currentID;


    public View(){
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
        endGame = false;
    }


    public void setGame() {

        notify("setup");
    }


    public void readName() {
        String name = "Ciao";
        notify("readname");
    }





    @Override
    public void update(Object message) {
        if(message instanceof String[]){
            showComplete((String[])message);
        }
    }

    @Override
    public void run() {

        setGame();

        while(!endGame) {

            outputStream.println("Player " + currentID + "'s turn");

            chooseWorker();
        }
    }

    private void chooseWorker() {
        outputStream.println("Which worker do you want to choose?");


    }

    //printare GodList completo
    private void showComplete(String[] completeList){
        outputStream.println("Le divinità che puoi scegliere sono:");
        for(String s : completeList){
            outputStream.println(s);
        }
    }



    private void operation() {





    }

    private void move() {

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


    private void build() {

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

    public void placeWorkers(Player player){

    }
}
